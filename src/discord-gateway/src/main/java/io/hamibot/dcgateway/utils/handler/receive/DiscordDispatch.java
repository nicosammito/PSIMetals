package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.core.ShardManager;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import org.json.JSONObject;

import java.util.Objects;

public class DiscordDispatch implements ReceiveHandler {

    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {

        //check if event is type Ready event and set session id
        if (Objects.equals(message.getString("t"), "READY")) {
            socketData.sessionId = message.getJSONObject("d").getString("session_id");
            return;
        }

        //this check is required because RESUMED events are not used by backend
        if (Objects.equals(message.getString("t"), "RESUMED")) return;

        //build event
        JSONObject payload = new JSONObject();
        payload.put("operation", 0);
        payload.put("content", message);

        //check if connection to hamibot is open
        if (!((DiscordSocketData) socketData).hamibotAdapter.socketData.socket.isOpen()) {
            ((DiscordSocketData) socketData).hamibotAdapter.socketData.resumedEvents.add(payload);
            return;
        }

        //send message to gateway
        ((DiscordSocketData) socketData).hamibotAdapter.send(payload);

    }
}
