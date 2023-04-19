const Base = require("./Base");
const User = require("./User");


class GuildBan extends Base {

    constructor(client, guild_id) {
        super(client);
        this._guild = guild_id ? client.guilds._addOne(guild_id, null) : null;
    }

    /**
     * patches the given data and saves it into the class instance
     *
     * @param {*} data
     * @return {GuildBan}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    _patch(data) {

        //insures that no request is made when calling a getter
        this._patched()

        //save data
        this._user = 'user' in data ? new User(this.client, data.user.id)._patch(data.user) : this._user;
        this._reason = data.reason ?? this._reason;

        return this;

    }

    /**
     * the banned / unbanned user
     *
     * @return {?User}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get user() {
        return this._user;
    }

    /**
     * the reason for the ban
     *
     * @return {?string}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get reason() {
        return this._reason;
    }

    /**
     * id of the guild
     *
     * @return {?Guild}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get guild() {
        return this._guild;
    }

}

module.exports = GuildBan;