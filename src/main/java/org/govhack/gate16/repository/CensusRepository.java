package org.govhack.gate16.repository;

import org.govhack.gate16.domain.Census;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Census entity.
 */
@SuppressWarnings("unused")
public interface CensusRepository extends JpaRepository<Census,Long> {

}
