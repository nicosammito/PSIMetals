package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.utils.discord.DiscordOP;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.ReceiveHandler;
import org.json.JSONObject;

public class DiscordHeartbeatReceive implements ReceiveHandler {
	@Override
	public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {
		DiscordOP.HEARTBEAT.sendHandler.createMessage(webSocket, socketData);
	}

}
