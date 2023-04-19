package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.ReceiveHandler;
import org.json.JSONObject;

public class DiscordReconnect implements ReceiveHandler {

    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {

        // closing the socket is enough
        // reconnecting is handled in DiscordAdapter when we disconnected
        webSocket.sendClose(4900, "RECONNECT");
    }
}
