package io.hamibot.dcgateway.utils.hamibot;

import io.hamibot.dcgateway.adapter.DiscordAdapter;
import io.hamibot.dcgateway.util.SocketData;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class HamibotSocketData extends SocketData {
    public long lastHeartbeat;
    public DiscordAdapter discordAdapter;
    public List<JSONObject> resumedEvents = new ArrayList<>();
}
