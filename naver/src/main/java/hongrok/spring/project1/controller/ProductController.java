package hongrok.spring.project1.controller;

import hongrok.spring.project1.Project1Application;
import hongrok.spring.project1.common.Constants;
import hongrok.spring.project1.common.exception.Project1Exception;
import hongrok.spring.project1.data.dto.ProductDto;
import hongrok.spring.project1.data.entity.ProductEntity;
import hongrok.spring.project1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/product-api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product/{productId}")
    public ProductDto getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }

    @PostMapping(value = "/product")
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto){ //@Valid를 붙여야만 dto에서 설정한 validation(@Max등)이 실행됨
        String productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int productPrice = productDto.getProductPrice();
        int productStock = productDto.getProductStock();

        //옛날 방식 그러나 특수한 validadtion의 경우 이렇게 구현해야할 수도 있다.
        /*
        if (productDto.getProductId().equals("") || productDto.getProductId().isEmpty()){
            Logger.error("[createProduct] failed Response :: productId is Empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(productDto);
        }*/
        return productService.saveProduct(productId, productName, productPrice, productStock);
    }

    @DeleteMapping(value = "/product/{productId}")
    public ProductDto DeleteProduct(@PathVariable String productId){
        return null;
    }

    @PostMapping(value = "/product/exception")
    public void exeptionTest() throws Project1Exception {
        throw new Project1Exception(Constants.ExceptionClass.PRODUCT, HttpStatus.BAD_REQUEST, "의도한 에러 발생");
    }
}
