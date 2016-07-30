package org.govhack.gate16.repository;

import org.govhack.gate16.domain.Industry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Industry entity.
 */
@SuppressWarnings("unused")
public interface IndustryRepository extends JpaRepository<Industry,Long> {
    List<Industry> findByRegion(String region);
}
