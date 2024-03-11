package com.zzz.producer.service;

import com.zzz.producer.entity.AccountInfo;
import com.zzz.producer.model.AccountChangeEvent;

/**
* @author zhangzhongzhen
* @description 针对表【account_info】的数据库操作Service
* @createDate 2024-03-10 22:20:18
*/
public interface AccountInfoService {


    public void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent) throws Exception;

}
