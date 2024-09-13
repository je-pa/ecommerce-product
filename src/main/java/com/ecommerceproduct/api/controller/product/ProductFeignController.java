package com.ecommerceproduct.api.controller.product;

import com.ecommerceproduct.api.controller.product.dto.request.UpdateQuantityByProductOptionsDto;
import com.ecommerceproduct.api.service.product.ProductFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v0/products")
@RequiredArgsConstructor
public class ProductFeignController {
  private final ProductFeignService productFeignService;

  @PutMapping("/options/stock")
  void updateProductStock(@RequestBody UpdateQuantityByProductOptionsDto dto){
    productFeignService.adjustQuantity(dto);
  }
}
