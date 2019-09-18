package YccStudio.SpringWS.ws;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

//一個简单的回音訊息處理器, 無論客户端發送什麼訊息, 將同樣的訊息在前面加【Echo:】 然後回覆回去
public class EchoHandler extends AbstractWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("與客戶端連線已經建立: "+session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("接收到客戶端的訊息("+session.getId()+"): "+message.getPayload());
        Thread.sleep(1000);
        session.sendMessage(new TextMessage("Echo: "+message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("與客戶端連線即將關閉: "+session.getId());
    }
}
