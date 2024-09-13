package com.ecommerceproduct.api.controller.product.dto.request;

import java.util.Collection;

public record UpdateQuantityByProductOptionsDto(
    Collection<ProductOptionInfo> productOptionInfos
) {
  public record ProductOptionInfo (Long getProductOptionId, int getQuantity){
  }
}
