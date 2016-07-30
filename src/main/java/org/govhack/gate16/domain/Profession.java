package org.govhack.gate16.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Profession.
 */
@Entity
@Table(name = "profession")
@Document(indexName = "profession")
public class Profession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "profession_id")
    private Integer professionId;

    @Column(name = "profession_name")
    private String professionName;

    @Column(name = "year_2016")
    private Integer year_2016;

    @Column(name = "year_2017")
    private Integer year_2017;

    @Column(name = "year_2018")
    private Integer year_2018;

    @Column(name = "year_2019")
    private Integer year_2019;

    @Column(name = "growth")
    private Float growth;

    @Column(name = "growth_percentage")
    private Float growthPercentage;

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

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }

    public Integer getYear_2016() {
        return year_2016;
    }

    public void setYear_2016(Integer year_2016) {
        this.year_2016 = year_2016;
    }

    public Integer getYear_2017() {
        return year_2017;
    }

    public void setYear_2017(Integer year_2017) {
        this.year_2017 = year_2017;
    }

    public Integer getYear_2018() {
        return year_2018;
    }

    public void setYear_2018(Integer year_2018) {
        this.year_2018 = year_2018;
    }

    public Integer getYear_2019() {
        return year_2019;
    }

    public void setYear_2019(Integer year_2019) {
        this.year_2019 = year_2019;
    }

    public Float getGrowth() {
        return growth;
    }

    public void setGrowth(Float growth) {
        this.growth = growth;
    }

    public Float getGrowthPercentage() {
        return growthPercentage;
    }

    public void setGrowthPercentage(Float growthPercentage) {
        this.growthPercentage = growthPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profession profession = (Profession) o;
        if(profession.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profession.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profession{" +
            "id=" + id +
            ", region='" + region + "'" +
            ", professionId='" + professionId + "'" +
            ", professionName='" + professionName + "'" +
            ", year_2016='" + year_2016 + "'" +
            ", year_2017='" + year_2017 + "'" +
            ", year_2018='" + year_2018 + "'" +
            ", year_2019='" + year_2019 + "'" +
            ", growth='" + growth + "'" +
            ", growthPercentage='" + growthPercentage + "'" +
            '}';
    }
}
