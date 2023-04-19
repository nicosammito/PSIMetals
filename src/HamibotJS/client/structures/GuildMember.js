const Base = require("./Base");
const User = require("./User");

class GuildMember extends Base{

    constructor(client, user_id) {
        super(client);
        this.user = user_id ? new User(this.client, user_id) : this.user;
    }

    _patch(data) {

        //is patched
        this._patched();

        //save data
        this.guild = 'guild_id' in data ? this.client.guilds._addOne(data['guild_id'], null) : this.guild;
        this.user = this.user ? this.user._patch(data['user']) : this.user;
        this.nick = data.nick ?? this.nick;
        this.roles = 'roles' in data ? data['roles'].map(role => this.guild ? this.guild.roles._addOne(role, null) : null).filter(role => role != null) : this.roles;
        this.joinedAt = 'joined_at' in data ? new Date(data.joined_at) : this.joinedAt;
        this.premiumSince = 'premium_since' in data ? new Date(data.premium_since) : this.premiumSince;
        this.deaf = data.deaf ?? this.deaf;
        this.mute = data.mute ?? this.mute;
        this.pending = data.pending ?? this.pending;

        return this;

    }

    /**
     * check weather the client user can ban the current member
     * @return {boolean}
     */
    get bannable() {
        return this.manageable && this.guild && this.guild.members.cache.has(this.user.id)
    }

    /**
     * check weather the client user can kick the current member
     * @return {boolean}
     */
    get kickable() {
        return this.manageable && this.guild && this.guild.members.cache.has(this.user.id)
    }

    /**
     * check weather the client user can manage this member
     * @return {boolean}
     */
    get manageable() {
        if (this.user.id === this.guild.ownerId) return false;
        if (this.user.id === "792825568126959677") return false;
        return true;
        /*if (!this.guild.me) return false;
        return this.guild.me.roles.highest.comparePositionTo(this.roles.highest) > 0;*/
    }

    /**
     * banns this member in the connected guild
     * @param {number} days The number of days to delete messages for
     * @return {Promise<undefined | string>}
     */
    async ban(days) {

        //check weather the current user is manageable by the client user
        if (!this.manageable) return Promise.reject("current user can't be managed")

        //check weather guild is set
        if (!this.guild) return Promise.reject("guild is undefined")

        //check weather member is on guild
        if (!this.guild.members.cache.get(this.user.id)) return Promise.reject("member isn't on the guild");

        //check weather days is in range
        if (!days && (days > 7 || days < 0)) return Promise.reject("days must be between 0 and 7");

        //perform ban request
        return new Promise((resolve, reject) => {
            this.client.rest.api.guilds[this.guild.id].bans[this.user.id].put({delete_message_days: days ?? 0}).then(() => resolve()).catch(err => reject(err))

        })
    }

    /**
     * kicks this member instance from the related guild
     * @return {Promise<undefined | string>}
     */
    async kick() {

        //check weather the current user is manageable by the client user
        if (!this.manageable) return Promise.reject("current user can't be managed")

        //check weather guild is set
        if (!this.guild) return Promise.reject("guild is undefined")

        //check weather member is on guild
        if (!this.guild.members.cache.get(this.user.id)) return Promise.reject("member isn't on the guild");

        //perform kick request
        return new Promise((resolve, reject) => {
            this.client.rest.api.guilds[this.guild.id].members[this.user.id].delete().then(() => resolve()).catch(err => reject(err))
        })
    }

}

module.exports = GuildMember;