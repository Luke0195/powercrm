package br.com.powercrm.app.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="tb_models")
public class Model implements Serializable {

    @Id
    private Long id;
    private String name;
}
