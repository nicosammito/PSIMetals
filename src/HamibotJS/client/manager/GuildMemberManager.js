const CacheManager = require("./CacheManager");
const GuildMember = require("../structures/GuildMember");

class GuildMemberManager extends CacheManager{

    /**
     *
     * @param client
     * @param {string} unique user_id of GuildMember
     * @param {{guild_id, user: {}, nick, roles, joined_at, premium_since, deaf, mute, pending, permissions}} data
     * @private
     */
    _create(client, unique, data) {

        //create GuildMember and link to guild_id
        const guildMember = new GuildMember(client, unique);

        //patch data
        return data ? guildMember._patch(data) : guildMember;
    }

}

module.exports = GuildMemberManager;