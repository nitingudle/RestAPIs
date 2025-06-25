package com.nitstech.restapis.service;

import com.nitstech.restapis.domain.Product;
import com.nitstech.restapis.exception.ProductNotFoundException;
import com.nitstech.restapis.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product p) {
        return repo.save(p);
    }

    public Product update(Long id, Product updated) {
        Product existing = repo.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        BeanUtils.copyProperties(updated, existing, "id");
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
