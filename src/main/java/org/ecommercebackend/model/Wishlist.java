package org.ecommercebackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wishlist")
public class Wishlist {

    @EmbeddedId
    private WishlistPK id = new WishlistPK();

    @ManyToOne
    @MapsId("customer_id") //This is the name of attr in EmployerDeliveryAgentPK class
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name ="date_ajout")
    private Date dateAjout;

    public WishlistPK getId() {
        return id;
    }

    public void setId(WishlistPK id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }
}
