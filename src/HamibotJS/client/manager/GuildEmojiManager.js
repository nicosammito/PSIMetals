const Emoji = require("../structures/Emoji");
const CacheManager = require("./CacheManager");

class GuildEmojiManager extends CacheManager{

    _create(client, unique, data) {

        const emoji = new Emoji(client, unique);

        return data ? emoji._patch(data): emoji;

    }

}

module.exports = GuildEmojiManager;