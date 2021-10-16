package org.example.entity.tech;

import org.example.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spare_parts")
public class SpareParts extends BaseEntity {
    
    @Column(name = "name_of_part")
    private String nameOfPart;
    
    @Column(name = "number_of_parts")
    private String numberOfParts;
    
    @Column(name = "price_of_part")
    private Double priceOfPart;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Techniques> techniques;

    public String getNameOfPart() {
        return nameOfPart;
    }

    public void setNameOfPart(String nameOfPart) {
        this.nameOfPart = nameOfPart;
    }

    public String getNumberOfParts() {
        return numberOfParts;
    }

    public void setNumberOfParts(String numberOfParts) {
        this.numberOfParts = numberOfParts;
    }

    public Double getPriceOfPart() {
        return priceOfPart;
    }

    public void setPriceOfPart(Double priceOfPart) {
        this.priceOfPart = priceOfPart;
    }

    public List<Techniques> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Techniques> techniques) {
        this.techniques = techniques;
    }
}
