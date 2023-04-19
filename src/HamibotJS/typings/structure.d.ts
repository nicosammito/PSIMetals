import {
    ImageFormat,
    UserFlags,
    ChannelTypes,
    VideoQualityModes,
    Permissions,
    MessageTypes,
    MessageFlags
} from './constant';
import {CacheManager} from "./manager";

export class Guild {

    constructor(client, guild_id: string)
    public id: string
    public bans: CacheManager<GuildBan>
    public members: CacheManager<GuildMember>
    public channels: CacheManager<GuildChannel>
    public invites: CacheManager<Invite>
    public roles: CacheManager<GuildRole>
    public name: string | undefined
    public icon: string | undefined
    public splash: string | undefined
    public discoverySplash: string | undefined
    public ownerId: string | undefined

}

export class Invite {
    get channel(): GuildChannel
    get code(): string
    get createdAt(): Date
    get createdTimestamp(): number
    get expiresAt(): Date
    get expiresTimestamp(): number
    get guild(): Guild
    get inviter(): User
    get maxAge(): number
    get maxUses(): number
    get temporary(): boolean
    get uses(): number
    get onlineMemberCount(): number
    get allMemberCount(): number
}

export class User {

    constructor(client, user_id: string)
    get id(): number
    get username(): string
    get tag(): string
    get discriminator(): string
    get avatar(): string
    public avatarUrl(format: ImageFormat): string | null
    get banner(): string | null
    get bannerColor(): number | null
    public bannerUrl(format: ImageFormat): string | null
    get isBot(): boolean | null
    get isSystem(): boolean | null
    get activated2FA(): boolean | null
    get locale(): string | null
    get userFlags(): UserFlags | null

}

export class GuildMember {

    constructor(client, user_id: string)
    public guild: Guild
    public user: User | null
    public nick: string | null
    public roles: GuildRole[] | undefined
    public joinedAt: Date | null
    public premiumSince: Date | null
    public deaf: boolean | null
    public mute: boolean | null
    public pending: boolean | null

    get bannable(): boolean
    get kickable(): boolean
    get manageable(): boolean
    public ban(days: number): Promise<undefined | string>
    public kick(): Promise<undefined | string>

}

export class GuildRole {

    constructor(client, role_id: string)
    get id(): string | undefined
    get guild(): Guild | undefined
    get name(): string | undefined
    get color(): number | undefined
    get isPinned(): boolean | undefined
    get position(): number | undefined
    get permissions(): Permissions | undefined
    get isManaged(): boolean | undefined
    get isMentionable(): boolean | undefined
    get tags(): {integration_id: null | string, premium_subscriber: boolean, bot_id: null | string}


}

export class GuildBan {

    constructor(client, guild_id: string)
    get user(): User | undefined
    get reason(): string | undefined
    get guild(): Guild | undefined

}

export class GuildChannel {

    constructor(client, channel_id: string)
    public id: string
    public permissionOverwrites: any //TODO: change to Manager
    public messages: CacheManager<Message>
    public commands: CacheManager<Command>
    public type: keyof typeof ChannelTypes | undefined
    public guild: Guild | undefined
    public position: number | undefined
    public name: string | undefined
    public topic: string | undefined
    public nsfw: boolean | undefined
    public lastMessage: Message | undefined
    public bitrate: number | undefined
    public userLimit: number | undefined
    public parent: GuildChannel | undefined
    public lastPin: Date | undefined
    public rtcRegionId: string | undefined
    public videoQualityMode: keyof typeof VideoQualityModes | undefined

    public send(object: IMessage): Promise<Message | string>

}

export class Command {

    public id: string
    public guild: Guild | undefined
    public channel: GuildChannel | undefined
    public author: GuildMember | undefined
    public createdTimestamp: number | undefined
    public editedTimestamp: number | undefined
    public isTts: boolean | undefined
    public mentionedEveryone: boolean | undefined
    public mentionedUsers: User[] | undefined
    public mentionedMembers: GuildMember[] | undefined
    public mentionedChannels: GuildChannel[] | undefined
    public mentionedRoles: GuildRole[] | undefined
    public reactions: MessageReaction[] | undefined
    public webhookId: string | undefined
    public messageReference: MessageReference | undefined
    public commandName: string | undefined
    public commandArgs: string[] | undefined

}

export class Message {

    public id: string
    public deleted: boolean
    public guild: Guild | undefined
    public channel: GuildChannel | undefined
    public author: GuildMember | undefined
    public content: string | undefined
    public createdTimestamp: number | undefined
    public editedTimestamp: number | undefined
    public isTts: boolean | undefined
    public mentionedEveryone: boolean | undefined
    public mentionedUsers: User[] | undefined
    public mentionedMembers: GuildMember[] | undefined
    public mentionedChannels: GuildChannel[] | undefined
    public mentionedRoles: GuildRole[] | undefined
    public embeds: MessageEmbed[] | undefined
    public reactions: MessageReaction[] | undefined
    public pinned: boolean | undefined
    public webhookId: string | undefined
    public type: keyof typeof MessageTypes | undefined
    public messageReference: MessageReference | undefined
    public flags: MessageFlags | undefined
    public referencedMessage: Message | undefined

    get editable(): boolean
    public edit(object: IMessage): Promise<Message | string>
    public delete(): Promise<Message | string>

}

interface IMessage {
    header: IMessageHeader
    description: string
    footer?: IMessageFooter
    image?: IMessageMedia
    video?:  IMessageMedia
    fields?: IMessageField[]
}

interface IMessageHeader {
    title: string
    url?: string
}

interface IMessageFooter {
    text: string
}

interface IMessageMedia {
    url: string
    width?: number
    height?: number
}

interface IMessageField {
    name: string
    value: string
    inline?: boolean
}

export class MessageReaction {

    public users: CacheManager<User>
    public count: number | undefined
    public me: boolean | undefined
    public emoji: any | undefined //TODO: change to Emoji

}

export class MessageReference {

    public guild: Guild | undefined
    public channel: GuildChannel | undefined
    public message: Message | undefined

}

export class MessageEmbed {

    constructor(client: string)
    public title: string | undefined
    public description: string | undefined
    public url: string | undefined
    public timestamp: number | undefined
    public color: number | undefined
    public footer: MessageEmbedFooter | undefined
    public image: MessageEmbedImage | undefined
    public thumbnail: MessageEmbedThumbnail | undefined
    public video: MessageEmbedVideo | undefined
    public provider: MessageEmbedProvider | undefined
    public author: MessageEmbedAuthor | undefined
    public fields: MessageEmbedField[] | undefined


}

interface MessageEmbedFooter {
    text?: string;
    iconURL?: string;
    proxyIconURL?: string;
}

interface MessageEmbedImage {
    url: string;
    proxyURL?: string;
    height?: number;
    width?: number;
}

interface MessageEmbedProvider {
    name: string;
    url: string;
}

interface MessageEmbedThumbnail {
    url: string;
    proxyURL?: string;
    height?: number;
    width?: number;
}
interface MessageEmbedVideo {
    url?: string;
    proxyURL?: string;
    height?: number;
    width?: number;
}

interface MessageEmbedAuthor {
    name?: string;
    url?: string;
    iconURL?: string;
    proxyIconURL?: string;
}
interface MessageEmbedField {
    name: string;
    value: string;
    inline?: boolean;
}
