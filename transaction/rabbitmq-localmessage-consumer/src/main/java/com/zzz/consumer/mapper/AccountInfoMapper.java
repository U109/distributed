package com.zzz.consumer.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zhangzhongzhen
 * @description 针对表【account_info】的数据库操作Mapper
 * @createDate 2024-03-10 22:20:18
 * @Entity com.zzz.producer.entity.AccountInfo
 */
@Mapper
public interface AccountInfoMapper {

    @Update("update account_info set account_balance=account_balance+#{amount}  where account_no=#{otherNo}")
    int updateAccountBalance(@Param("otherNo") String otherNo, @Param("amount") Double amount);

    @Select("select count(1) from de_duplication where tx_no = #{txNo}")
    int isExistTx(String txNo);

    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(String txNo);

}




