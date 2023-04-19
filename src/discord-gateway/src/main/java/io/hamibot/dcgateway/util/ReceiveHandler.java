package io.hamibot.dcgateway.util;

import com.neovisionaries.ws.client.WebSocket;
import org.json.JSONObject;

public interface ReceiveHandler {
	void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData);
}
