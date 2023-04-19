package io.hamibot.dcgateway.utils.handler.send;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.adapter.HamibotAdapter;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;
import org.json.JSONObject;

import java.util.Date;

public class HamibotHeartbeat implements SendHandler {

    @Override
    public void createMessage(WebSocket webSocket, SocketData socketData) {

        HamibotSocketData hamibotSocketData = (HamibotSocketData) socketData;
        HamibotAdapter hamibotAdapter = (HamibotAdapter) socketData.adapter;

        if((new Date().getTime() - hamibotSocketData.lastHeartbeat) > 120*1000){
            webSocket.sendClose(4005, "TIMED_OUT");
        }
        JSONObject payload = new JSONObject();
        payload.put("operation", 1);
        hamibotAdapter.send(payload);

    }
}
