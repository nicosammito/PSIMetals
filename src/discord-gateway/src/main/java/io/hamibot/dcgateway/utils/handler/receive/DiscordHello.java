package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.utils.discord.DiscordOP;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DiscordHello implements ReceiveHandler {
	final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

	@Override
	public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {

		DiscordSocketData discordSocketData = (DiscordSocketData) socketData;
		int interval = message.getJSONObject("d").getInt("heartbeat_interval");
		discordSocketData.keepAlive = exec.scheduleAtFixedRate(() -> DiscordOP.HEARTBEAT.sendHandler.createMessage(webSocket, socketData), 0, interval, TimeUnit.MILLISECONDS);
	}
}
