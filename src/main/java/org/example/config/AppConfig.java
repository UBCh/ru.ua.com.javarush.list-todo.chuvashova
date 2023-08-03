package org.example.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;


@Configuration
@ComponentScan("org.example")
@PropertySource("classpath:application.properties")
public class AppConfig implements ApplicationContextAware, WebMvcConfigurer {

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/html/**").addResourceLocations("/html/");
	registry.addResourceHandler("*/style/**").addResourceLocations("*/style/*");
	registry.addResourceHandler("*/script/**").addResourceLocations("/script/");
    }


    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
	SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	templateResolver.setApplicationContext(this.applicationContext);
	templateResolver.setPrefix("/html/");
	templateResolver.setSuffix(".html");
	templateResolver.setTemplateMode(TemplateMode.HTML);
	templateResolver.setCacheable(false);
	templateResolver.setCharacterEncoding("UTF-8");
	return templateResolver;
    }


    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	templateEngine.setTemplateResolver(thymeleafTemplateResolver());
	templateEngine.setEnableSpringELCompiler(true);
	return templateEngine;
    }

//
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//	viewResolver.setTemplateEngine(thymeleafTemplateEngine());
//	return viewResolver;
//    }


    @Bean
    public ViewResolver thymeleafViewResolver() {
	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	viewResolver.setContentType("text/html; charset=UTF-8");
	viewResolver.setViewNames(new String[]{"*-thymeleaf"});
	viewResolver.setTemplateEngine(thymeleafTemplateEngine());
	viewResolver.setCharacterEncoding("UTF-8");
	viewResolver.setCache(false);
	return viewResolver;
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	converter.getObjectMapper().setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
	converter.getObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	converters.add(converter);
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/").setViewName("index");
    }
}
