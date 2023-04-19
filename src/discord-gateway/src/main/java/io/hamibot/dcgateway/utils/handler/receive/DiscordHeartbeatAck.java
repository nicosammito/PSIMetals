package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import org.json.JSONObject;

public class DiscordHeartbeatAck implements ReceiveHandler {


    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {
        DiscordSocketData discordSocketData = (DiscordSocketData) socketData;
        discordSocketData.missedHeartbeatAcks.set(0);
    }
}
