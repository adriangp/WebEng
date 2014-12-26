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
public class RemoveEndPoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		try {
			session.getBasicRemote().sendText("remove: " + RemoveServlet.toDo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		String op = message.split(": ")[0];
		logger.info("Received ...." + message);
		switch (op) {
		case "remove":
			RemoveServlet.status = "200: Ok";
			break;
		default:
			RemoveServlet.status = "500: Internal Server Error";
			break;
		}
		RemoveServlet.response = message.split(": ")[1];
		RemoveServlet.latch.countDown();
		return message;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s",
				session.getId(), closeReason));
		RemoveServlet.latch.countDown();
	}
}
