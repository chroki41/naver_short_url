package hongrok.spring.project1.data.dao;

import hongrok.spring.project1.data.entity.ProductEntity;

public interface ProductDAO {

    ProductEntity saveProduct(ProductEntity productEntity);

    ProductEntity getProduct(String productId);
}

//interface에서는 함수를 선언만 하고 실제 작용은 따로 만들어줌