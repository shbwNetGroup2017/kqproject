package servlet;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/test")
public class WsServlet{
    
	@OnOpen
	public void OnOpen(Session session) throws IOException{
		System.out.println("�յ��ͻ��˵����󡣡�����");
		session.getBasicRemote().sendText("���������ӳɹ�����������");
	}
	
    @OnMessage
	public void OnMessage(Session session) throws IOException{
		session.getId();
		session.isOpen();
		session.getBasicRemote().sendText("���������ӳɹ�����������");
	}
}
