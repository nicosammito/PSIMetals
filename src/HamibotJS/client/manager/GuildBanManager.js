const CacheManager = require("./CacheManager");
const GuildBan = require("../structures/GuildBan");

class GuildBanManager extends CacheManager{

    _create(client, unique, data) {

        //create GuildBan instance and return
        const guildBan = new GuildBan(client, data.guild_id);
        return data ? guildBan._patch(data) : guildBan;

    }

}

module.exports = GuildBanManager;