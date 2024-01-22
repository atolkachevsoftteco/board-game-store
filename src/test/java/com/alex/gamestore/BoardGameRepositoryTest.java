package com.alex.gamestore;

import com.alex.gamestore.jpa.BoardGameRepository;
import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.GameType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
class BoardGameRepositoryTest {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @Test
    void testFindAll() {
//        Set<GameType> type = Set.of(GameType.CARDS, GameType.CLASSIC);

        Page<BoardGame> page = boardGameRepository.findAll(PageRequest.of(0, 8));
        assertThat(page.getNumberOfElements()).isEqualTo(8);
    }

    @Test
    void testGetByType() {
//        Set<GameType> type = Set.of(GameType.CARDS, GameType.CLASSIC);

        Page<BoardGame> page = boardGameRepository.findByType(GameType.CLASSIC, PageRequest.of(0, 8));
        assertThat(page.getNumberOfElements()).isEqualTo(5);
    }

    @Test
    void testGetByTypes() {
//        Set<GameType> type = Set.of(GameType.CARDS, GameType.CLASSIC);

        Page<BoardGame> page = boardGameRepository.findByTypeIn(List.of(GameType.CLASSIC, GameType.CARDS), PageRequest.of(0, 8));
        assertThat(page.getNumberOfElements()).isEqualTo(8);
    }

    @Test
    void testAddNewGame() {
        BoardGame boardGame = BoardGame.builder()
                .name("Манчкин")
                .type(GameType.STRATEGY)
                .age(12)
                .cost(150.0)
                .description("Test")
                .descSmall("Board game")
                .build();

        BoardGame game = boardGameRepository.save(boardGame);
        assertThat(game.getId()).isNotNull();
    }
}
