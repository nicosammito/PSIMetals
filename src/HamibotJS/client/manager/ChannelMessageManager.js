const CacheManager = require("./CacheManager");
const Message = require("../structures/Message");

class ChannelMessageManager extends CacheManager {

    _create(client, unique, data) {
        return data ? new Message(client, unique)._patch(data): new Message(client, unique);
    }

}

module.exports = ChannelMessageManager;