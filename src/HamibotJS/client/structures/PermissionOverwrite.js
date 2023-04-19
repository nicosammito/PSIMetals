const Base = require("./Base");
const Permissions = require('../util/Permissions');
const {OverwriteTypes} = require("../util/Constants");

class PermissionOverwrite extends Base{

    constructor(client, id, channel) {
        super(client);
        this._id = id;
        this._channel = channel;
    }

    _patch(data) {

        this._patched();
        this._type = 'type' in data ? OverwriteTypes[data.type] : this._type;
        this._allow = 'allow' in data ? new Permissions(data.allow).freeze() : this._allow;
        this._deny = 'deny' in data ? new Permissions(data.deny).freeze() : this._deny;

        return this;
    }

    /**
     * role or user id
     *
     * @return {string}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get id() {
        return this._id;
    }

    /**
     * the channel where the overwritten permission is linked to
     *
     * @return {GuildChannel}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get channel() {
        return this._channel;
    }

    /**
     * whether a role or a user permission was overwritten
     *
     * @return {?OverwriteTypes}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get type() {
        return this._type;
    }

    /**
     * The permissions that are allowed for the user or role.
     *
     * @return {?Readonly<BitField>}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get allow() {
        return this._allow;
    }

    /**
     * The permissions that are denied for the user or role.
     *
     * @return {?Readonly<BitField>}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get deny() {
        return this._deny;
    }

}

module.exports = PermissionOverwrite;