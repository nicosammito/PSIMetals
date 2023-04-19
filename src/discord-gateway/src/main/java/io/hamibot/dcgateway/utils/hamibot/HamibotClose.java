package io.hamibot.dcgateway.utils.hamibot;

public enum HamibotClose {

    // Default close codes
    DISCONNECTED(1000, true),

    // Discord specific close codes
    UNKNOWN_ERROR(4000, true),
    UNKNOWN_OPERATION(4001, true),
    NOT_AUTHENTICATED(4002, false),
    WRONG_FORMAT(4003, true),
    AUTHORIZATION_FAILED(4004, true),
    TIMED_OUT(4005, true),
    INVALID_SESSION(4006, true),
    NOT_SECURE_CONNECTION(4007, false);

    public final int code;
    public final boolean reconnect;

    HamibotClose(int code, boolean reconnect) {
        this.code = code;
        this.reconnect = reconnect;
    }

    public static HamibotClose findByCode(int code) {
        for (HamibotClose value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return UNKNOWN_ERROR;
    }
}
