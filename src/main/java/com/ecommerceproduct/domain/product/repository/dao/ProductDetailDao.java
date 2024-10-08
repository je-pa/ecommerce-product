/**
 * @Date : 2024. 08. 24.
 * @author : jieun(je-pa)
 */
package com.ecommerceproduct.domain.product.repository.dao;

import com.ecommerceproduct.domain.product.type.OptionType;
import com.ecommerceproduct.domain.product.type.ProductCategory;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public record ProductDetailDao(
    Long id,
    String name,
    StoreInfo store,
    int quantity,
    ProductCategory category,
    String thumbnailImgUrl,
    int basePrice,
    LocalDateTime createdDateTime,
    ProductOptionInfo option
) {

  @QueryProjection
  public ProductDetailDao {
    // QueryProjection 을 위한 생성자
  }

  public record StoreInfo(
      Long storeId,
      String name
  ) {

    @QueryProjection
    public StoreInfo {
      // QueryProjection 을 위한 생성자
    }
  }

  public record ProductOptionInfo(
      Long optionId,
      String name,
      int count,
      int price,
      OptionType optionType
  ){
    @QueryProjection
    public ProductOptionInfo {
      // QueryProjection 을 위한 생성자
    }
  }
}
