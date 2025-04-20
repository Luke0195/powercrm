package br.com.powercrm.app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_vehicles")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String plate;
    @Column(name = "advertised_plate")
    private BigDecimal advertisedPlate;
    @Column(name = "vehicle_year")
    private Integer vehicleYear;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name="model_id")
    private Model model;
    @OneToOne
    @JoinColumn(name="brand_id")
    private Brand brand;
    @Column(name="fipe_price")
    private BigDecimal fipePrice;


    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

}

