package io.hamibot.dcgateway.utils.handler.receive;

import com.neovisionaries.ws.client.WebSocket;
import io.hamibot.dcgateway.core.ShardManager;
import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.util.SocketData;
import io.hamibot.dcgateway.utils.hamibot.HamibotOP;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HamibotHello implements ReceiveHandler {
    final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void handleMessage(WebSocket webSocket, JSONObject message, SocketData socketData) {

        HamibotSocketData hamibotSocketData = (HamibotSocketData) socketData;
        hamibotSocketData.lastHeartbeat = new Date().getTime();
        hamibotSocketData.keepAlive = exec.scheduleAtFixedRate(() -> HamibotOP.HEARTBEAT.sendHandler.createMessage(webSocket, socketData), 60, 60, TimeUnit.SECONDS);
    }
}
