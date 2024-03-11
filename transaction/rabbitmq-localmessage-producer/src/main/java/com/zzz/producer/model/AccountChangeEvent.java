package com.zzz.producer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangzhongzhen wrote on 2024/3/10
 * @version 1.0
 * @description:
 */
@Data
@NoArgsConstructor
public class AccountChangeEvent {

    private String accountNo;
    private Double amount;
    private String txNo;
    private String password;

    public AccountChangeEvent(String accountNo, Double amount, String txNo, String password) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.txNo = txNo;
        this.password = password;
    }
}
