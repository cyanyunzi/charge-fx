package app.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author ：林雾
 * @date ：2020/07/28
 * @description :
 */
@Configuration
public class MybatisPlusConfig {

  @Bean("sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource);
    // 开启XML
    sqlSessionFactory.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*Mapper.xml"));

    MybatisConfiguration configuration = new MybatisConfiguration();

    // 开启XML
    configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    configuration.setMapUnderscoreToCamelCase(true);
    configuration.setCacheEnabled(false);
    sqlSessionFactory.setConfiguration(configuration);
    return sqlSessionFactory.getObject();
  }
}
