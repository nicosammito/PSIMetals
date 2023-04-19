const CacheManager = require("./CacheManager");
const Invite = require("../structures/Invite");
class GuildInviteManager extends CacheManager {

    _create(client, unique, data) {

        //create invite instance
        const invite = new Invite(client, data.code);

        return data ? invite._patch(data) : invite;

    }

}

module.exports = GuildInviteManager;