package org.example.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    
    @Column(unique = true)
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    public void addUser(User user) {
        this.users.add(user);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}