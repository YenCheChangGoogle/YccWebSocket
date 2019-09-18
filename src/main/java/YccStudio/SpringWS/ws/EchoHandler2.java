package YccStudio.SpringWS.ws;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/echo2")
public class EchoHandler2 {
    
    //與客戶端連線已經建立
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("與客戶端連線已經建立: "+session.getId());
    }

    //客戶端連線關閉
    @OnClose
    public void onClose() {
        System.out.println("與客戶端連線即將關閉");
    }

    //接收到客戶端的訊息
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("接收到客戶端的訊息(javax.websocket.Session.getId()="+session.getId()+"): "+message);
        try { Thread.sleep(1000); } catch(InterruptedException e) { e.printStackTrace(); }
        session.getBasicRemote().sendText("Echo: "+message);        
    }

    //連接發送錯誤
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("連接發送錯誤");
        error.printStackTrace();
    }
}
