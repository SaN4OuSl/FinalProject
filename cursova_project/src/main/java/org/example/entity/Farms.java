package org.example.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "farms")
public class Farms extends BaseEntity {

    @NaturalId
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long year;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Plants> plants;

    public Farms() {
        plants = new ArrayList<>();
    }

    public String getTitle() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Farms)) return false;
        Farms course = (Farms) o;
        return getTitle().equals(course.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
