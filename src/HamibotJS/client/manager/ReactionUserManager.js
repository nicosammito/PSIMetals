const CacheManager = require("./CacheManager");

class ReactionUserManager extends CacheManager {

    constructor(client, guild_id) {
        super(client);
        this._guild = client.guilds._addOne(guild_id, null);
    }

    _create(client, unique, data) {
        return this._guild.members._addOne(unique, data['member'] ?? {guild_id: this._guild.id, user: data});
    }

}

module.exports = ReactionUserManager;