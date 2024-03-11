package com.zzz.springbootxa.mapper.order;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
@Mapper
public interface OrderMapper {
    /**
     * 创建订单
     * @param productId: 商品ID
     */
    void createOrder(Integer productId);
}