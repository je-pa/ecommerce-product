/**
 * @Date : 2024. 08. 24.
 * @author : jieun(je-pa)
 */
package com.ecommerceproduct.api.service.product;

import com.ecommerceproduct.api.controller.product.dto.request.ReadProductListRequest;
import com.ecommerceproduct.api.controller.product.dto.response.ProductDetailResponse;
import com.ecommerceproduct.api.controller.product.dto.response.ProductListItemResponse;
import com.ecommerceproduct.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public ProductDetailResponse get(Long productId) {
    return ProductDetailResponse.from(productRepository.findWithOptions(productId));
  }

  @Override
  public Slice<ProductListItemResponse> getProductSlice(ReadProductListRequest request) {
    return productRepository.findListBy(request);
  }

}
