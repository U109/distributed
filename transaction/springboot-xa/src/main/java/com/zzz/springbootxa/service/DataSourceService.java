package com.zzz.springbootxa.service;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
public interface DataSourceService {


    void registerDataSource(DataSource dataSource);

    Map<String, DataSource> getDataSource();

}
