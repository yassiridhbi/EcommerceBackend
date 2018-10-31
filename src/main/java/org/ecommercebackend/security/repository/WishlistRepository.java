package org.ecommercebackend.security.repository;


import org.ecommercebackend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface WishlistRepository  extends JpaRepository<Wishlist, WishlistPK> {

    List<Wishlist> findAllByCustomer(Customer customer);


}
