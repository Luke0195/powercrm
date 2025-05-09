package br.com.powercrm.app.repository;

import br.com.powercrm.app.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    List<User> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
