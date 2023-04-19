package io.hamibot.dcgateway.utils.handler.send;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.adapter.DiscordAdapter;
import io.hamibot.dcgateway.utils.discord.DiscordOP;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import org.json.JSONObject;

public class DiscordHeartbeatSend implements SendHandler {

	@Override
	public void createMessage(WebSocket webSocket, SocketData socketData) {
		DiscordSocketData discordSocketData = (DiscordSocketData) socketData;
		if(discordSocketData.missedHeartbeatAcks.get() > 2) {
			System.err.println("[ "+socketData.shardingId+" / "+socketData.shardingCount+" ] Missed 2 heartbeats. Reconnecting!");
			// closing the socket is enough
			// reconnecting is handled in DiscordAdapter when we disconnected
			webSocket.sendClose(4900, "RECONNECT");
			return;
		}
		JSONObject payload = new JSONObject();
		payload.put("op", DiscordOP.HEARTBEAT.code)
				.put("d", discordSocketData.seqNumber);
		DiscordAdapter discordAdapter = (DiscordAdapter) socketData.adapter;
		discordAdapter.send(payload, true);
		discordSocketData.missedHeartbeatAcks.incrementAndGet();
	}
}
