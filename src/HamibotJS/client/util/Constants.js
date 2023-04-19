'use strict';

exports.Events = arrayToObject([
    'INVITE_CREATE',
    'INVITE_DELETE',
    'GUILD_MEMBER_ADD',
    'GUILD_MEMBER_REMOVE',
    'GUILD_MEMBER_UPDATE',
    'GUILD_ROLE_CREATE',
    'GUILD_ROLE_UPDATE',
    'GUILD_ROLE_DELETE',
    'GUILD_BAN_ADD',
    'GUILD_BAN_REMOVE',
    'CHANNEL_UPDATE',
    'CHANNEL_DELETE',
    'CHANNEL_CREATE',
    'MESSAGE_CREATE',
    'MESSAGE_UPDATE',
    'MESSAGE_DELETE',
    'COMMAND_CREATE',
    'COMMAND_UPDATE'
]);

exports.VerificationLevel = createEnum(['NONE', 'LOW', 'MEDIUM', 'HIGH', 'VERY_HIGH']);
exports.DefaultMessageNotificationLevels = createEnum(['ALL_MESSAGES', 'ONLY_MENTIONS']);
exports.ExplicitContentFilterLevels = createEnum(['DISABLED', 'MEMBERS_WITHOUT_ROLES', 'ALL_MEMBERS']);
exports.MFALevels = createEnum(['NONE', 'ELEVATED']);
exports.PremiumTiers = createEnum(['NONE', 'TIER_1', 'TIER_2', 'TIER_3']);
exports.GuildNSFWLevels = createEnum(['DEFAULT', 'EXPLICIT', 'SAFE', 'AGE_RESTRICTED']);
exports.ChannelTypes = createEnum(['GUILD_TEXT', undefined, 'GUILD_VOICE', undefined, 'GUILD_CATEGORY', 'GUILD_NEWS', 'GUILD_STORE', undefined, undefined, undefined, 'GUILD_NEWS_THREAD', 'GUILD_PUBLIC_THREAD', 'GUILD_PRIVATE_THREAD', 'GUILD_STAGE_VOICE']);
exports.OverwriteTypes = createEnum(['ROLE', 'MEMBER']);
exports.VideoQualityModes = createEnum([undefined, 'AUTO', 'FULL'])
exports.MessageTypes = createEnum(['DEFAULT', 'RECIPIENT_ADD', 'RECIPIENT_REMOVE', 'CALL', 'CHANNEL_NAME_CHANGE', 'CHANNEL_ICON_CHANGE', 'CHANNEL_PINNED_MESSAGE', 'GUILD_MEMBER_JOIN', 'USER_PREMIUM_GUILD_SUBSCRIPTION', 'USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1', 'USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2', 'USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3', 'CHANNEL_FOLLOW_ADD', undefined, 'GUILD_DISCOVERY_DISQUALIFIED', 'GUILD_DISCOVERY_REQUALIFIED', 'GUILD_DISCOVERY_GRACE_PERIOD_INITIAL_WARNING', 'GUILD_DISCOVERY_GRACE_PERIOD_FINAL_WARNING', undefined, 'REPLY', undefined, undefined, 'GUILD_INVITE_REMINDER', undefined])


function createEnum(keys) {
    const obj = {};
    keys.forEach((element, index) => {
        obj[element] = index;
        obj[index] = element;
    })
    return obj;
}


function arrayToObject(array) {
    let object = Object.create(null);
    for (const value of array) object[value] = value;
    return object;
}