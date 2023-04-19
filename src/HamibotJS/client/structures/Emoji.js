const Base = require("./Base");
const User = require("./User");

class Emoji extends Base{

    /**
     *
     * @param client
     * @param {string} emoji_id
     */
    constructor(client, emoji_id) {
        super(client);
        this._id = emoji_id;
    }

    /**
     *
     * @param {{name, roles, user: {}, require_colons, managed, animated, available}} data
     * @private
     */
    _patch(data) {

        //save that data was patched
        this._patched()

        //set data
        this._name = data.name ?? this._name;
        this._roles = data.roles ?? this._roles /*todo*/;
        this._creater = 'user' in data ? new User(this.client, data.user.id)._patch(data.user) : this._creater;
        this._requireColons = data.require_colons ?? this._requireColons;
        this._managed = data.managed ?? this._managed;
        this._animated = data.animated ?? this._animated;

        return this;

    }

    /**
     * id of emoji
     *
     * @returns {?string}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get id() {
        return this._id;
    }

    /**
     * name of emoji
     *
     * @returns {?string}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get name() {
       return this._name;
    }

    /**
     * roles allowed to use this emoji
     *
     * @returns {?Role[]}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get roles() {
        return this._roles;
    }

    /**
     * user that created this emoji
     *
     * @returns {?User}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get creator() {
        return this._creater;
    }

    /**
     * whether this emoji must be wrapped in colons
     *
     * @returns {?boolean}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get requireColons() {
        return this._requireColons;
    }

    /**
     * whether this emoji is managed
     *
     * @returns {?boolean}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get managed() {
        return this._managed;
    }

    /**
     * whether this emoji is animated
     *
     * @returns {?boolean}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get animated() {
        return this._animated;
    }


}

module.exports = Emoji;