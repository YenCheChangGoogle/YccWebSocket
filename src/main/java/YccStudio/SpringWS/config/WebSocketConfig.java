package YccStudio.SpringWS.config;

import YccStudio.SpringWS.ws.EchoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//配置WebSocket

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // withSockJS 聲明啟用支持 sockJS
        webSocketHandlerRegistry.addHandler(echoHandler(), "/echo").withSockJS();
    }

    @Bean
    public WebSocketHandler echoHandler() {
        return new EchoHandler();
    }
}
