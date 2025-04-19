package br.com.powercrm.app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_vehicle")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String plate;
    @Column(name = "advertised_plate")
    private BigDecimal advertisedPlate;
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name="created_at")
    private Instant createdAt;
}
