package hongrok.spring.project1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import hongrok.spring.project1.data.dto.ProductDto;
import hongrok.spring.project1.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)    //test하고자하는 class를 집어넣음
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean       //mock개체 생성
    ProductServiceImpl productService;

    @Test       //test의 주체
    @DisplayName("Product 데이터 가져오기 테스트")    //test에 대한 설명
    void getProductTest() throws Exception {
        //given:mock개체를 사용하는 bddmockito라는 라이브러리에서 가져온 함수
        //given은 mock개체가 특정 상황에서 해야하는 행위를 정의하는 method이다
        given(productService.getProduct("12315")).willReturn(
                //해당 작업은 ProductDto를 받고, 아래와 같은 객체를 생성해서 return함
                new ProductDto("15871", "pen", 5000, 2000)
        );

        String productId = "12315";

        //RestApitest를 할 수 있는 환경을 생성해줌
        mockMvc.perform(
                get("/api/v1/product-api/product/" + productId)) //get,post등의 방법 중 get을 사용하게 해 줌
                .andExpect(status().isOk())                                 //andExpect: 다음의 기대하는 값이 나왔는지 확인
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());        //test 결과를 출력

        verify(productService).getProduct("12315"); //verify: 실제 해당 프로세스가 실행되었는지 확인
    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws Exception {

        given(productService.saveProduct("15871", "pen", 5000, 2000)).willReturn(
                new ProductDto("15871", "pen", 5000, 2000)
        );

        ProductDto productDto = ProductDto.builder().productId("15871").productName("pen")
                .productPrice(5000).productStock(2000).build();
        Gson gson = new Gson();
        String content = gson.toJson(productDto);
//        위의 작업은 아래의 코드로 대체할 수 있음
//        String json = new ObjectMapper().writeValueAsString(productDto);

        mockMvc.perform(
                post("/api/v1/product-api/product")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());

        verify(productService).saveProduct("15871", "pen", 5000, 2000);
    }
}
