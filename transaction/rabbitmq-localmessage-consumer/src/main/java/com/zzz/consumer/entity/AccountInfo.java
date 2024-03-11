package com.zzz.consumer.entity;

import lombok.Data;

/**
 * @author zhangzhongzhen
 * @TableName account_info
 */
@Data
public class AccountInfo {

    private Long id;

    private String accountName;

    private String accountNo;

    private String accountPassword;

    private Double accountBalance;

}