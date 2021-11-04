package org.example.entity.tech;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "combustible_lubricants")
public class CombustibleLubricant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name_of_lubricant")
    private String nameOfLubricant;
    
    @Column(name = "number_of_lubricant")
    private Double numberOfLubricant;
    
    @Column(name = "price_of_lubricant")
    private Double priceOfLubricant;
    
    @ManyToMany( fetch = FetchType.LAZY)
    private List<Technique> techniques;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNameOfLubricant() {
        return nameOfLubricant;
    }

    public void setNameOfLubricant(String nameOfLubricant) {
        this.nameOfLubricant = nameOfLubricant;
    }

    public Double getNumberOfLubricant() {
        return numberOfLubricant;
    }

    public void setNumberOfLubricant(Double numberOfLubricant) {
        this.numberOfLubricant = numberOfLubricant;
    }

    public Double getPriceOfLubricant() {
        return priceOfLubricant;
    }

    public void setPriceOfLubricant(Double priceOfLubricant) {
        this.priceOfLubricant = priceOfLubricant;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }
}
