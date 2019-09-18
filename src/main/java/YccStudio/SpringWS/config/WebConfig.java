package YccStudio.SpringWS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//SpringMvc和整個應用的配置

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={
    "YccStudio.SpringWS"
})
public class WebConfig extends WebMvcConfigurerAdapter {
    
    //配置視圖解析器
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resourceViewResolver=new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        resourceViewResolver.setExposeContextBeansAsAttributes(true);
        return resourceViewResolver;
    }

    //设置不攔截静態資源文件請求
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
