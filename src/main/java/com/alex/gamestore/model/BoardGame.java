package com.alex.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private Double cost;
    private Integer players;
    private String imageUrl;
    private String description;
    private Integer age;
    private String descSmall;

    @Enumerated(EnumType.STRING)
    @Column(name = "g_type")
    @Type(type = "com.alex.gamestore.model.EnumTypePostgreSql")
    private GameType type;

    @OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Product product;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "boardgame_booking",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "booking_id")}
    )
    @JsonIgnore
    Set<Booking> bookings;
}
