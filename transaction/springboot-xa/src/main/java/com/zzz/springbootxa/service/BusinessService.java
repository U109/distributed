package com.zzz.springbootxa.service;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
public interface BusinessService {

    /**
     * 下单
     * @param productId: 商品ID
     * @param count    : 下单商品数量
     */
    void placeOrder(Integer productId,Integer count);

}
