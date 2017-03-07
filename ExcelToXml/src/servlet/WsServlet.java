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
		System.out.println("收到客户端的请求。。。。");
		session.getBasicRemote().sendText("与服务端链接成功。。。。。");
	}
	
    @OnMessage
	public void OnMessage(Session session) throws IOException{
		session.getId();
		session.isOpen();
		session.getBasicRemote().sendText("与服务端链接成功。。。。。");
	}
}
