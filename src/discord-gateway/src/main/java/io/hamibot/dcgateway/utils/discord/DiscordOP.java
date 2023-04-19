package io.hamibot.dcgateway.utils.discord;

import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.utils.handler.receive.*;
import io.hamibot.dcgateway.utils.handler.send.DiscordHeartbeatSend;
import io.hamibot.dcgateway.utils.handler.send.DiscordIdentify;
import io.hamibot.dcgateway.utils.handler.send.DiscordResume;


public enum DiscordOP {

	DISPATCH(0, new DiscordDispatch(), null),
	HEARTBEAT(1, new DiscordHeartbeatReceive(), new DiscordHeartbeatSend()),
	IDENTIFY(2, null, new DiscordIdentify()),
	PRESENCE_UPDATE(3, null, null),
	VOICE_STATE_UPDATE(4, null, null),
	RESUME(6, null, new DiscordResume()),
	RECONNECT(7, new DiscordReconnect(), null),
	REQUEST_GUILD_MEMBERS(8, null, null),
	INVALID_SESSION(9, new DiscordInvalidSession(), null),
	HELLO(10, new DiscordHello(), null),
	HEARTBEAT_ACK(11, new DiscordHeartbeatAck(), null);

	public final int code;
	public final ReceiveHandler recHandler;
	public final SendHandler sendHandler;

	DiscordOP(int code, ReceiveHandler recHandler, SendHandler sendHandler) {
		this.code = code;
		this.recHandler = recHandler;
		this.sendHandler = sendHandler;
	}

	public static DiscordOP findByCode(int code) {
		for (DiscordOP value : values()) {
			if (value.code == code) {
				return value;
			}
		}
		return null;
	}
}
