package com.ecommerceproduct.api.service.product;

import com.ecommerceproduct.api.controller.product.dto.request.UpdateQuantityByProductOptionsDto;

public interface ProductFeignService {
  /**
   * 상품 및 상품 옵션 재고를 조정합니다.
   * @param dto 상품 옵션id와 조정할 quantity를 리스트로 받습니다.
   */
  void adjustQuantity(UpdateQuantityByProductOptionsDto dto);
}
