package com.alibou.security.sharing;

import com.alibou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByUser(User user);
}
