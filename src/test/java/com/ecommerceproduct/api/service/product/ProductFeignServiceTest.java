package com.ecommerceproduct.api.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ecommerceproduct.IntegrationTestSupport;
import com.ecommerceproduct.api.controller.product.dto.request.UpdateQuantityByProductOptionsDto;
import com.ecommerceproduct.domain.product.entity.Product;
import com.ecommerceproduct.domain.product.entity.ProductOption;
import com.ecommerceproduct.domain.product.repository.ProductOptionRepository;
import com.ecommerceproduct.domain.product.repository.ProductRepository;
import com.ecommerceproduct.domain.product.type.OptionType;
import com.ecommerceproduct.domain.product.type.ProductCategory;
import com.ecommerceproduct.domain.store.entity.Store;
import com.ecommerceproduct.domain.store.repository.StoreRepository;
import com.ecommerceproduct.global.exception.CustomException;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductFeignServiceTest extends IntegrationTestSupport {
  @Autowired
  private ProductFeignService productFeignService;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private ProductOptionRepository productOptionRepository;

  @AfterEach
  void tearDown() {
    productOptionRepository.deleteAllInBatch();
    productRepository.deleteAllInBatch();
    storeRepository.deleteAllInBatch();
  }

  @DisplayName("상품 옵션 재고를 조정한다.")
  @Test
  void adjustQuantity(){
    // given
    Store store1 = storeRepository.save(createStore("storeName1"));

    int option1Quantity = 5;
    int option2Quantity = 7;
    int option3Quantity = 9;
    int totalCount = option1Quantity + option2Quantity + option3Quantity;
    Product product1 = productRepository.save(
        createProduct("상품1", store1, totalCount));

    ProductOption option1 = productOptionRepository.save(
        createProductOption("옵션1-1", option1Quantity, product1));
    ProductOption option2 = productOptionRepository.save(
        createProductOption("옵션1-2", option2Quantity, product1));
    ProductOption option3 = productOptionRepository.save(
        createProductOption("옵션1-3", option3Quantity, product1));

    // when
//    productService.adjustQuantity(new UpdateQuantityByProductOptionsEvent(List.of(
//        new Info(option1.getId(), 2),
//        new Info(option2.getId(), 1)
//    )));

    productFeignService.adjustQuantity(new UpdateQuantityByProductOptionsDto(List.of(
        new UpdateQuantityByProductOptionsDto.ProductOptionInfo(option1.getId(), 2),
        new UpdateQuantityByProductOptionsDto.ProductOptionInfo(option2.getId(), 1)
    )));

    // then
    assertThat(productOptionRepository.findAll())
        .extracting("name", "count")
        .containsExactlyInAnyOrder(
            AssertionsForClassTypes.tuple("옵션1-1", 7),
            AssertionsForClassTypes.tuple("옵션1-2", 8),
            AssertionsForClassTypes.tuple("옵션1-3", option3Quantity)
        );
    assertThat(productRepository.findById(product1.getId()).get())
        .extracting("name", "stockQuantity")
        .contains("상품1", option1Quantity + 2 + option2Quantity + 1 + option3.getCount());

  }

  @DisplayName("상품 옵션id가 중복이면 안된다.")
  @Test
  void adjustQuantityWithProductOptionDuplicate(){
    // given
    Store store1 = storeRepository.save(createStore("storeName1"));

    int option1Quantity = 5;
    int option2Quantity = 7;
    int option3Quantity = 9;
    int totalCount = option1Quantity + option2Quantity + option3Quantity;
    Product product1 = productRepository.save(
        createProduct("상품1", store1, totalCount));

    ProductOption option1 = productOptionRepository.save(
        createProductOption("옵션1-1", option1Quantity, product1));

    // when
    // then
//    assertThatThrownBy(() ->productService.adjustQuantity(new UpdateQuantityByProductOptionsEvent(List.of(
//        new Info(option1.getId(), 2),
//        new Info(option1.getId(), 1)
//    )))).isInstanceOf(CustomException.class)
//        .hasMessage("중복된 상품 옵션이 있습니다.");
    assertThatThrownBy(() -> productFeignService.adjustQuantity(new UpdateQuantityByProductOptionsDto(List.of(
        new UpdateQuantityByProductOptionsDto.ProductOptionInfo(option1.getId(), 2),
        new UpdateQuantityByProductOptionsDto.ProductOptionInfo(option1.getId(), 1)
    )))).isInstanceOf(CustomException.class)
        .hasMessage("중복된 상품 옵션이 있습니다.");

  }

  @DisplayName("재고가 모자르면 안된다.")
  @Test
  void adjustQuantityWithInsufficientStock(){
    // given
    Store store1 = storeRepository.save(createStore("storeName1"));

    int option1Quantity = 0;
    int option2Quantity = 7;
    int option3Quantity = 9;
    int totalCount = option1Quantity + option2Quantity + option3Quantity;
    Product product1 = productRepository.save(
        createProduct("상품1", store1, totalCount));

    ProductOption option1 = productOptionRepository.save(
        createProductOption("옵션1-1", option1Quantity, product1));
    ProductOption option2 = productOptionRepository.save(
        createProductOption("옵션1-2", option2Quantity, product1));

    // when
    // then
    assertThatThrownBy(() -> productFeignService.adjustQuantity(new UpdateQuantityByProductOptionsDto(List.of(
        new UpdateQuantityByProductOptionsDto.ProductOptionInfo(option1.getId(), -2),
        new UpdateQuantityByProductOptionsDto.ProductOptionInfo(option2.getId(), -1)
    )))).isInstanceOf(CustomException.class)
        .hasMessage("재고가 충분하지 않습니다.");

  }

  private ProductOption createProductOption(String name, int quantity, Product product) {
    return ProductOption.builder()
        .product(product)
        .count(quantity)
        .name(name)
        .optionType(OptionType.MANDATORY)
        .price(10000)
        .build();
  }

  private Product createProduct(String name, Store store, int quantity){
    return createProduct(name, quantity, ProductCategory.TOP, store, 10000);
  }

  private Product createProduct(String name, int quantity, ProductCategory category, Store store, int price) {
    return Product.builder()
        .name(name)
        .stockQuantity(quantity)
        .store(store)
        .category(category)
        .info("상품정보")
        .thumbnailImgUrl("url")
        .price(price)
        .build();
  }

  private Store createStore(String name){
    return Store.builder()
        .name(name)
        .tellNumber("encrypted")
        .info("정보")
        .build();
  }
}