package com.alex.gamestore.service;

import com.alex.gamestore.exceptions.InvalidProductCountException;
import com.alex.gamestore.exceptions.NoProductFoundException;
import com.alex.gamestore.jpa.BoardGameRepository;
import com.alex.gamestore.jpa.ProductRepository;
import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.GameType;
import com.alex.gamestore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class StockService {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private ProductRepository productRepository;

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

    @Transactional
    public void decrementProductCount(Set<BoardGame> games) {
        Set<Product> toUpdate = new HashSet<>();
        for (BoardGame game : games) {
            Product product = boardGameRepository.findById(game.getId())
                    .map(BoardGame::getProduct)
                    .orElseThrow(() -> new NoProductFoundException("No Product found"));
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
        productRepository.saveAll(toUpdate);
    }
}
