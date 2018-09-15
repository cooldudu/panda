package com.wms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.CacheControl;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import  org.springframework.validation.Validator;
import org.springframework.web.reactive.resource.VersionResourceResolver;
import org.springframework.web.reactive.result.view.HttpMessageWriterView;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;



@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer{
    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
        builder.build();
    }
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker().suffix(".html");
        var encoder = new Jackson2JsonEncoder();
        registry.defaultViews(new HttpMessageWriterView(encoder));
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        var configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/static");
        var settings = new Properties();
        settings.put("default_encoding", "UTF-8");
        settings.put("output_encoding", "UTF-8");
        configurer.setFreemarkerSettings(settings);
        return configurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/console/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageable() {
        return new PageableHandlerMethodArgumentResolver();
    }

}
