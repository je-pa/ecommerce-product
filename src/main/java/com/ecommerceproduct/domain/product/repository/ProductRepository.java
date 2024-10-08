/**
 * @Date : 2024. 08. 24.
 * @author : jieun(je-pa)
 */
package com.ecommerceproduct.domain.product.repository;

import com.ecommerceproduct.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryDslRepository {

}
