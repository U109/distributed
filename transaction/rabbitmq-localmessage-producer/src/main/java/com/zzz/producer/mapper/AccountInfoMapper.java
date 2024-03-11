package com.zzz.producer.mapper;

import com.zzz.producer.entity.AccountInfo;
import com.zzz.producer.entity.AccountInfoLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author zhangzhongzhen
 * @description 针对表【account_info】的数据库操作Mapper
 * @createDate 2024-03-10 22:20:18
 * @Entity com.zzz.producer.entity.AccountInfo
 */
public interface AccountInfoMapper {

    @Update("update account_info set account_balance=account_balance-#{amount}  where account_no=#{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") Double amount);

    @Select("select count(1) from de_duplication where tx_no = #{txNo}")
    int isExistTx(String txNo);

    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(String txNo);

    @Insert("insert into account_info_log values(null,#{accountNo},#{amount},#{txNo},'Y','2');")
    int addAccountLog(@Param("accountNo") String accountNo, @Param("amount") Double amount, @Param("txNo") String txNo);

    @Update("update account_info_log set status = #{status} where txNo = #{txNo};")
    int updAccountLog(@Param("txNo") String txNo, @Param("status") String status);

    @Select("select * from account_info_log where status = 'N'")
    List<AccountInfoLog> selectFailAcount();

}




