package io.hamibot.dcgateway.util;

import com.neovisionaries.ws.client.WebSocket;


public interface SendHandler {
	void createMessage(WebSocket webSocket, SocketData socketData);
}
