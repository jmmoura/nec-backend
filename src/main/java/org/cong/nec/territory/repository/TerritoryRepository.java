package org.cong.nec.territory.repository;

import org.cong.nec.territory.model.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, Long> {

    Optional<Territory> findByNumber(String number);

}
