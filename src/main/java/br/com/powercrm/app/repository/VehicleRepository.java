package br.com.powercrm.app.repository;

import br.com.powercrm.app.domain.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    boolean existsByPlate(String plate);
    Optional<Vehicle> findByPlate(String plate);
}
