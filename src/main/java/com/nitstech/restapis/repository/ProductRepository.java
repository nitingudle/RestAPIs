package com.nitstech.restapis.repository;

import com.nitstech.restapis.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
