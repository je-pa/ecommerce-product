/**
 * @Date : 2024. 08. 25.
 * @author : jieun(je-pa)
 */
package com.ecommerceproduct.api.controller.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerceproduct.api.controller.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductControllerTest extends ControllerTestSupport {

  @DisplayName("상품 상세 정보를 조회한다.")
  @Test
  void getProduct() throws Exception {
    // given
    Long productId = 1L;

    // when
    // then
    mockMvc.perform(
        get("/api/v0/products/"+productId)
    ).andDo(print())
        .andExpect(status().isOk());

  }

  @DisplayName("상품 리스트를 조회한다.")
  @Test
  void getProductSlice() throws Exception {
    // given
    // when
    // then
    mockMvc.perform(
            get("/api/v0/products")
        ).andDo(print())
        .andExpect(status().isOk());
  }
}