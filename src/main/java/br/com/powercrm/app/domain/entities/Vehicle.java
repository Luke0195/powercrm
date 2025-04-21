package br.com.powercrm.app.domain.entities;

import br.com.powercrm.app.domain.enums.VehicleStatus;
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="model_id")
    private Model model;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_status")
    private VehicleStatus status;

    @Column(name="fipe_price")
    private BigDecimal fipePrice;


    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

}

