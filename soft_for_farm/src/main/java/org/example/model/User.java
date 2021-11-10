package org.example.model;


import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 2, max = 40, message = "Login must be more than 2 and no more than 40 characters")
    private String login;
    
    @Size(min = 8, message = "Password must be more than 8 characters")
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Farm> farms;
    
    @CreatedDate
    private Date created;
    
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    public User() {
    
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public List<Farm> getFarms() {
        return farms;
    }
    
    public void setFarms(List<Farm> farms) {
        this.farms = farms;
    }
    
    public Date getCreated() {
        return created;
    }
    
    public void setCreated(Date created) {
        this.created = created;
    }
}
