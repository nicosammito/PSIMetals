const Base = require("./Base");
const {
    VerificationLevels, DefaultMessageNotificationLevels, ExplicitContentFilterLevels, MFALevels, PremiumTiers,
    GuildNSFWLevels
} = require("../util/Constants");
const SystemChannelFlags = require("../util/SystemChannelFlags");
const GuildEmojiManager = require("../manager/GuildEmojiManager");
const GuildMemberManager = require("../manager/GuildMemberManager");
const GuildBanManager = require("../manager/GuildBanManager");
const GuildChannelManager = require("../manager/GuildChannelManager");
const GuildInviteManager = require("../manager/GuildInviteManager");
const GuildRoleManager = require("../manager/GuildRoleManager");

class Guild extends Base {

    /**
     *
     * @param client
     * @param {string} guild_id
     */
    constructor(client, guild_id) {
        super(client);

        this.id = guild_id;
        this.bans = new GuildBanManager(this.client);
        this.emojis = new GuildEmojiManager(this.client);
        this.members = new GuildMemberManager(this.client);
        this.channels = new GuildChannelManager(this.client);
        this.invites = new GuildInviteManager(this.client);
        this.roles = new GuildRoleManager(this.client);

    }


    /**
     *
     * @param {{name, icon, splash, discovery_splash, owner_id, afk_channel_id, afk_timeout, widget_enabled, widget_channel_id,verification_level, default_message_notifications,
     * explicit_content_filter, mfa_level, application_id, system_channel_id, system_channel_flags, rules_channel_id, max_presences, max_members, vanity_url_code, description,
     * banner, premium_tier, premium_subscription_count, preferred_locale, public_updates_channel_id, max_video_channel_users, approximate_member_count, approximate_presence_count,
     * welcome_screen, nsfw_level, stickers, emojis: [{}], members: [{}]}} data
     */
    _patch(data) {
        this.name = data.name ?? this.name;
        this.icon = data.icon ?? this.icon;
        this.splash = data.splash ?? this.splash;
        this.discoverySplash = data.discovery_splash ?? this.discoverySplash;
        this.ownerId = data.owner_id ?? this.ownerId;
        this.afkChannelId = data.afk_channel_id ?? this.afkChannelId;
        this.afkTimeout = data.afk_timeout ?? this.afkTimeout;
        this.widgetEnabled = data.widget_enabled ?? this.widgetEnabled;
        this.widgetChannelId = data.widget_channel_id ?? this.widgetChannelId;
        this.verificationLevel = 'verification_level' in data ? VerificationLevels[data.verification_level] : this.verificationLevel;
        this.defaultMessageNotifications = 'default_message_notifications' in data ? DefaultMessageNotificationLevels[data.default_message_notifications] : this.defaultMessageNotifications;
        this.explicitContentFilter = 'explicit_content_filter' in data ? ExplicitContentFilterLevels[data.explicit_content_filter] : this.explicitContentFilter;
        this.mfaLevel = 'mfa_level' in data ? MFALevels[data.mfa_level] : this.mfaLevel;
        this.applicationId = data.application_id ?? this.applicationId;
        this.systemChannelId = data.system_channel_id ?? this.systemChannelId;
        this.systemChannelFlags = 'system_channel_flags' in data ? new SystemChannelFlags(data.system_channel_flags).freeze() : this.systemChannelFlags;
        this.rulesChannelId = data.rules_channel_id ?? this.rulesChannelId;
        this.maximumPresences = data.max_presences ?? this.maximumPresences;
        this.maximumMembers = data.max_members ?? this.maximumMembers;
        this.vanityUrlCode = data.vanity_url_code ?? this.vanityUrlCode;
        this.description = data.description ?? this.description;
        this.banner = data.banner ?? this.banner;
        this.premiumTier = 'premium_tier' in data ? PremiumTiers[data.premium_tier] : this.premiumTier;
        this.premiumSubscriptionCount = data.premium_subscription_count ?? this.premiumSubscriptionCount;
        this.preferredLocale = data.preferred_locale ?? this.preferredLocale;
        this.publicUpdatesChannelId = data.public_updates_channel_id ?? this.publicUpdatesChannelId;
        this.maxVideoChannelUsers = data.max_video_channel_users ?? this.maxVideoChannelUsers;
        this.approximateMemberCount = data.approximate_member_count ?? this.approximateMemberCount;
        this.approximatePresenceCount = data.approximate_presence_count ?? this.approximatePresenceCount;
        this.nsfwLevel = 'nsfw_level' in data ? GuildNSFWLevels[data.nsfw_level] : this.nsfwLevel;

        if ('emojis' in data) data.emojis.forEach((value) => !value.id ?? this.emojis._addOne(value.id, value));

        if ('members' in data) data.members.forEach(member => {

            //save guild_id and add member to GuildMember cache
            member.guild_id = this._id;
            this.members._addOne((member.user && member.user.id) ? member.user.id : null, member)

        })

        'channels' in data && data.channels.forEach(channel => this.channels._addOne(channel.id ? channel.id : null, channel))

        return this;
    }
}

module.exports = Guild;