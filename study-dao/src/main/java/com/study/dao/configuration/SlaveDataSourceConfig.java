package com.study.dao.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * 数据源设置
 * @author 何小文
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = SlaveDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory", sqlSessionTemplateRef = "slaveSqlSessionTemplate")
@AutoConfigureAfter({SlaveDataSourceConfig.class})
public class SlaveDataSourceConfig {
	
	protected static final String PACKAGE = "org.study.dao.slave";
	protected static final String MAPPER_LOCATION = "classpath*:mybatis/*/*.xml";
	
	@Value("${spring.druid.slave.datasource.url}")
    private String url;

    @Value("${spring.druid.slave.datasource.username}")
    private String user;

    @Value("${spring.druid.slave.datasource.password}")
    private String password;

    @Value("${spring.druid.slave.datasource.driverClassName}")
    private String driverClass;
    
    @Value("${spring.druid.maxActive}")
    private int maxActive;
    
    @Value("${spring.druid.minIdle}")
    private int minIdel;
    
    @Value("${spring.druid.maxWait}")
    private long maxWait;
    
    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdel);
        dataSource.setMaxWait(maxWait);
        return dataSource;
    }
    
    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager slaveTransactionManager() {
        return new DataSourceTransactionManager(slaveDataSource());
    }
    @Bean(name = "slaveSqlSessionFactory")
     public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource)
            throws Exception {
    	 final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
         sessionFactory.setDataSource(slaveDataSource);
         sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SlaveDataSourceConfig.MAPPER_LOCATION));
         return sessionFactory.getObject();
    }
    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory slaveSqlSessionFactory) {
        return new SqlSessionTemplate(slaveSqlSessionFactory);
    }
}
