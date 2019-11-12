package YccStudio.YccWebSocket;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import YccStudio.YccWebSocket.config.RootConfig;
import YccStudio.YccWebSocket.config.WebConfig;

//使用注解驅動的配置初始化器
public class WsWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
