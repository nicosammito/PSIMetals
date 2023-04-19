package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;
import org.json.JSONObject;

import java.util.Date;

public class HamibotHeartbeatReceive implements ReceiveHandler {
    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {

        ((HamibotSocketData) socketData).lastHeartbeat = new Date().getTime();
    }
}
