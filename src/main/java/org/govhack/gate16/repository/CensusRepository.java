package org.govhack.gate16.repository;

import org.govhack.gate16.domain.Census;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Census entity.
 */
@SuppressWarnings("unused")
public interface CensusRepository extends JpaRepository<Census,Long> {

    @Query(value = "SELECT SUM(c.year_2013) FROM Census c " +
            "WHERE c.region LIKE ?1 " +
            "   AND c.interest_area LIKE ?2",
            nativeQuery = true)
    Integer getTotalAllTypeByRegionAndInterestAreaFor2013(String region, String interestArea);

    @Query(value = "SELECT c.year_2013 FROM Census c " +
            "WHERE c.region LIKE ?1 " +
            "   AND c.interest_area LIKE ?2" +
            "   AND c.type LIKE ?3",
            nativeQuery = true)
    Integer getValueByRegionAndInterestAreaAndTypeFor2013(String region, String interestArea, String type);

//    @Query("FROM Census c " +
//            "WHERE c.region LIKE '?1' " +
//            "   AND c.interestArea LIKE '?2' " +
//            "   AND c.type LIKE '?3'")
    Census findByRegionAndInterestAreaAndType(String region, String interestArea, String type);
}
