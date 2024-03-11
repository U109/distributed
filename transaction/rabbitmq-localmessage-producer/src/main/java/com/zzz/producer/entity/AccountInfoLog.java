package com.zzz.producer.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author zhangzhongzhen
 * @TableName account_info_log
 */
@Data
public class AccountInfoLog {
    private Long id;

    private String accountNo;

    private String amount;

    private String txNo;

    private String status;

    private String otherNo;

}