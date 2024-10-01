package com.alex.gamestore.service;

import com.alex.gamestore.exceptions.InvalidProductCountException;
import com.alex.gamestore.exceptions.NoProductFoundException;
import com.alex.gamestore.jpa.BoardGameRepository;
import com.alex.gamestore.jpa.ProductRepository;
import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.GameType;
import com.alex.gamestore.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class StockService {

    private final BoardGameRepository boardGameRepository;

    private final ProductRepository productRepository;

    public StockService(BoardGameRepository boardGameRepository, ProductRepository productRepository) {
        this.boardGameRepository = boardGameRepository;
        this.productRepository = productRepository;
    }

    public Page<BoardGame> getAllGamesFromWarehouse(Pageable pageable, GameType type) {
        if (type != null) {
            return boardGameRepository.findByType(type, pageable);
        }
        return boardGameRepository.findAll(pageable);
    }

    public Integer getPagesCount(int size) {
        double itemsCount = boardGameRepository.getPagesCount();
        return (int) Math.ceil(itemsCount / size);
    }

    public boolean checkProductInStock(Long gameId) {
        Optional<Product> product = boardGameRepository.findById(gameId).map(BoardGame::getProduct);
        return product.map(Product::getInStock).orElseThrow(RuntimeException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void decrementProductCount(Set<BoardGame> games) {
        Set<Product> toUpdate = new HashSet<>();
        for (BoardGame game : games) {
            Product product = boardGameRepository.findById(game.getId())
                    .map(BoardGame::getProduct)
                    .orElseThrow(() -> new NoProductFoundException("No Product found"));
            //TODO: what in case 2 concurrent transactions try to read count?
            // Native query: BEGIN transaction
            // select * from BoardGame bg JOIN Product p on bg.productId=p.id where bg.id = {game_id}
            // UPDATE Product SET count = (count - 1)
            // (if count == 0) UPDATE Product SET inStock = false
            // COMMIT
            Integer count = product.getCount();
            if (count > 0) {
                count--;
                if (count == 0) {
                    product.setInStock(false);
                }
                product.setCount(count);
            } else {
                throw new InvalidProductCountException("Invalid count for product: " + product.getId());
            }
            toUpdate.add(product);
        }
        log.info("Thread name: " + Thread.currentThread().getName() + Thread.currentThread().getId());
        toUpdate.forEach(item -> log.info(String.valueOf(item)));
        productRepository.saveAll(toUpdate);
    }
}
