package io.hamibot.dcgateway.utils.handler.send;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.adapter.DiscordAdapter;
import io.hamibot.dcgateway.utils.discord.DiscordOP;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import org.json.JSONObject;

public class DiscordResume implements SendHandler {
    @Override
    public void createMessage(WebSocket webSocket, SocketData socketData) {
        DiscordSocketData discordSocketData = (DiscordSocketData) socketData;
        JSONObject payload = new JSONObject()
                .put("op", DiscordOP.RESUME.code)
                .put("d", new JSONObject()
                        .put("token", discordSocketData.token)
                        .put("session_id", socketData.sessionId)
                        .put("seq", discordSocketData.seqNumber.get())
                );
        DiscordAdapter discordAdapter = (DiscordAdapter) socketData.adapter;
        discordAdapter.send(payload, true);
    }
}
