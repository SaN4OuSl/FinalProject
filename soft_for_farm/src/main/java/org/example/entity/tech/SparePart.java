package org.example.entity.tech;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spare_parts")
public class SparePart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name_of_part")
    private String nameOfPart;
    
    @Column(name = "number_of_parts")
    private String numberOfParts;
    
    @Column(name = "price_of_part")
    private Double priceOfPart;
    
    @ManyToMany( fetch = FetchType.LAZY)
    private List<Technique> techniques;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }
}
