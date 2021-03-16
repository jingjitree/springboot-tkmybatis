package top.inson.springboot.core;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;
import top.inson.springboot.base.ITkBaseMapper;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "top.inson.springboot.dao",
        sqlSessionTemplateRef = "sqlSessionTemplate", markerInterface = ITkBaseMapper.class)
public class DruidConfiguration {

    @Value("${spring.datasource.master.url}")
    private String url;

    @Value("${spring.datasource.master.username}")
    private String username;

    @Value("${spring.datasource.master.password}")
    private String password;

    //druid控制台
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet(){
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //白名单
        registrationBean.addInitParameter("allow", "127.0.0.1");
        //黑名单
        //servletRegistrationBean.addInitParameter("deny", "");
        registrationBean.addInitParameter("loginUsername","admin");
        registrationBean.addInitParameter("loginPassword","123456");
        registrationBean.addInitParameter("resetEnable","false");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> druidStatFilter(){
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        //添加过滤规则
        registrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
        return registrationBean;
    }


    @Bean(initMethod = "init", destroyMethod = "close")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setConfiguration(new org.apache.ibatis.session.Configuration());
        //使用数据源连接数据库
        factoryBean.setDataSource(dataSource);
        //指定entity和mapper的xml路径
        factoryBean.setTypeAliasesPackage("top.inson.springboot.entity");
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
        return factoryBean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }

    //事务管理
    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(
            @Qualifier("dataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
