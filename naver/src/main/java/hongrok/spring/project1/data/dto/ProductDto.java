package hongrok.spring.project1.data.dto;

import hongrok.spring.project1.data.entity.ProductEntity;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

/*
@NotNull, @Min, @Max, @Size 등의 validation을 설정해줄 수 있다.
 */
public class ProductDto {

    @NotNull
    //@Size(min = 8)
    private String productId;

    @NotNull
    private String productName;

    @NotNull
    @Min(value = 500)
    @Max(value = 500000)
    private int productPrice;

    @Min(value = 0)
    @Min(value = 9999)
    private int productStock;

    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productStock(productStock)
                .build();
    }
}
