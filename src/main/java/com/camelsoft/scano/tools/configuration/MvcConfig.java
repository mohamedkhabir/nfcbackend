package com.camelsoft.scano.tools.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        registry.jsp("/src/main/WEB-INF/jsp/", ".jsp");
    }


    public @Override void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        exposeDirectory("WebContent", registry);
        exposeDirectory("/WebContent/course_videos_file", registry);


        registry.addResourceHandler("/**").addResourceLocations("");
        registry.addResourceHandler("**/swagger-ui.html/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/STATIC/**")
                .addResourceLocations("classpath:/STATIC/");
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());


    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file://"+ uploadPath + "/").setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }



}
