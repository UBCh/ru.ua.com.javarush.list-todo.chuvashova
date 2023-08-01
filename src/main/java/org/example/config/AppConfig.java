package org.example.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;


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
	registry.addResourceHandler("*/html/**").addResourceLocations("/html/");
	registry.addResourceHandler("*/style/**").addResourceLocations("*/style/*");
	registry.addResourceHandler("*/script/**").addResourceLocations("/script/");
    }


    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
	SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	templateResolver.setApplicationContext(this.applicationContext);
	templateResolver.setPrefix("/WEB-INF/jsp/");
	templateResolver.setSuffix(".jsp");
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


    @Bean
    public ViewResolver thymeleafViewResolver() {
	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	viewResolver.setViewNames(new String[]{"*-thymeleaf"});
	viewResolver.setTemplateEngine(thymeleafTemplateEngine());
	viewResolver.setCharacterEncoding("UTF-8");
	viewResolver.setCache(false);
	return viewResolver;
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/").setViewName("list");
    }
}
