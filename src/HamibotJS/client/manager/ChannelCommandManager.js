const CacheManager = require("./CacheManager");
const Command = require("../structures/Command");

class ChannelCommandManager extends CacheManager {

    _create(client, unique, data) {
        return data ? new Command(client, unique)._patch(data) : new Command(client, unique);
    }

}

module.exports = ChannelCommandManager;