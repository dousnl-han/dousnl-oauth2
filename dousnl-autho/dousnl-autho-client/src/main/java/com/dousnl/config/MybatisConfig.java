package com.dousnl.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * springboot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages="com.dousnl.**.mapper")
public class MybatisConfig {
    
    @Autowired
    private Environment env;
    
    /**
     * 创建数据源
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错 
     */
    @Bean(name="dataSource")
    @Primary
    public DataSource getDataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.driverClassName"));
        props.put("url", env.getProperty("spring.datasource.url"));
        props.put("username", env.getProperty("spring.datasource.username"));
        props.put("password", env.getProperty("spring.datasource.password"));
        props.put("maxActive", env.getProperty("spring.datasource.max-active"));
        props.put("minIdle", env.getProperty("spring.datasource.min-idle"));
        props.put("initialSize", env.getProperty("spring.datasource.initial-size"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));//指定xml文件位置
        System.out.println("---------mybatis扫描开始----------");
        System.out.println("--------mybatis扫描xml位置:"+env.getProperty("mybatis.mapper-locations"));
        /*org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setLogImpl(Slf4jImpl.class);
        config.setLogPrefix("com.dousnl.**.mapper");
        fb.setConfiguration(config);*/
        return fb.getObject();
    }
    
    // 创建事务管理器1
    /**
     * @param dataSource
     * <!-- 配置spring的PlatformTransactionManager，名字为默认值 -->  
     * @return
     */
    @Bean
    public DataSourceTransactionManager  txManager(@Qualifier("dataSource")DataSource dataSource) {
    	 System.out.println("----------------事务配置已加载.....--------------");
        return new DataSourceTransactionManager(dataSource);
    }

}
