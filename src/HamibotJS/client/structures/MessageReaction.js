const Base = require("./Base");
const ReactionUserManager = require("../manager/ReactionUserManager");
const Emoji = require("./Emoji");

class MessageReaction extends Base {

    constructor(client, guild_id) {
        super(client);
        this.users = new ReactionUserManager(client, guild_id);
    }

    _patch(data) {

        this._patched();

        this.count = data['count'] ?? this.count;
        this.me = data['me'] ?? this.me;
        this.emoji = 'emoji' in data ? new Emoji(this.client, data['emoji'].id)._patch(data['emoji']) : this.emoji;

        return this;

    }

}

module.exports = MessageReaction;