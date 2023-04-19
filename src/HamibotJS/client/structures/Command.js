const Base = require("./Base");
const User = require("./User");
const MessageReaction = require("./MessageReaction");
const MessageReference = require("./MessageReference");

class Command extends Base {

    constructor(client, command_id) {
        super(client);
        this.id = command_id;
    }

    _patch(data) {

        this._patched();

        this.guild = 'guild_id' in data ? this.client.guilds._addOne(data['guild_id'], null) : this.guild;
        this.channel = this.guild
            ? 'channel_id' in data ? this.guild.channels._addOne(data['channel_id'], null) : this.channel
            : this.channel;

        //set author
        if ('member' in data && 'author' in data) data['member'].user = data['author'];
        else if ('author' in data) data['member'] = {user: data['author']};
        this.author = this.guild ? this.guild.members._addOne(data['author'].id, data['member']) : this.author;
        this.createdTimestamp = 'timestamp' in data ? new Date(data['timestamp']).getTime() : this.createdTimestamp;
        this.editedTimestamp = 'edited_timestamp' in data ? new Date(data['edited_timestamp']).getTime() : this.editedTimestamp;
        this.isTts = data['tts'] ?? this.isTts;
        this.mentionedEveryone = data['mention_everyone'] ?? this.mentionedEveryone;
        this.mentionedUsers = data['mentions'] ? data['mentions'].map(user => new User(this.client, user.id)._patch(user)) : this.mentionedUsers;
        this.mentionedMembers = data['mentions'] && this.guild ? data['mentions'].map(user => this.guild.members._addOne(user['id'], {guild_id: this.guild.id, user: user})) : this.mentionedMembers;
        this.mentionedChannels = data['mention_channels'] ? data['mention_channels'].map(channel => {

            this.guild = this.client.guilds._addOne(channel['guild_id'], null);
            return this.guild.channels._addOne(channel['id'], channel);

        }) : this.mentionedChannels;
        this.mentionedRoles = 'mention_roles' in data ? data['mention_roles'].map(role => this.guild ? this.guild.roles._addOne(role, null) : null).filter(role => role != null) : this.mentionedRoles;
        this.reactions = 'reactions' in data ? data['reactions'].map(reaction => new MessageReaction(this.client, data['guild_id'])._patch(reaction)) : this.reactions;
        this.webhookId = data['webhook_id'] ?? this.webhookId;
        this.messageReference = 'message_reference' in data ? new MessageReference(this.client)._patch(data['message_reference']) : this.messageReference;
        this.commandName = data['command_name'] ?? this.commandName;
        this.commandArgs = 'command_args' in data ? data['command_args'].map(arg => arg) : this.commandArgs;

        return this;

    }

}

module.exports = Command;