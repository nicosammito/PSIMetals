package io.hamibot.dcgateway.core;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import io.hamibot.dcgateway.adapter.DiscordAdapter;
import io.hamibot.dcgateway.adapter.HamibotAdapter;
import io.hamibot.dcgateway.util.Sockets;
import io.hamibot.dcgateway.utils.discord.DiscordSocketData;
import io.hamibot.dcgateway.utils.hamibot.HamibotSocketData;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class ShardManager {

    private static final ConcurrentLinkedQueue<DiscordSocketData> discordQueued = new ConcurrentLinkedQueue<>();
    private static final AtomicReference<ScheduledFuture<?>> discordCurrentLoginProcess = new AtomicReference<>(null);
    private static final ConcurrentLinkedQueue<HamibotSocketData> hamibotQueued = new ConcurrentLinkedQueue<>();
    private static final AtomicReference<ScheduledFuture<?>> hamibotCurrentLoginProcess = new AtomicReference<>(null);
    private static final ScheduledExecutorService exec = Executors.newScheduledThreadPool(0);

    /**
     * creates a new connection to hamibot as well as discord
     *
     * @param discordSocketData discord socket data
     * @param hamibotSocketData hamibot socket data
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    public static void createConnection(DiscordSocketData discordSocketData, HamibotSocketData hamibotSocketData) {
        try {
            //create new Hamibot socket clients
            WebSocket hamibotSocket = new WebSocketFactory().createSocket(Sockets.SOCKET_HAMIBOT_URI);
            HamibotAdapter hamibotAdapter = new HamibotAdapter(hamibotSocketData);
            hamibotSocket.addListener(hamibotAdapter);
            hamibotSocketData.socket = hamibotSocket;

            //create new Discord socket client
            WebSocket socket = new WebSocketFactory().createSocket(Sockets.SOCKET_DISCORD_URI);
            DiscordAdapter discordAdapter = new DiscordAdapter(discordSocketData);
            socket.addListener(discordAdapter);
            discordSocketData.socket = socket;
            discordSocketData.hamibotAdapter = hamibotAdapter;
            discordSocketData.hamibotAdapter.socketData.discordAdapter = discordAdapter;

            //start both clients
            startHamibot(hamibotSocketData);
            startDiscord(discordSocketData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * restarts the given discord client
     *
     * @param adapter discord event catcher
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    public static void restartDiscord(DiscordAdapter adapter) {
        try {
            adapter.socketData.socket = adapter.socketData.socket.recreate();
            startDiscord(adapter.socketData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * starts the given discord client by his docket data
     *
     * @param socketData discord socket data
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    private static void startDiscord(DiscordSocketData socketData) {
        discordQueued.add(socketData);
        if (discordCurrentLoginProcess.get() == null) {
            discordCurrentLoginProcess.set(exec.scheduleAtFixedRate(ShardManager::runDiscordQueue, 0, 6, TimeUnit.SECONDS)); // scheduled because of identify ratelimit
        }
    }
    /**
     * starts the given hamibot client by his docket data
     *
     * @param socketData discord socket data
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    private static void startHamibot(HamibotSocketData socketData) {
        hamibotQueued.add(socketData);
        if (hamibotCurrentLoginProcess.get() == null) {
            hamibotCurrentLoginProcess.set(exec.scheduleAtFixedRate(ShardManager::runHamibotQueue, 0, 1, TimeUnit.SECONDS));
        }
    }

    /**
     * loops throw all waiting restart attempts in queue
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    private static void runDiscordQueue() {
        if(discordQueued.size() == 0) {
            discordCurrentLoginProcess.get().cancel(false);
            discordCurrentLoginProcess.set(null);
            return;
        }

        DiscordSocketData socketData = discordQueued.poll();

        connectDiscord(socketData);

        discordQueued.forEach(data -> {
            if(data.sessionId != null) { // Resume can be connected regardless of ratelimit
                connectDiscord(socketData);
            }
        });
    }

    /**
     * loops throw all waiting restart attempts in queue
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    private static void runHamibotQueue() {
        if(hamibotQueued.size() == 0) {
            hamibotCurrentLoginProcess.get().cancel(false);
            hamibotCurrentLoginProcess.set(null);
            return;
        }

        connectHamibot(hamibotQueued.poll());
    }

    /**
     * try's to connect to discord websocket server
     *
     * @param socketData given socket data from discord client
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    private static void connectDiscord(DiscordSocketData socketData) {
        try {
            WebSocket socket = socketData.socket;
            if(socket.isOpen()) {
                System.err.println("["+socketData.shardingId+"/"+socketData.shardingCount+"] Cannot reconnect in the " + socket.getState().name() + " state!");
                return;
            }
            socket.connect();
        } catch (Exception e) {
            restartDiscord((DiscordAdapter) socketData.adapter);
        }
    }

    /**
     * restarts the given hamibot client
     *
     * @param adapter discord event catcher
     * @throws IOException throw exception when something went wrong
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    public static void restartHamibot(HamibotAdapter adapter) {

        try {
            adapter.socketData.socket = adapter.socketData.socket.recreate();
            startHamibot(adapter.socketData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * try's to connect to hamibot websocket server
     *
     * @param hamibotSocketData given socket data from hamibot client
     *
     * @version Alpha 1.0.0
     * @author Nico Sammito
     */
    private static void connectHamibot(HamibotSocketData hamibotSocketData){
        WebSocket socket = hamibotSocketData.socket;

        //check if connection is already established
        if(socket.isOpen()) {
            if (hamibotSocketData.debug) System.err.println("["+hamibotSocketData.shardingId+"/"+hamibotSocketData.shardingCount+"] Cannot reconnect in the " + socket.getState().name() + " state!");
            return;
        }

        //connection isn't open so try to connect
        try {
            socket.connect();
        } catch (WebSocketException e) {
            if (hamibotSocketData.debug) System.out.println("Hamibot -> [ " + hamibotSocketData.shardingId + " / " + hamibotSocketData.shardingCount + " ] Attempt to reconnect or connect to Hamibot Backend failed will try again in 1 Minute!");

            //Try to connect again after 1 Minute
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    restartHamibot((HamibotAdapter) hamibotSocketData.adapter);
                }
            }, 60*1000);
        }
    }
}
