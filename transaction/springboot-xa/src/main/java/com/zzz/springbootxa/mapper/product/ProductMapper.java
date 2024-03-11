package com.zzz.springbootxa.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
@Mapper
public interface ProductMapper {
    /**
     * 更新商品库存
     *
     * @param productId: 商品ID
     * @param count      : 添加或者减少的商品数量
     */
    void updateStock(@Param("productId") Integer productId, @Param("count") Integer count);
}
