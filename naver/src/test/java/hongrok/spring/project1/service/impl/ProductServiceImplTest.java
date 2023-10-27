package hongrok.spring.project1.service.impl;

import hongrok.spring.project1.data.dto.ProductDto;
import hongrok.spring.project1.data.entity.ProductEntity;
import hongrok.spring.project1.data.handler.impl.ProductDataHandlerImpl;
import hongrok.spring.project1.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.AutoConfigureDataCassandra;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;


@ExtendWith(SpringExtension.class)
@Import({ProductDataHandlerImpl.class})
public class ProductServiceImplTest {

    @MockBean
    ProductDataHandlerImpl productDataHandler;

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void getProductTest() {

        Mockito.when(productDataHandler.getProductEntity("123"))
                .thenReturn(new ProductEntity("123","pen",2000,5000));

        ProductDto productDto = productService.getProduct("123");

        Assertions.assertEquals(productDto.getProductId(), "123");

        verify(productDataHandler).getProductEntity("123");
    }

    @Test
    public void saveProductTest() {
        Mockito.when(productDataHandler.saveProductEntity("123", "pen", 5000, 2000))
                .thenReturn(new ProductEntity("123", "pen", 5000, 2000));

        ProductDto productDto = productService.saveProduct("123", "pen", 5000, 2000);

        Assertions.assertEquals(productDto.getProductId(), "123");

        verify(productDataHandler).saveProductEntity("123", "pen", 5000, 2000);
    }


}
