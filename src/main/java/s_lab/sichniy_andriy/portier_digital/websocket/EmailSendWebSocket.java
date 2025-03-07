package s_lab.sichniy_andriy.portier_digital.websocket;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class EmailSendWebSocket extends TextWebSocketHandler {

    private EmailSender emailSender;

    @Override
    public void afterConnectionEstablished(
            @NonNull WebSocketSession session
    ) throws Exception {
        emailSender = new EmailSender();
        System.out.println("Connected to websocket");
    }

    @Override
    protected void handleTextMessage(
            @NonNull final WebSocketSession session,
            @NonNull final TextMessage message
    ) throws Exception {
        try{
            String line = emailSender.sendEmail(message.getPayload());
            TextMessage response = new TextMessage(line);
            session.sendMessage(response);
        } catch (Exception e) {
            emailSender = null;
            throw e;
        }
    }

    @Override
    public void afterConnectionClosed(
            @NonNull final WebSocketSession session,
            @NonNull CloseStatus status
    ) throws Exception {
        System.out.println("Closed websocket");
        System.out.println(emailSender.result());
        emailSender = null;
    }

}
