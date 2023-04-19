const BitField = require("./BitField");

/**
 * Data structure that makes it easy to interact with a {@link Guild#_systemChannelFlags} bitfield.
 * @extends {BitField}
 */
class SystemChannelFlags extends BitField{}

SystemChannelFlags.FLAGS = {
    SUPPRESS_JOIN_NOTIFICATIONS: 1 << 0,
    SUPPRESS_PREMIUM_SUBSCRIPTIONS: 1 << 1,
    SUPPRESS_GUILD_REMINDER_NOTIFICATIONS: 1 << 2
}

module.exports = SystemChannelFlags;