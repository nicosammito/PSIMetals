package io.hamibot.dcgateway.utils.discord;

import io.hamibot.dcgateway.adapter.HamibotAdapter;
import io.hamibot.dcgateway.util.SocketData;
import java.util.concurrent.atomic.AtomicInteger;

public final class DiscordSocketData extends SocketData {
	public final AtomicInteger seqNumber = new AtomicInteger();
	public final AtomicInteger missedHeartbeatAcks = new AtomicInteger();
	public HamibotAdapter hamibotAdapter;
	public String token;
}
