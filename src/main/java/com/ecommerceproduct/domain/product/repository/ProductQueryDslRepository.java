/**
 * @Date : 2024. 08. 24.
 * @author : jieun(je-pa)
 */
package com.ecommerceproduct.domain.product.repository;

import com.ecommerceproduct.api.controller.product.dto.request.ReadProductListRequest;
import com.ecommerceproduct.api.controller.product.dto.response.ProductListItemResponse;
import com.ecommerceproduct.domain.product.repository.dao.ProductDetailDao;
import java.util.List;
import org.springframework.data.domain.Slice;

public interface ProductQueryDslRepository {

  List<ProductDetailDao> findWithOptions(Long productId);

  Slice<ProductListItemResponse> findListBy(ReadProductListRequest request);
}
