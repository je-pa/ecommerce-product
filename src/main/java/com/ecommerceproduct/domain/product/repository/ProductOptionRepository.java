package com.ecommerceproduct.domain.product.repository;

import com.ecommerceproduct.domain.product.entity.Product;
import com.ecommerceproduct.domain.product.entity.ProductOption;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
  @EntityGraph(attributePaths = "product")
  List<ProductOption> findAllById(Iterable<Long> ids);

  List<ProductOption> findAllByProduct(Product product);
}
