// @ts-ignore
import EventEmitter = require("node:events");
import {Invite, GuildChannel, GuildBan, GuildMember, GuildRole, Message, Command} from './structure';

export class Client extends EventEmitter {

    private token: string
    private debug: boolean

    constructor(token: string);
    public startWithDebug(): void
    public on<K extends keyof HamibotEvents>(event: K, listener: (...args: HamibotEvents[K]) => void): this;
    public on<S extends string | symbol>(event: Exclude<S, keyof HamibotEvents>, listener: (...args: any[]) => void): this;
    public emit<K extends keyof HamibotEvents>(event: K, ...args: HamibotEvents[K]): boolean;
}


interface HamibotEvents {
    ready: [void],
    inviteCreate: [Invite],
    inviteDelete: [Invite],
    guildMemberAdd: [GuildMember],
    guildMemberRemove: [GuildMember],
    guildMemberUpdate: [GuildMember],
    guildRoleCreate: [GuildRole],
    guildRoleUpdate: [GuildRole],
    guildRoleDelete: [GuildRole],
    guildBanAdd: [GuildBan],
    guildBanRemove: [GuildBan],
    channelUpdate: [GuildChannel],
    channelCreate: [GuildChannel],
    channelDelete: [GuildChannel],
    messageCreate: [Message],
    messageUpdate: [Message],
    messageDelete: [Message],
    commandCreate: [Command],
    commandUpdate: [Command]
}
