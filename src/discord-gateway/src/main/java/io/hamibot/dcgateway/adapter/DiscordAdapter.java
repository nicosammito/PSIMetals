package io.hamibot.dcgateway.adapter;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;
import io.hamibot.dcgateway.core.ShardManager;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import io.hamibot.dcgateway.utils.discord.DiscordClose;
import io.hamibot.dcgateway.utils.discord.DiscordOP;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DiscordAdapter extends WebSocketAdapter {

    public final DiscordSocketData socketData;
    private final AtomicInteger sentMessages = new AtomicInteger();
    private long rateLimitReset = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60);

    public DiscordAdapter(DiscordSocketData socketData) {
        this.socketData = socketData;
        this.socketData.adapter = this;
    }

    /**
     * method is called when socket successfully connects to Discord
     *
     * @param websocket connected socket to discord
     * @param headers   all socket headers
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
        if (socketData.sessionId != null) {
            DiscordOP.RESUME.sendHandler.createMessage(websocket, socketData);
        } else {
            DiscordOP.IDENTIFY.sendHandler.createMessage(websocket, socketData);
        }
        socketData.missedHeartbeatAcks.set(0);
    }

    /**
     * method is called when we receive a message from discord
     *
     * @param websocket connected socket to discord
     * @param text      text that was send by connected server (discord)
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    @Override
    public void onTextMessage(WebSocket websocket, String text) {
        JSONObject message = new JSONObject(text);

        if (!Objects.equals(message.get("s").toString(), "null")) {
            if (message.getInt("s") != 0 && message.getInt("s") > socketData.seqNumber.get()) {
                socketData.seqNumber.set(message.getInt("s"));
            }
        }
        if (socketData.debug) {
            System.out.println("Discord -> [ " + socketData.shardingId + " / " + socketData.shardingCount + " ] " + message.toString());
        }

        DiscordOP gatewayOp = DiscordOP.findByCode(message.getInt("op"));
        assert gatewayOp != null;
        gatewayOp.recHandler.handleMessage(websocket, message, socketData);
    }

    /**
     * method is called when either server or client closed connection
     *
     * @param websocket        connected socket to discord
     * @param serverCloseFrame close error when server closed connection
     * @param clientCloseFrame close error when client closed connection
     * @param closedByServer   boolean weather client or server closed connection
     * @throws Exception throws exception when something went wrong
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {

        //clear heartbeat interval
        if (socketData.keepAlive != null) {
            socketData.keepAlive.cancel(false);
            socketData.keepAlive = null;
        }

        //check if session was closed by discord
        if (closedByServer && serverCloseFrame != null) {

            DiscordClose gatewayClose = DiscordClose.findByCode(serverCloseFrame.getCloseCode());

            // if socket should not resume or reconnect, fail completely
            // all fails here require manual action by us
            //
            // this is only the case when we get one of the following close codes
            // - AUTHENTICATION_FAILED (can't reconnect because token is invalid)
            // - INVALID_SHARD (can happen, if so we need to update application parameters)
            // - SHARDING_REQUIRED (should never happen, if so we need to update application parameters)
            // - INVALID_API_VERSION (this will require a review of the current code and the discord documentation)
            // - INVALID_INTENTS (if so, this requires an update of identify message)
            // - DISALLOWED_INTENTS (will require a verification by discord or removal of that intent in identify, can only be the GUILD_MEMBERS intent)
            if (!gatewayClose.resume && !gatewayClose.reconnect) {
                System.exit(-1); //currently, just exit program but later also add error handling
            }

            if (!gatewayClose.resume) {
                socketData.sessionId = null;
            }
        }

        //try restart connection
        ShardManager.restartDiscord((DiscordAdapter) socketData.adapter);
    }


    /**
     * @param message  message that should be sent to discord
     * @param priority send priority
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    public void send(JSONObject message, boolean priority) {

        if (socketData.debug) {
            System.out.println("Discord <- [ " + socketData.shardingId + " / " + socketData.shardingCount + " ] " + message.toString());
        }
        long now = System.currentTimeMillis();

        if (now > rateLimitReset) { // Rate Limit has reset
            sentMessages.set(0);
            rateLimitReset = now + TimeUnit.SECONDS.toMillis(60);
        }

        // 110 messages allowed
        // we leave 9 spots for priority messages
        if (sentMessages.get() <= 110 || (sentMessages.get() <= 119 && priority)) { // 120 messages allowed, -1 for safety
            socketData.socket.sendText(message.toString());
            sentMessages.incrementAndGet();
        } else {
            System.err.println("Dropped a WebSocket message due to rate limit");
        }

        //TODO: handle dropped messages
    }
}
