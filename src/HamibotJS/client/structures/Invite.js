const Base = require("./Base");
const User = require("./User");

class Invite extends Base {

    constructor(client, invite_id) {
        super(client);
        this._code = invite_id;

    }

    /**
     *
     * @param {*} data
     * @private
     */
    _patch(data) {

        this._patched();

        /**
         * unix timestamp when the invite was created
         * @type {?number}
         */
        this.createdTimestamp = 'created_at' in data ? new Date(data.created_at).getTime() : this.createdTimestamp;

        /**
         * unix timestamp when the invite will expire
         * @type {?number}
         */
        this.expiresTimestamp = 'expires_at' in data ? new Date(data.expires_at).getTime() : this.expiresTimestamp;

        /**
         * The guild id where the invite is linked to
         * @type {?String | ?Guild}
         */
        this.guild = 'guild_id' in data ? this.client.guilds._addOne(data.guild_id, null) : this.guild;

        /**
         * channel id where the invite is linked to
         * @type {?String | ?GuildChannel}
         */
        this.channel = 'channel' in data
            ? (this.guild ? this.guild.channels._addOne(data['channel'].id, data.channel) : this.channel)
            : ('channel_id' in data ? (this.guild ? this.guild.channels._addOne(data['channel_id'], null) : this.channel) : this.channel);

        this.inviter = 'inviter' in data ? new User(this.client, data['inviter'].id)._patch(data.inviter) : this.inviter;

        /**
         * The maximum age of the invite, in seconds, 0 if never expires
         * @type {?number}
         */
        this.maxAge = data.max_age ?? this.maxAge;

        /**
         * the maximum number of times the invite can be used.
         * When 0 there is no uses limitation.
         * @type {?number}
         */
        this.maxUses = data.max_uses ?? this.maxUses;

        /**
         * Whether or not this invite is temporary
         * @type {?boolean}
         */
        this.temporary = data.temporary ?? this.temporary;

        /**
         * How many times this invite has been used
         * @type {?number}
         */
        this.uses = data.uses ?? this.uses;

        /**
         * The approximate number of online members of the guild this invite is for
         * @type {?number}
         */
        this.onlineMemberCount = data.approximate_presence_count ?? this.onlineMemberCount;

        /**
         * The approximate total number of members of the guild this invite is for
         * @type {?number}
         */
        this.allMemberCount = data.approximate_member_count ?? this.allMemberCount;


        return this;

    }
}

module.exports = Invite;