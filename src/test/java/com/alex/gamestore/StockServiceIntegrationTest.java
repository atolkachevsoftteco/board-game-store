package com.alex.gamestore;

import com.alex.gamestore.jpa.BoardGameRepository;
import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.Product;
import com.alex.gamestore.service.StockService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test-containers")
@Testcontainers
@Sql("/test-data.sql")
class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;
    @SpyBean
    private BoardGameRepository boardGameRepository;

    @Test
    @Disabled
    void testDecrementProductCount() throws InterruptedException {
        Set<BoardGame> boardGames = Set.of(BoardGame.builder().id(1L).build());
        Product product = boardGameRepository.findById(1L).orElseThrow().getProduct();
        int countBeforeTest = product.getCount();

        Runnable runnable = () -> stockService.decrementProductCount(boardGames);
        Runnable runnable1 = () -> stockService.decrementProductCount(boardGames);
        Runnable runnable2 = () -> stockService.decrementProductCount(boardGames);
        Runnable runnable4 = () -> stockService.decrementProductCount(boardGames);

        Thread t1 = new Thread(runnable, "thread-1");
        Thread t2 = new Thread(runnable1, "thread-2");
        Thread t3 = new Thread(runnable2, "thread-3");
        Thread t4 = new Thread(runnable4, "thread-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.sleep(4000);

        int countAfter = boardGameRepository.findById(1L).orElseThrow().getProduct().getCount();
        verify(boardGameRepository, times(6)).findById(anyLong());
        assertEquals(countBeforeTest - 4, countAfter);
    }
}
