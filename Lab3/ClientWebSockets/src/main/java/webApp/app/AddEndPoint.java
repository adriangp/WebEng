package webApp.app;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class AddEndPoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		try {
			session.getBasicRemote().sendText("add: " + AddServlet.toDo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		String op = message.split(": ")[0];
		logger.info("Received ...." + message);
		switch (op) {
		case "add":
			AddServlet.status = "201: Created";
			break;
		default:
			AddServlet.status = "500: Internal Server Error";
			break;
		}
		AddServlet.response = message.split(": ")[1];
		AddServlet.latch.countDown();
		return message;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s",
				session.getId(), closeReason));
		AddServlet.latch.countDown();
	}
}
