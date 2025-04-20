package br.com.powercrm.app.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_fipe_prices")
public class FipePrice implements Serializable {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;
    private Long year;
    @Column(name = "fipe_price")
    private BigDecimal fipePrice;
}
