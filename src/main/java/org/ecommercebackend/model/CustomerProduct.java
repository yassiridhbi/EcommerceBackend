package org.ecommercebackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_product")
public class CustomerProduct {

    @EmbeddedId
    private CustomerProductPK id = new CustomerProductPK();

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

    @Column(name = "shipping_address")
    private String shippingAddress;

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CustomerProductPK getId() {
        return id;
    }

    public void setId(CustomerProductPK id) {
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
