package top.inson.springboot.core;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;
import top.inson.springboot.base.ITkBaseMapper;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "top.inson.springboot.cluster.dao",
        sqlSessionTemplateRef = "clusterSqlSessionTemplate", markerInterface = ITkBaseMapper.class)
public class ClusterDruidConfiguration {
    @Value("${mybatis.config-location}")
    private String configLocation;
    @Value("${mybatis.cluster.typeAliasesPackage}")
    private String typeAliasesPackage;
    @Value("${mybatis.cluster.mapperLocations}")
    private String mapperLocations;

    @Value("${spring.datasource.cluster.url}")
    private String url;

    @Value("${spring.datasource.cluster.username}")
    private String username;

    @Value("${spring.datasource.cluster.password}")
    private String password;

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource clusterDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    //session工厂
    @Bean
    public SqlSessionFactory clusterSqlSessionFactory(
            @Qualifier("clusterDataSource") DataSource clusterDataSource) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //数据源连接
        factoryBean.setDataSource(clusterDataSource);

        factoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        //指定entity和mapper的路径
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(mapperLocations));

        return factoryBean.getObject();
    }

    //会话管理
    @Bean
    public SqlSessionTemplate clusterSqlSessionTemplate(
            @Qualifier("clusterSqlSessionFactory") SqlSessionFactory clusterSqlSessionFactory){
        SqlSessionTemplate template = new SqlSessionTemplate(clusterSqlSessionFactory);
        return template;
    }

    //事务管理
    @Bean
    public DataSourceTransactionManager clusterTransactionManager(
            @Qualifier("clusterDataSource") DataSource clusterDataSource){
        return new DataSourceTransactionManager(clusterDataSource);
    }

}
