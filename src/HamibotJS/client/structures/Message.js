const Base = require("./Base");
const User = require("./User");
const MessageEmbed = require("./MessageEmbed");
const MessageReaction = require("./MessageReaction");
const {MessageTypes} = require("../util/Constants");
const MessageReference = require("./MessageReference");
const MessageFlags = require("../util/MessageFlags");
const Joi = require("joi");

class Message extends Base {

    constructor(client, message_id) {
        super(client);
        this.id = message_id;
        this.deleted = false;
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
        this.content = data['content'] ?? this.content;
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
        this.embeds = 'embeds' in data ? data['embeds'].map(embed => new MessageEmbed(this.client)._patch(embed)) : this.embeds;
        this.reactions = 'reactions' in data ? data['reactions'].map(reaction => new MessageReaction(this.client, data['guild_id'])._patch(reaction)) : this.reactions;
        this.pinned = data['pinned'] ?? this.pinned;
        this.webhookId = data['webhook_id'] ?? this.webhookId;
        this.type = 'type' in data ? MessageTypes[data['type']] : this.type;
        this.messageReference = 'message_reference' in data ? new MessageReference(this.client)._patch(data['message_reference']) : this.messageReference;
        this.flags = 'flags' in data ? new MessageFlags(data['flags']).freeze() : this.flags;
        this.referencedMessage = 'referenced_message' in data && data['referenced_message'] != null && this.channel ? this.channel.messages._addOne(data['referenced_message'].id, data['referenced_message']) : this.referencedMessage;

        return this;

    }

    /**
     * When concatenated with a string, this automatically concatenates the message's content instead of the object.
     * @returns {string}
     * @example
     * // Logs: Message: This is a message!
     * console.log(`Message: ${message}`);
     */
    toString() {
        return this.content;
    }

    /**
     * Whether the message is editable by the client user
     * @type {boolean}
     * @readonly
     */
    get editable() {
        return Boolean(this.author['id'] === "792825568126959677" && !this.deleted && this.guild && this.channel);
    }

    /**
     * edit's this message instance by the given new message in the related channel
     * in the related guild
     * @param object {IMessage}
     * @return {Promise<Message | string>}
     */
    async edit(object) {

        //check if guild and channel is set
        if (!this.guild && !this.channel) return Promise.reject("Guild or Channel isn't set")

        //validate object
        const {error} = Joi.object({
            header: Joi.object({
                title: Joi.string().required(),
                url: Joi.string().uri()
            }).required(),
            description: Joi.string().required(),
            footer: Joi.object({
                text: Joi.string().required()
            }).allow(null),
            image: Joi.object({
                url: Joi.string().uri().required(),
                width: Joi.number(),
                height: Joi.number()
            }).allow(null),
            video: Joi.object({
                url: Joi.string().uri().required(),
                width: Joi.number(),
                height: Joi.number()
            }).allow(null),
            fields: Joi.array().has(Joi.object({
                name: Joi.string().required(),
                value: Joi.any().required(),
                inline: Joi.boolean().default(false)
            })).allow(null)
        }).validate(object)
        if (error) return Promise.reject(error.details[0].message.toString());

        //make request and return response
        return new Promise((resolve, reject) => {
            this.client.rest.api.guilds[this.guild.id].channels[this.channel.id].messages[this.id].patch(object)
                .then(response => resolve(this._patch(response)))
                .catch(err => reject(err))
        })
    }

    /**
     * deletes this message instance in the related channel in the related guild
     * @return {Promise<Message | string>}
     */
    async delete() {

        //check if guild and channel is set
        if (!this.guild && !this.channel) return Promise.reject("Guild or Channel isn't set")

        //make request and return response
        return new Promise((resolve, reject) => {
            this.client.rest.api.guilds[this.guild.id].channels[this.channel.id].messages[this.id].delete()
                .then(() => {
                    this.deleted = true;
                    resolve(this)
                }).catch(err => reject(err))
        })
    }

}

module.exports = Message;