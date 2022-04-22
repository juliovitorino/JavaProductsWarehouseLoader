package br.com.juliovitorino.msproductcharger.repositories;

import br.com.juliovitorino.msproductcharger.models.WarehouseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<WarehouseModel, UUID> {
}
