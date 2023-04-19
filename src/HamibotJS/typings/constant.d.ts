export class BitField<S extends string, N extends number | bigint = number> {
    public constructor(bits?: BitFieldResolvable<S, N>);
    public bitfield: N;
    public add(...bits: BitFieldResolvable<S, N>[]): BitField<S, N>;
    public any(bit: BitFieldResolvable<S, N>): boolean;
    public equals(bit: BitFieldResolvable<S, N>): boolean;
    public freeze(): Readonly<BitField<S, N>>;
    public has(bit: BitFieldResolvable<S, N>): boolean;
    public missing(bits: BitFieldResolvable<S, N>, ...hasParams: readonly unknown[]): S[];
    public remove(...bits: BitFieldResolvable<S, N>[]): BitField<S, N>;
    public toArray(): S[];
    public valueOf(): N;
    public static FLAGS: Record<string, number | bigint>;
    public resolve(bit?: BitFieldResolvable<string, number | bigint>): number | bigint;
}

export type BitFieldResolvable<T extends string, N extends number | bigint> =
    | RecursiveReadonlyArray<T | N | `${bigint}` | Readonly<BitField<T, N>>>
    | T
    | N
    | `${bigint}`
    | Readonly<BitField<T, N>>;

export type RecursiveReadonlyArray<T> = ReadonlyArray<T | RecursiveReadonlyArray<T>>;

export class UserFlags extends BitField<UserFlagsString>{
    public static FLAGS: Record<UserFlagsString, number>;
}

export class Permissions extends BitField<PermissionFlagsString> {
    public static FLAGS: Record<PermissionFlagsString, number>;
}

export class MessageFlags extends BitField<MessageFlagsString>{
    public static FLAGS: Record<MessageFlagsString, number>;
}

export type ImageFormat =
    | 'png'
    | 'jpg'
    | 'jpeg'
    | 'gif';

export type PermissionFlagsString =
    | 'CREATE_INSTANT_INVITE'
    | 'KICK_MEMBERS'
    | 'BAN_MEMBERS'
    | 'ADMINISTRATOR'
    | 'MANAGE_CHANNELS'
    | 'MANAGE_GUILD'
    | 'ADD_REACTIONS'
    | 'VIEW_AUDIT_LOG'
    | 'PRIORITY_SPEAKER'
    | 'STREAM'
    | 'VIEW_CHANNEL'
    | 'SEND_MESSAGES'
    | 'SEND_TTS_MESSAGES'
    | 'MANAGE_MESSAGES'
    | 'EMBED_LINKS'
    | 'ATTACH_FILES'
    | 'READ_MESSAGE_HISTORY'
    | 'MENTION_EVERYONE'
    | 'USE_EXTERNAL_EMOJIS'
    | 'VIEW_GUILD_INSIGHTS'
    | 'CONNECT'
    | 'SPEAK'
    | 'MUTE_MEMBERS'
    | 'DEAFEN_MEMBERS'
    | 'MOVE_MEMBERS'
    | 'USE_VAD'
    | 'CHANGE_NICKNAME'
    | 'MANAGE_NICKNAMES'
    | 'MANAGE_ROLES'
    | 'MANAGE_WEBHOOKS'
    | 'MANAGE_EMOJIS_AND_STICKERS'
    | 'REQUEST_TO_SPEAK'

export type UserFlagsString =
    | 'NONE'
    | 'DISCORD_EMPLOYEE'
    | 'PARTNERED_SERVER_OWNER'
    | 'HYPESQUAD_EVENTS'
    | 'BUGHUNTER_LEVEL_1'
    | 'HOUSE_BRAVERY'
    | 'HOUSE_BRILLIANCE'
    | 'HOUSE_BALANCE'
    | 'EARLY_SUPPORTER'
    | 'TEAM_USER'
    | 'BUGHUNTER_LEVEL_2'
    | 'VERIFIED_BOT'
    | 'EARLY_VERIFIED_BOT_DEVELOPER'
    | 'DISCORD_CERTIFIED_MODERATOR';

export type MessageFlagsString =
    | 'CROSSPOSTED'
    | 'IS_CROSSPOST'
    | 'SUPPRESS_EMBEDS'
    | 'SOURCE_MESSAGE_DELETED'
    | 'URGENT'
    | 'HAS_THREAD'
    | 'EPHEMERAL'
    | 'LOADING';

export const enum ChannelTypes {
    GUILD_TEXT = 0,
    GUILD_VOICE = 2,
    GUILD_CATEGORY = 4,
    GUILD_NEWS = 5,
    GUILD_STORE = 6,
    GUILD_NEWS_THREAD = 10,
    GUILD_PUBLIC_THREAD = 11,
    GUILD_PRIVATE_THREAD = 12,
    GUILD_STAGE_VOICE = 13
}

export const enum VideoQualityModes {
    AUTO = 1,
    FULL = 2
}

export const enum MessageTypes {
    DEFAULT = 0,
    RECIPIENT_ADD = 1,
    RECIPIENT_REMOVE = 2,
    CALL = 3,
    CHANNEL_NAME_CHANGE = 4,
    CHANNEL_ICON_CHANGE = 5,
    CHANNEL_PINNED_MESSAGE = 6,
    GUILD_MEMBER_JOIN = 7,
    USER_PREMIUM_GUILD_SUBSCRIPTION = 8,
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1 = 9,
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2 = 10,
    USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3 = 11,
    CHANNEL_FOLLOW_ADD = 12,
    GUILD_DISCOVERY_DISQUALIFIED = 14,
    GUILD_DISCOVERY_REQUALIFIED = 15,
    GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING = 16,
    GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING = 17,
    REPLY = 19,
    GUILD_INVITE_REMINDER = 22,

}