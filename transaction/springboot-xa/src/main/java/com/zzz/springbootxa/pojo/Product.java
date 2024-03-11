package com.zzz.springbootxa.pojo;

import lombok.Data;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
@Data
public class Product {

    //商品ID
    private Integer productId;
    //商品价格
    private Double price;
    //商品库存
    private Integer stock;

}
