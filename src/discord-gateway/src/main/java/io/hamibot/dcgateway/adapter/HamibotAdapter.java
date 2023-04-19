package io.hamibot.dcgateway.adapter;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;
import io.hamibot.dcgateway.core.ShardManager;
import io.hamibot.dcgateway.utils.hamibot.HamibotOP;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public class HamibotAdapter extends WebSocketAdapter {

    public final HamibotSocketData socketData;

    public HamibotAdapter(HamibotSocketData socketData) {
        this.socketData = socketData;
        this.socketData.adapter = this;
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
        HamibotOP.IDENTIFY.sendHandler.createMessage(websocket, socketData);
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) {
        JSONObject message = new JSONObject(text);
        if (socketData.debug) System.out.println("Hamibot -> [ " + socketData.shardingId + " / " + socketData.shardingCount + " ] " + message);

        HamibotOP gatewayOp = HamibotOP.findByCode(message.getInt("operation"));
        assert gatewayOp != null;
        gatewayOp.recHandler.handleMessage(websocket, message, socketData);

    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {

        if(socketData.keepAlive != null) {
            socketData.keepAlive.cancel(false);
            socketData.keepAlive = null;
        }

        if (socketData.debug) System.out.println("Hamibot -> [ " + socketData.shardingId + " / " + socketData.shardingCount + " ] Closed because of " + clientCloseFrame.getCloseReason() + " | " + clientCloseFrame.getCloseCode());

        ShardManager.restartHamibot((HamibotAdapter) socketData.adapter);
    }

    public void send(JSONObject message) {

        if (socketData.debug) {
            System.out.println("Hamibot <- [ " + socketData.shardingId + " / " + socketData.shardingCount + " ] " + message.toString());
        }
        socketData.socket.sendText(message.toString());
        //TODO: handle dropped messages
    }
}
