package hongrok.spring.project1.service;

import hongrok.spring.project1.controller.ProductController;
import hongrok.spring.project1.data.dto.ProductDto;

public interface ProductService {

    ProductDto saveProduct(String productId, String productName, int productPrice, int productStock);

    ProductDto getProduct(String productId);
}
