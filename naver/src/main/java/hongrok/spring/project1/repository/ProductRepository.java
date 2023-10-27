package hongrok.spring.project1.repository;

import hongrok.spring.project1.data.entity.ProductEntity;
import io.lettuce.core.GeoArgs;
import io.lettuce.core.dynamic.annotation.Param;
import org.hibernate.validator.internal.metadata.provider.ProgrammaticMetaDataProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    //조회
    List<ProductEntity> findByProductName(String name);
    List<ProductEntity> queryByProductName(String name);

    //존재 유무
    boolean existsByProductName(String name);

    //쿼리 결과 개수
    long countByProductName(String name);

    //삭제
    void deleteByProductId(String id);
    long removeByProductId(String id);

    //값 개수 제한
    List<ProductEntity> findFirst5ByProductName(String name);
    List<ProductEntity> findTop3ByProductName(String name);

    /*쿼리 메소드의 조건자 키워드*/

    //Is,Equals, ...
    ProductEntity findByProductIdIs(String id);
    ProductEntity findByProductIdEquals(String id);

    //(Is) Not
    List<ProductEntity> findByProductIdNot(String id);
    List<ProductEntity> findByProductIdIsNot(String id);

    //(Is) Null
    List<ProductEntity> findByProductStockIsNull();
    List<ProductEntity> findByProductStockIsNotNull();

    // And, Or
    List<ProductEntity> findTopByProductIdAndProductName(String id, String name);

    //(Is) GreaterThan, LessThan, Between
    List<ProductEntity> findByProductPriceGreaterThan(Integer price);

    //(Is) Like, Containing, StartingWith, EndingWith
    List<ProductEntity> findByProductNameContaining(String name);


    //정렬과 페이징
    //Asc : 오름차순, Desc: 내림차순
    //재고 오름차순
    List<ProductEntity> findByProductNameContainingOrderByProductStockAsc(String name);
    //재고 내림차순
    List<ProductEntity> findByProductNameContainingOrderByProductStockDesc(String name);

    //여러 정렬 기준 사용 - 가격 오름차순, 재고 내림차순
    List<ProductEntity> findByProductNameContainingOrderByProductPriceAscProductStockDesc(String name);

    //매개변수를 이용한 정렬
    //특정 string을 포함하는 것을 찾고 sort하기
    List<ProductEntity> findByProductNameContaining(String name, Sort sort);

    //페이징 처리하기
    //특정값보다 큰 가격을 가지는 물건, pageable
    List<ProductEntity> findByProductPriceGreaterThan(Integer price, Pageable pageable);



    /*쿼리(@Query) 사용하기*/
    //query가 할당된 함수는 그 이름에 그다지 의미가 없다 -> 아무렇게나 지어도 되긴 함
    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > 2000")
    List<ProductEntity> findByProductPriceBasis();

    @Query(value = "SELECT * FROM ProductEntity p WHERE p.productPrice > 2000", nativeQuery = true)
    List<ProductEntity> findByProductPriceNative();

    @Query("SELECT p from ProductEntity p WHERE p.productPrice > ?1")
    List<ProductEntity> findByProductPriceWithParameter(Integer price);

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :price")
    List<ProductEntity> findByProductPriceWithParameterNaming(Integer price);

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :pri")
    List<ProductEntity> findByProductPriceWithParameterRenaming(@Param("pri") Integer price);

    @Query(value = "SELECT * FROM product WHERE price > :price",
    countQuery = "SELECT count(*) FROM product WHERE price > ?1",
    nativeQuery = true)
    List<ProductEntity> findByProductPriceWithParameterPaging(Integer price, Pageable pageable);
}

