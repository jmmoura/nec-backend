package org.cong.nec.address.repository;

import org.cong.nec.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM address a JOIN a.block b JOIN b.territory t WHERE t.number = :territoryNumber")
    List<Address> findByTerritoryNumber(@Param("territoryNumber") String territoryNumber);
}
