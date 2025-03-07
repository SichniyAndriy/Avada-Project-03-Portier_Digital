package s_lab.sichniy_andriy.portier_digital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import s_lab.sichniy_andriy.portier_digital.websocket.EmailSendWebSocket;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new EmailSendWebSocket(), "/websocket/emails").setAllowedOrigins("*");
    }

}
