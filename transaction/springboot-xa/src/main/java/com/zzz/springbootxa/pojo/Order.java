package com.zzz.springbootxa.pojo;

import lombok.Data;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
@Data
public class Order {
    //订单ID
    private Integer orderId;
    //商品ID
    private Integer productId;
}
