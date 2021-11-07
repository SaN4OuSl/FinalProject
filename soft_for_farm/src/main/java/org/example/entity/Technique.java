package org.example.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "techniques")
public class Technique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, name = "type_of_technique")
    private String typeOfTechnique;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "price_of_parts")
    private Double priceOfParts;
    
    @NotNull
    @DecimalMin("0.00")
    @Column(name = "price_of_lubricants")
    private Double priceOfLubricant;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTypeOfTechnique() {
        return typeOfTechnique;
    }

    public void setTypeOfTechnique(String typeOfTechnique) {
        this.typeOfTechnique = typeOfTechnique;
    }
}
