const Base = require("./Base");

class MessageReference extends Base {

    constructor(client) {
        super(client);
    }

    _patch(data) {

        this._patched();

        this.guild = 'guild_id' in data ? this.client.guilds._addOne(data['guild_id'], null) : this.guild;
        this.channel = this.guild && 'channel_id' in data ? this.guild.channels._addOne(data['channel_id'], null) : this.channel;
        this.message = 'message_id' in data && this.channel ? this.channel.messages._addOne(data['message_id'], null) : this.message;

        return this;

    }

}

module.exports = MessageReference;