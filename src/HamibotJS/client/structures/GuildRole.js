const Base = require("./Base");
const Permissions = require('../util/Permissions');

class GuildRole extends Base {

    constructor(client, role_id) {
        super(client);
        this._id = role_id;
    }

    _patch(data) {

        this._patched();

        this._guild = 'guild_id' in data ? this.client.guilds._addOne(data['guild_id'], null): this._guild;
        this._name = data['name'] ?? this._name;
        this._color = data['color'] ?? this._color;
        this._pinned = data['hoist'] ?? this._pinned;
        this._position = data['position'] ?? this._position;
        this._permissions = data['permissions'] ? new Permissions(BigInt(data['permissions'])).freeze() : this._permissions;
        this._managed = data['managed'] ?? this._managed;
        this._mentionable = data['mentionable'] ?? this._mentionable;
        this._tags = {bot_id: null, integration_id: null, premium_subscriber: false};
        if ('tags' in data) {
            if ('bot_id' in data.tags) this._tags.botId = data['tags']['bot_id'];
            if ('integration_id' in data.tags) this._tags.integrationId = data['tags']['integration_id'];
            if ('premium_subscriber' in data.tags) this._tags.premiumSubscriberRole = true;
        }

        return this;
    }

    /**
     * role id
     *
     * @return {?string}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get id() {
        return this._id;
    }

    /**
     *
     * @return {?Guild}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get guild() {
        return this._guild;
    }

    /**
     * role name
     *
     * @return {?string}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get name() {
        return this._name;
    }

    /**
     * integer representation of hexadecimal color code
     *
     * @return {?number}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get color() {
        return this._color;
    }

    /**
     * if this role is pinned in the user listing
     *
     * @return {?boolean}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get isPinned() {
        return this._pinned;
    }

    /**
     * position of this role in a guild
     *
     * @return {?number}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get position() {
        return this._position;
    }

    /**
     * permission bit set
     *
     * @return {?Readonly<BitField>}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get permissions() {
        return this._permissions;
    }

    /**
     * whether this role is managed by an integration
     *
     * @return {?boolean}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get isManaged() {
        return this._managed;
    }

    /**
     * whether this role is mentionable
     *
     * @return {?boolean}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get isMentionable() {
        return this._mentionable;
    }

    /**
     * the tags this role has
     *
     * @return {{integration_id: null | string, premium_subscriber: boolean, bot_id: null | string}}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get tags() {
        return this._tags;
    }

}

module.exports = GuildRole;