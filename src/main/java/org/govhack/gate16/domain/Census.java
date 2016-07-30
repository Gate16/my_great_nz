package org.govhack.gate16.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Census.
 */
@Entity
@Table(name = "census")
@Document(indexName = "census")
public class Census implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "interest_area")
    private String interestArea;

    @Column(name = "type")
    private String type;

    @Column(name = "year_2006")
    private Integer year2006;

    @Column(name = "year_2013")
    private Integer year2013;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(String interestArea) {
        this.interestArea = interestArea;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear2006() {
        return year2006;
    }

    public void setYear2006(Integer year2006) {
        this.year2006 = year2006;
    }

    public Integer getYear2013() {
        return year2013;
    }

    public void setYear2013(Integer year2013) {
        this.year2013 = year2013;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Census census = (Census) o;
        if(census.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, census.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Census{" +
            "id=" + id +
            ", region='" + region + "'" +
            ", interestArea='" + interestArea + "'" +
            ", type='" + type + "'" +
            ", year2006='" + year2006 + "'" +
            ", year2013='" + year2013 + "'" +
            '}';
    }
}
