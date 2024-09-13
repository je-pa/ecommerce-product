package com.ecommerceproduct.api.service.product;

import com.ecommerceproduct.api.controller.product.dto.request.UpdateQuantityByProductOptionsDto;
import com.ecommerceproduct.api.controller.product.dto.request.UpdateQuantityByProductOptionsDto.ProductOptionInfo;
import com.ecommerceproduct.domain.product.entity.Product;
import com.ecommerceproduct.domain.product.entity.ProductOption;
import com.ecommerceproduct.domain.product.repository.ProductOptionRepository;
import com.ecommerceproduct.domain.product.repository.ProductRepository;
import com.ecommerceproduct.global.exception.CustomException;
import com.ecommerceproduct.global.exception.ExceptionCode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductFeignServiceImpl implements ProductFeignService{
  private final ProductOptionRepository productOptionRepository;

  @Override
  @Transactional
  public void adjustQuantity(UpdateQuantityByProductOptionsDto dto) {
    HashMap<Long, Integer> optionQuantity = new HashMap<>();
    for (ProductOptionInfo info : dto.productOptionInfos()) {
      optionQuantity.put(info.getProductOptionId(), info.getQuantity());
    }
    if (optionQuantity.size() != dto.productOptionInfos().size()) {
      throw CustomException.from(ExceptionCode.PRODUCT_OPTION_DUPLICATE);
    }

    List<ProductOption> options = productOptionRepository.findAllById(optionQuantity.keySet());
    Set<Product> products = new HashSet<>();

    for (ProductOption option : options) {
      int quantity = optionQuantity.get(option.getId());
      if (option.getCount() + quantity < 0) {
        throw CustomException.from(ExceptionCode.INSUFFICIENT_STOCK);
      }
      option.addQuantity(quantity);
      products.add(option.getProduct());
    }

    for (Product product : products) {
      int totalQuantity = productOptionRepository.findAllByProduct(product)
          .stream()
          .mapToInt(ProductOption::getCount)
          .sum();
      product.setQuantity(totalQuantity);
    }
  }
}
