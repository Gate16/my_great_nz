package org.govhack.gate16.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Industry.
 */
@Entity
@Table(name = "industry")
@Document(indexName = "industry")
public class Industry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "industry_name")
    private String industryName;

    @Column(name = "year_2016")
    private Integer year2016;

    @Column(name = "year_2017")
    private Integer year2017;

    @Column(name = "year_2018")
    private Integer year2018;

    @Column(name = "year_2019")
    private Integer year2019;

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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Integer getYear2016() {
        return year2016;
    }

    public void setYear2016(Integer year2016) {
        this.year2016 = year2016;
    }

    public Integer getYear2017() {
        return year2017;
    }

    public void setYear2017(Integer year2017) {
        this.year2017 = year2017;
    }

    public Integer getYear2018() {
        return year2018;
    }

    public void setYear2018(Integer year2018) {
        this.year2018 = year2018;
    }

    public Integer getYear2019() {
        return year2019;
    }

    public void setYear2019(Integer year2019) {
        this.year2019 = year2019;
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
        Industry industry = (Industry) o;
        if(industry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, industry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Industry{" +
            "id=" + id +
            ", region='" + region + "'" +
            ", industryName='" + industryName + "'" +
            ", year2016='" + year2016 + "'" +
            ", year2017='" + year2017 + "'" +
            ", year2018='" + year2018 + "'" +
            ", year2019='" + year2019 + "'" +
            ", growth='" + growth + "'" +
            ", growthPercentage='" + growthPercentage + "'" +
            '}';
    }
}
