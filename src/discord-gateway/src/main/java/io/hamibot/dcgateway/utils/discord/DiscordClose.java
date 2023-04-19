package io.hamibot.dcgateway.utils.discord;

public enum DiscordClose {

    // Default close codes
    DISCONNECTED(1000, false, true),

    // Discord specific close codes
    UNKNOWN_ERROR(4000, false, true),
    UNKNOWN_OPCODE(4001, true, true),
    DECODE_ERROR(4002, true, true),
    NOT_AUTHENTICATED(4003, false, true),
    AUTHENTICATION_FAILED(4004, false, false),
    ALREADY_AUTHENTICATED(4005, true, true),
    INVALID_SEQ(4007, false, true),
    RATE_LIMITED(4008, true, true),
    SESSION_TIMED_OUT(4009, false, true),
    INVALID_SHARD(4010, false, false),
    SHARDING_REQUIRED(4011, false, false),
    INVALID_API_VERSION(4012, false, false),
    INVALID_INTENTS(4013, false, false),
    DISALLOWED_INTENTS(4014, false, false),

    UNKNOWN(-1, false, true);

    public final int code;
    public final boolean resume;
    public final boolean reconnect;

    DiscordClose(int code, boolean resume, boolean reconnect) {
        this.code = code;
        this.resume = resume;
        this.reconnect = reconnect;
    }

    public static DiscordClose findByCode(int code) {
        for (DiscordClose value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
