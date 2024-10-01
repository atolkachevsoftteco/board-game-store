package com.alex.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @Id
    @Column(name = "game_id")
    @EqualsAndHashCode.Include
    private Long id;

    private Integer count;

    private Boolean inStock;

    @OneToOne
    @MapsId
    @JoinColumn(name = "game_id")
    @JsonIgnore
    @ToString.Exclude
    private BoardGame game;
}
