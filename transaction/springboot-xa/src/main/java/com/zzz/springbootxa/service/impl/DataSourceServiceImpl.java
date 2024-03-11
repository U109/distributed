package com.zzz.springbootxa.service.impl;

import com.zzz.transactions.xa.datasource.annotations.RefreshDependency;
import com.zzz.springbootxa.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
@Service
@RefreshDependency
public class DataSourceServiceImpl implements DataSourceService {


    @Autowired
    private Map<String, DataSource> dataSourceMap;

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;


    @Override
    public void registerDataSource(DataSource dataSource) {
        defaultListableBeanFactory.registerSingleton("localDataSource", dataSource);
        Map<String, Object> beansMap = defaultListableBeanFactory.getBeansWithAnnotation(RefreshDependency.class);
        beansMap.values().forEach(bean -> defaultListableBeanFactory.autowireBean(bean));
    }

    @Override
    public Map<String, DataSource> getDataSource() {
        return dataSourceMap;
    }
}
