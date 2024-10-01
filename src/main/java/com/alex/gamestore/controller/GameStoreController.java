package com.alex.gamestore.controller;

import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.GameType;
import com.alex.gamestore.service.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class GameStoreController {

    private final StockService stockService;

    public GameStoreController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(path = "/games")
    public ResponseEntity<Page<BoardGame>> fetchAllGames(@PageableDefault(sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable,
                                                         @RequestParam(required = false) GameType type) {
        return ResponseEntity.ok(stockService.getAllGamesFromWarehouse(pageable, type));
    }

    @GetMapping(path = "/pages")
    public ResponseEntity<Integer> countPages(int size) {
        return ResponseEntity.ok(stockService.getPagesCount(size));
    }
}
