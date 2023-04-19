package io.hamibot.dcgateway.util;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

import java.util.concurrent.ScheduledFuture;

public class SocketData {

    public String sessionId;
    public ScheduledFuture<?> keepAlive;
    public int shardingId;
    public int shardingCount;
    public WebSocketAdapter adapter;
    public WebSocket socket;
    public boolean debug;
}
