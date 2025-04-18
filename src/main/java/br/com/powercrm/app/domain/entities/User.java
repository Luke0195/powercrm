package br.com.powercrm.app.domain.entities;

import br.com.powercrm.app.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(unique = true)
    private String email;
    private String phone;
    @Column(unique = true)
    private String cpf;
    @Column(name="zip_code")
    private String zipCode;
    private String address;
    private Integer number;
    private String complement;
    private UserStatus status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
