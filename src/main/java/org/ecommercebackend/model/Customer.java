package org.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.ecommercebackend.model.security.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.Thread;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@JsonIgnoreProperties({"user"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="ID")
    private User user;

    @Column(name = "FIRSTNAME", length = 50)
    private String firstname;

    @Column(name = "LASTNAME", length = 50)
    private String lastname;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "EMAIL", length = 50)
    @NotNull
    private String email;

    @OneToMany(mappedBy = "product")
    private Set<CustomerProduct> products = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Wishlist> wishlistProducts = new HashSet<>();



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer(){

    }



}
