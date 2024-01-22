package com.alex.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
@Data
public class Product {
    @Id
    @Column(name = "game_id")
    private Long id;

    private Integer count;

    private Boolean inStock;

    @OneToOne
    @MapsId
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private BoardGame game;
}
