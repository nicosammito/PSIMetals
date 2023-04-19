const Base = require("./Base");
const {ChannelTypes, VideoQualityModes} = require("../util/Constants");
const PermissionOverwritesManager = require("../manager/PermissionOverwritesManager");
const ChannelMessageManager = require("../manager/ChannelMessageManager");
const ChannelCommandManager = require("../manager/ChannelCommandManager");
const Joi = require("joi");

class GuildChannel extends Base {

    constructor(client, channel_id) {
        super(client);
        this.id = channel_id;
        this.permissionOverwrites = new PermissionOverwritesManager(client);
        this.messages = new ChannelMessageManager(client);
        this.commands = new ChannelCommandManager(client);
    }

    _patch(data) {

        this._patched();

        this.type = 'type' in data ? ChannelTypes[data.type] : this.type;
        this.guild = data.guild_id ? this.client.guilds._addOne(data.guild_id, null) : this.guild;
        this.position = data.position ?? this.position;
        'permission_overwrites' in data && data.permission_overwrites.forEach(permissionOverwrite => {
            //set channel and add payload
            permissionOverwrite.channel = this;
            this.permissionOverwrites._addOne(permissionOverwrite.id, permissionOverwrite);
        })
        this.name = data.name ?? this.name;
        this.topic = data["topic"] ?? this.topic;
        this.nsfw = data["nsfw"] ?? this.nsfw;
        this.lastMessage = 'last_message_id' in data ? this.messages._addOne(data['last_message_id'], null) : this.lastMessage;
        this.bitrate = data["bitrate"] ?? this.bitrate;
        this.userLimit = data["user_limit"] ?? this.userLimit;
        this.parent = data['parent_id'] ?? this.parentId; //TODO need to be added / searched over channelManager
        this.lastPin = 'last_pin_timestamp' in data ? new Date(data['last_pin_timestamp']) : this.lastPin;
        this.rtcRegionId = data['rtc_region'] ?? this.rtcRegionId;
        this.videoQualityMode = 'video_quality_mode' in data ? VideoQualityModes[data['video_quality_mode']] : this.videoQualityMode;

        return this;

    }

    /**
     *
     * @param obj
     * @return {Promise<Message | string>}
     */
    async send(obj) {

        //check if guild is set
        if (!this.guild) return Promise.reject("Guild isn't set")

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
        }).validate(obj)
        if (error) return Promise.reject(error.details[0].message.toString());

        //make request and return response
        return new Promise((resolve, reject) => {
            this.client.rest.api.guilds[this.guild.id].channels[this.id].messages.post(obj)
                .then(response => resolve(this.messages._addOne(response['id'], response)))
                .catch(err => reject(err))
        })

    }

}

module.exports = GuildChannel;