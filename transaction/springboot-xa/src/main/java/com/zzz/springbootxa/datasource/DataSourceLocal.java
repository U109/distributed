package com.zzz.springbootxa.datasource;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.concurrent.Callable;

/**
 * @author zhangzhongzhen wrote on 2024/3/2
 * @version 1.0
 * @description:
 */
//@Configuration
//@MapperScan(basePackages = {"com.zzz.transactions.xa.mapper.order"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class DataSourceLocal  {

    @Value("${spring.datasource.local.jdbc-url}")
    private String url;
    @Value("${spring.datasource.local.username}")
    private String username;
    @Value("${spring.datasource.local.password}")
    private String password;
    @Value("${spring.datasource.local.driver-class-name}")
    private String driverClassName;

    /**
     * 创建order_db数据源
     */
    @Bean(name = "orderDataSource")
    @Primary
    public DataSource createOrderDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        DruidXADataSource dds = new DruidXADataSource();
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setUrl(url);
        dds.setDriverClassName(driverClassName);
        ds.setXaDataSource(dds);
        ds.setUniqueResourceName("orderDataSource");
        return ds;
    }

    @Primary
    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(@Qualifier(value = "orderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        //设置别名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.zzz.transactions.xa.pojo.order");
        //设置驼峰
        org.apache.ibatis.session.Configuration c = new org.apache.ibatis.session.Configuration();
        c.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(c);
        //设置映射接口的xml配置文件
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 创建SqlSessionTemplate
     */
    @Primary
    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
