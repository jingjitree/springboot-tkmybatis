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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;
import top.inson.springboot.base.ITkBaseMapper;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "top.inson.springboot.cluster.dao",
        sqlSessionTemplateRef = "clusterSqlSessionTemplate", markerInterface = ITkBaseMapper.class)
public class ClusterDruidConfiguration {
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

    @Bean
    public SqlSessionFactory clusterSqlSessionFactory(
            @Qualifier("clusterDataSource") DataSource clusterDataSource) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        factoryBean.setConfiguration(configuration);
        //数据源连接
        factoryBean.setDataSource(clusterDataSource);

        //指定entity和mapper的路径
        factoryBean.setTypeAliasesPackage("top.inson.springboot.cluster.entity");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:clusterMapper/*.xml"));

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate clusterSqlSessionTemplate(
            @Qualifier("clusterSqlSessionFactory") SqlSessionFactory clusterSqlSessionFactory){
        SqlSessionTemplate template = new SqlSessionTemplate(clusterSqlSessionFactory);
        return template;
    }

    @Bean
    public DataSourceTransactionManager clusterTransactionManager(
            @Qualifier("clusterDataSource") DataSource clusterDataSource){
        return new DataSourceTransactionManager(clusterDataSource);
    }

}
