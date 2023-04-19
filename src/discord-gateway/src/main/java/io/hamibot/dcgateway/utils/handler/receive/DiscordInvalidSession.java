package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.util.ReceiveHandler;
import org.json.JSONObject;

public class DiscordInvalidSession implements ReceiveHandler {
    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {

        if(!message.getBoolean("d")) {
            // if not possible to resume, just set session to null
            // this will trigger an IDENTIFY message after reconnect
            socketData.sessionId = null;
        }
        // closing the socket is enough
        // reconnecting is handled in DiscordAdapter when we disconnected
        webSocket.sendClose(4900, "RECONNECT");
    }
}
