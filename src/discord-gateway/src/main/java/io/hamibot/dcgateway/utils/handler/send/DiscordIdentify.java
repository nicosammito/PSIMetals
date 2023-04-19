package io.hamibot.dcgateway.utils.handler.send;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.adapter.DiscordAdapter;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import org.json.JSONObject;

public class DiscordIdentify implements SendHandler {
	@Override
	public void createMessage(WebSocket webSocket, SocketData socketData) {

		DiscordSocketData discordSocketData = (DiscordSocketData) socketData;

		int[] shardingInfo = {socketData.shardingId, socketData.shardingCount};
		JSONObject payload = new JSONObject();
		payload.put("op", 2)
				.put("d", new JSONObject().put("token", discordSocketData.token)
						.put("properties", new JSONObject()
								.put("$os", "browser")
								.put("$browser", "chrome")
								.put("$device", "cloud9")
						)
						.put("compress", false)
						.put("shard", shardingInfo)
						.put("intents", 1999) //includes everything except Typing and Presence (everything: 32767));
				);
		DiscordAdapter discordAdapter = (DiscordAdapter) socketData.adapter;
		discordAdapter.send(payload, true);
	}
}
