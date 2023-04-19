const CacheManager = require("./CacheManager");
const GuildChannel = require("../structures/GuildChannel");

class GuildChannelManager extends CacheManager {

    _create(client, unique, data) {

        //create GuildChannel and link to channel_id
        const guildMember = new GuildChannel(client, unique);

        //patch data
        return data ? guildMember._patch(data): guildMember;

    }

}

module.exports = GuildChannelManager;