package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.adapter.HamibotAdapter;
import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;
import org.json.JSONObject;

import java.util.Date;

public class HamibotIdentify implements ReceiveHandler {
    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {
        ((HamibotSocketData) socketData).resumedEvents.forEach(event -> ((HamibotAdapter) socketData.adapter).send(event));
    }
}
