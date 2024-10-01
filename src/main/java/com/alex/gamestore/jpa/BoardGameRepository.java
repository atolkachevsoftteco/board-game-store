package com.alex.gamestore.jpa;

import com.alex.gamestore.model.BoardGame;
import com.alex.gamestore.model.GameType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardGameRepository extends PagingAndSortingRepository<BoardGame, Long>, CrudRepository<BoardGame, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "product")
    Page<BoardGame> findByTypeIn(List<GameType> typesList, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "product")
    Page<BoardGame> findByType(@Param("g_type") GameType type, Pageable pageable);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "product")
    Page<BoardGame> findAll(Pageable pageable);

    @Query("select count(*) from BoardGame")
    Integer getPagesCount();
}
