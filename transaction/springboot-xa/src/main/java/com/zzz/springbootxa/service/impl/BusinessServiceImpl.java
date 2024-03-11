package com.zzz.springbootxa.service.impl;


import com.zzz.transactions.xa.mapper.order.OrderMapper;
import com.zzz.transactions.xa.mapper.product.ProductMapper;
import com.zzz.springbootxa.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
@Service
public class BusinessServiceImpl implements BusinessService {

//    @Autowired
//    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;

//    @Transactional
    @Override
    public void placeOrder(Integer productId, Integer count) {
//        orderMapper.createOrder(productId);
//        int i = 1 / 0;
//        productMapper.updateStock(productId, count);
    }
}
