const CacheManager = require("./CacheManager");
const GuildRole = require("../structures/GuildRole");

class GuildRoleManager extends CacheManager {

    _create(client, unique, data) {

        const role = new GuildRole(client, unique);

        return data ? role._patch(data) : role;

    }

}

module.exports = GuildRoleManager;