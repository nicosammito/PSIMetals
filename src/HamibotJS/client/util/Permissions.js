'use strict';

const BitField = require('./BitField');

/**
 * Data structure that makes it easy to interact with a permission bitfield. All {@link GuildMember}s have a set of
 * permissions in their guild, and each channel in the guild may also have PermissionOverwrites for the member
 * that override their default permissions.
 * @extends {BitField}
 */
class Permissions extends BitField {
    /**
     * Bitfield of the packed bits
     * @type {bigint}
     * @name Permissions#bitfield
     */

    /**
     * Gets all given bits that are missing from the bitfield.
     * @param {BitFieldResolvable} bits Bit(s) to check for
     * @returns {string[]}
     */
    missing(bits) {
        return this.has(this.constructor.FLAGS.ADMINISTRATOR) ? [] : super.missing(bits);
    }

    /**
     * Checks whether the bitfield has a permission, or any of multiple permissions.
     * @param {BitFieldResolvable} permission Permission(s) to check for
     * @returns {boolean}
     */
    any(permission) {
        return super.has(this.constructor.FLAGS.ADMINISTRATOR) || super.any(permission);
    }

    /**
     * Checks whether the bitfield has a permission, or multiple permissions.
     * @param {BitFieldResolvable} permission Permission(s) to check for
     * @returns {boolean}
     */
    has(permission) {
        return super.has(this.constructor.FLAGS.ADMINISTRATOR) || super.has(permission);
    }
}

/**
 * Numeric permission flags. All available properties:
 * * `CREATE_INSTANT_INVITE` (create invitations to the guild)
 * * `KICK_MEMBERS`
 * * `BAN_MEMBERS`
 * * `ADMINISTRATOR` (implicitly has *all* permissions, and bypasses all channel overwrites)
 * * `MANAGE_CHANNELS` (edit and reorder channels)
 * * `MANAGE_GUILD` (edit the guild information, region, etc.)
 * * `ADD_REACTIONS` (add new reactions to messages)
 * * `VIEW_AUDIT_LOG`
 * * `PRIORITY_SPEAKER`
 * * `STREAM`
 * * `VIEW_CHANNEL`
 * * `SEND_MESSAGES`
 * * `SEND_TTS_MESSAGES`
 * * `MANAGE_MESSAGES` (delete messages and reactions)
 * * `EMBED_LINKS` (links posted will have a preview embedded)
 * * `ATTACH_FILES`
 * * `READ_MESSAGE_HISTORY` (view messages that were posted prior to opening Discord)
 * * `MENTION_EVERYONE`
 * * `USE_EXTERNAL_EMOJIS` (use emojis from different guilds)
 * * `VIEW_GUILD_INSIGHTS`
 * * `CONNECT` (connect to a voice channel)
 * * `SPEAK` (speak in a voice channel)
 * * `MUTE_MEMBERS` (mute members across all voice channels)
 * * `DEAFEN_MEMBERS` (deafen members across all voice channels)
 * * `MOVE_MEMBERS` (move members between voice channels)
 * * `USE_VAD` (use voice activity detection)
 * * `CHANGE_NICKNAME`
 * * `MANAGE_NICKNAMES` (change other members' nicknames)
 * * `MANAGE_ROLES`
 * * `MANAGE_WEBHOOKS`
 * * `MANAGE_EMOJIS_AND_STICKERS`
 * * `USE_APPLICATION_COMMANDS`
 * * `REQUEST_TO_SPEAK`
 * * `MANAGE_THREADS`
 * * `USE_PUBLIC_THREADS`
 * * `USE_PRIVATE_THREADS`
 * * `USE_EXTERNAL_STICKERS` (use stickers from different guilds)
 * @type {Object<string, bigint>}
 * @see {@link https://discord.com/developers/docs/topics/permissions#permissions-bitwise-permission-flags}
 */
Permissions.FLAGS = {
    CREATE_INSTANT_INVITE: 1n << 0n,
    KICK_MEMBERS: 1n << 1n,
    BAN_MEMBERS: 1n << 2n,
    ADMINISTRATOR: 1n << 3n,
    MANAGE_CHANNELS: 1n << 4n,
    MANAGE_GUILD: 1n << 5n,
    ADD_REACTIONS: 1n << 6n,
    VIEW_AUDIT_LOG: 1n << 7n,
    PRIORITY_SPEAKER: 1n << 8n,
    STREAM: 1n << 9n,
    VIEW_CHANNEL: 1n << 10n,
    SEND_MESSAGES: 1n << 11n,
    SEND_TTS_MESSAGES: 1n << 12n,
    MANAGE_MESSAGES: 1n << 13n,
    EMBED_LINKS: 1n << 14n,
    ATTACH_FILES: 1n << 15n,
    READ_MESSAGE_HISTORY: 1n << 16n,
    MENTION_EVERYONE: 1n << 17n,
    USE_EXTERNAL_EMOJIS: 1n << 18n,
    VIEW_GUILD_INSIGHTS: 1n << 19n,
    CONNECT: 1n << 20n,
    SPEAK: 1n << 21n,
    MUTE_MEMBERS: 1n << 22n,
    DEAFEN_MEMBERS: 1n << 23n,
    MOVE_MEMBERS: 1n << 24n,
    USE_VAD: 1n << 25n,
    CHANGE_NICKNAME: 1n << 26n,
    MANAGE_NICKNAMES: 1n << 27n,
    MANAGE_ROLES: 1n << 28n,
    MANAGE_WEBHOOKS: 1n << 29n,
    MANAGE_EMOJIS_AND_STICKERS: 1n << 30n,
    USE_APPLICATION_COMMANDS: 1n << 31n,
    REQUEST_TO_SPEAK: 1n << 32n,
    MANAGE_THREADS: 1n << 34n,
    USE_PUBLIC_THREADS: 1n << 35n,
    USE_PRIVATE_THREADS: 1n << 36n,
    USE_EXTERNAL_STICKERS: 1n << 37n,
};

/**
 * Bitfield representing every permission combined
 * @type {bigint}
 */
Permissions.ALL = Object.values(Permissions.FLAGS).reduce((all, p) => all | p, 0n);

/**
 * Bitfield representing the default permissions for users
 * @type {bigint}
 */
Permissions.DEFAULT = BigInt(104324673);

/**
 * Bitfield representing the permissions required for moderators of stage channels
 * @type {bigint}
 */
Permissions.STAGE_MODERATOR = Permissions.FLAGS.MANAGE_CHANNELS | Permissions.FLAGS.MUTE_MEMBERS | Permissions.FLAGS.MOVE_MEMBERS;

module.exports = Permissions;