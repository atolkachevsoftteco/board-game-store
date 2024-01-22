package com.alex.gamestore.controller;

import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.Booking;
import com.alex.gamestore.model.User;
import com.alex.gamestore.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> bookGames(@RequestBody @Valid Set<BoardGame> games,
                                             @RequestParam int days) {

        return ResponseEntity.ok(bookingService.createBooking(new User("123", "123"), games, days));
    }
}
