/*
 * Copyright 2013 longkai
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package gxu.software_engineering.shen10.market.core;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;


/**
 * spring的beans配置文件。
 * @author longkai
 * @email  im.longkai@gmail.com
 */
@Configuration
@ComponentScan("gxu.software_engineering.shen10.market")
@EnableTransactionManagement
//@EnableWebMvc // 假如不在web容器上测试的话，那么请注释掉此注解！
public class SpringBeans extends WebMvcConfigurerAdapter {

	private static final Logger L = LoggerFactory.getLogger(SpringBeans.class);
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		int cachePeriod = 3600 * 24 * 15;
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/favicon.ico").addResourceLocations("/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/robots.txt").addResourceLocations("/").setCachePeriod(cachePeriod);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/home").setViewName("index");
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false).ignoreAcceptHeader(false);
	}
	
//	@Bean
//	public DataSourceInitializer dataSourceInitializer() {
//		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//		populator.addScript(new ClassPathResource("/db/market.sql"));
//		
//		DataSourceInitializer initializer = new DataSourceInitializer();
//		initializer.setDataSource(dataSource());
//		initializer.setDatabasePopulator(populator);
//		return initializer;
//	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = this.getClass().getResourceAsStream("/db/dataSource.properties");
			props.load(in);
		} catch (IOException e) {
			L.error("启动数据源出错！", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				L.error("wtf!", e);
			}
		}
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(props.getProperty("db.driver"));
		dataSource.setUrl(props.getProperty("db.url"));
		dataSource.setUsername(props.getProperty("db.username"));
		dataSource.setPassword(props.getProperty("db.password"));
		dataSource.setDefaultAutoCommit(false);
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());

		Properties properties = new Properties();
		//properties.setProperty("hibernate.hbm2ddl.auto", "none");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		entityManagerFactoryBean.setJpaProperties(properties);
		entityManagerFactoryBean.setPackagesToScan("gxu.software_engineering.shen10.market.entity");
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(true);
//		jpaVendorAdapter.setShowSql(false);
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform(org.hibernate.dialect.MySQL5InnoDBDialect.class.getName());
//		这里不管怎么设置，实际上最终还是依赖于jpaProperties的相关设置
		jpaVendorAdapter.setGenerateDdl(false);
		return jpaVendorAdapter;
	}

	@Bean
	public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(true);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".html");
		viewResolver.setContentType("text/html;charset=utf-8");
		viewResolver.setRequestContextAttribute("request");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setExposeRequestAttributes(true);
		viewResolver.setExposeSessionAttributes(true);
		return viewResolver;
	}
	
	@Bean
	public FreeMarkerConfig freeMarkerConfig() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/WEB-INF/views/");
		Properties settings = new Properties();
		settings.setProperty("template_update_delay", "0");
		settings.setProperty("default_encoding", "UTF-8");
		settings.setProperty("number_format", "0.##");
		settings.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
		settings.setProperty("classic_compatible", "true");
		settings.setProperty("template_exception_handler", "ignore");
		configurer.setFreemarkerSettings(settings);
		return configurer;
	}
	
//	@Bean
//	public MultipartResolver multipartResolver() {
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		return resolver;
//	}

	@Bean
	public ContentNegotiationManager contentNegotiationManager() {
		Map<String, MediaType> mediaTypes = new HashMap<String, MediaType>(2);
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("jsonp", MediaType.parseMediaType("application/javascript"));
		PathExtensionContentNegotiationStrategy extension = new PathExtensionContentNegotiationStrategy(mediaTypes);
		HeaderContentNegotiationStrategy header = new HeaderContentNegotiationStrategy();
		ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager(extension, header);
		return contentNegotiationManager;
	}
	
	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setContentNegotiationManager(contentNegotiationManager());
		List<View> defaultViews = new ArrayList<View>(2);
		View jsonView = new MappingJacksonJsonView();
		View jsonpView = new MappingJacksonJsonpView();
		defaultViews.add(jsonView);
		defaultViews.add(jsonpView);
		viewResolver.setDefaultViews(defaultViews);
		return viewResolver;
	}
	
	@Bean
	public MethodValidationPostProcessor validationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
	

}
