/**
 * @Date : 2024. 08. 24.
 * @author : jieun(je-pa)
 */
package com.ecommerceproduct.api.service.product;

import com.ecommerceproduct.api.controller.product.dto.request.ReadProductListRequest;
import com.ecommerceproduct.api.controller.product.dto.response.ProductDetailResponse;
import com.ecommerceproduct.api.controller.product.dto.response.ProductListItemResponse;
import org.springframework.data.domain.Slice;

public interface ProductService {

  /**
   * 상품의 상세 정보를 조회합니다.
   * @param productId 조회할 상품의 id
   * @return 상품 상세 정보
   */
  ProductDetailResponse get(Long productId);

  /**
   * 상품의 리스트를 필터링하여 조회합니다.
   * @param request 상품 필터링 정보
   * @return 페이징 기반 상품 리스트
   */
  Slice<ProductListItemResponse> getProductSlice(ReadProductListRequest request);

}
