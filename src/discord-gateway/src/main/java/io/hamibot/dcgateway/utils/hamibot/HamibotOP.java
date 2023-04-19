package io.hamibot.dcgateway.utils.hamibot;

import io.hamibot.dcgateway.util.ReceiveHandler;
import io.hamibot.dcgateway.util.SendHandler;
import io.hamibot.dcgateway.utils.handler.receive.HamibotHeartbeatReceive;
import io.hamibot.dcgateway.utils.handler.receive.HamibotHello;
import io.hamibot.dcgateway.utils.handler.send.HamibotHeartbeat;
import io.hamibot.dcgateway.utils.handler.send.HamibotIdentify;

public enum HamibotOP {

    IDENTIFY(2, new io.hamibot.dcgateway.utils.handler.receive.HamibotIdentify(), new HamibotIdentify()),
    HELLO(10, new HamibotHello(), null),
    HEARTBEAT(1, null, new HamibotHeartbeat()),
    HEARTBEAT_RECEIVE(11, new HamibotHeartbeatReceive(), null);

    public final int code;
    public final ReceiveHandler recHandler;
    public final SendHandler sendHandler;

    HamibotOP(int code, ReceiveHandler recHandler, SendHandler sendHandler) {
        this.code = code;
        this.recHandler = recHandler;
        this.sendHandler = sendHandler;
    }

    public static HamibotOP findByCode(int code) {
        for (HamibotOP value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }
}
