const CacheManager = require("./CacheManager");
const Guild = require("../structures/Guild");

class GuildManager extends CacheManager{

    _create(client, unique, data) {

        //create guild instance and patch data
        const guild = new Guild(client, unique);
        return data ? guild._patch(data) : guild;
    }


}

module.exports = GuildManager;