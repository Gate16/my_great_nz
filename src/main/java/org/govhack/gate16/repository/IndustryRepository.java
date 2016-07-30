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

    @Query(value = "SELECT SUM(industry.year_2017) FROM Industry industry WHERE industry.region LIKE ?1 ", nativeQuery = true)
    Integer getTotalManPowerOfAllIndustryInRegion(String region);

    @Query(value = "SELECT industry.year_2017 FROM Industry industry WHERE industry.region LIKE ?1 AND industry.industry_name LIKE ?2 ", nativeQuery = true)
    Integer getTotalManPowerByRegionAndIndustry(String region, String industry);

    Industry findByRegionAndIndustryName(String region, String industry);
}
