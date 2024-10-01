package com.alex.gamestore.service;

import com.alex.gamestore.exceptions.NoProductFoundException;
import com.alex.gamestore.jpa.BookingRepository;
import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.Booking;
import com.alex.gamestore.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;

    private final StockService stockService;

    public BookingService(BookingRepository bookingRepository, StockService stockService) {
        this.bookingRepository = bookingRepository;
        this.stockService = stockService;
    }

    public Booking createBooking(User user, Set<BoardGame> games, int days) {
        Booking booking = Booking.builder()
                .user(user)
                .games(games)
                .days(days)
                .build();
        if (emptyIfNull(games).stream().allMatch(boardGame -> stockService.checkProductInStock(boardGame.getId()))) {
            booking = bookingRepository.save(booking);
            stockService.decrementProductCount(games);
        } else {
            log.info("No product in stock");
            throw new NoProductFoundException("Product not found in stock. Booking not available");
        }
        return booking;
    }
}
