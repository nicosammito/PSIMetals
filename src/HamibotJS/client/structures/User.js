const Base = require("./Base");
const UserFlags = require("../util/UserFlags");

class User extends Base {


    /**
     *
     * @param client
     * @param {string} user_id
     */
    constructor(client, user_id) {
        super(client);
        this._id = user_id;
    }

    /**
     *
     * @param {{username, discriminator, avatar, bot, system, mfa_enabled, locale, verified, email, flags, premium_type, public_flags, banner, accent_color}} data
     */
    _patch(data) {

        //is patched
        this._patched();

        //save payload
        this._username = data.username ?? this._username;
        this._discriminator = data.discriminator ?? this._discriminator;
        this._avatar = data.avatar ?? this._avatar;
        this._banner = data.banner ?? this._banner;
        this._accent_color = data.accent_color ?? this._accent_color;
        this._bot = data.bot ?? this._bot;
        this._system = data.system ?? this._system;
        this._mfa_enabled = data.mfa_enabled ?? this._mfa_enabled;
        this._locale = data.locale ?? this._locale;
        this._public_flags = 'public_flags' in data ? new UserFlags(data.public_flags).freeze() : this._public_flags;

        return this;

    }

    /**
     * id of discord user
     *
     * @returns {string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get id() {
        return this._id;
    }

    /**
     * username of discord user
     *
     * @returns {string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get username() {
        return this._username;
    }

    /**
     * the username in combination with discriminator
     * @example Nico Sammito#0308
     *
     * @returns {string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get tag() {
        return `${this.username}#${this.discriminator}`
    }

    /**
     * user's discriminator
     * @example 0308
     *
     * @returns {string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get discriminator() {
        return this._discriminator;
    }

    /**
     * avatar hash that is needed to get the avatar over discords CDN
     * @example eb25997a7b547860ee9feec02570a965
     *
     * @returns {string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get avatar() {
        return this._avatar;
    }

    /**
     * return's the complete URL to get user's avatar
     *
     * @param {ImageFormat} format
     * @returns {?string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    avatarUrl(format) {
        return this.avatar ? `https://cdn.discordapp.com/avatars/${this.id}/${this.avatar}.${format}` : null;
    }

    /**
     * banner in hash format to request it over discords CDN
     * @example {@link User#avatar} this also return's a hash
     *
     * @returns {?string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get banner() {
        return this._banner;
    }

    /**
     * the user's banner color encoded as an integer representation of hexadecimal color code
     *
     * @returns {?number}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get bannerColor() {
        return this._accent_color;
    }

    /**
     * return's the complete URL to get user's banner
     *
     * @param {ImageFormat} format
     * @returns {?string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    bannerUrl(format) {
        return this.banner ? `https://cdn.discordapp.com/banners/${this.id}/${this.banner}.${format}` : null;
    }

    /**
     * whether the user belongs to an OAuth2 application
     *
     * @returns {?boolean}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get isBot() {
        return this._bot;
    }

    /**
     * whether the user is an Official Discord
     * System user (part of the urgent message system)
     *
     * @returns {?boolean}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get isSystem() {
        return this._system;
    }

    /**
     * whether the user has two factor enabled on their account
     *
     * @returns {?boolean}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get activated2FA() {
        return this._mfa_enabled;
    }

    /**
     * the user's chosen language option
     *
     * @returns {?string}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get locale() {
        return this._locale;
    }

    /**
     * the public flags on a user's account
     *
     * @returns {?UserFlags}
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    get userFlags() {
        return this._public_flags;
    }


    /**
     * supported image formats for discords cdn
     * @typedef {"png" | "jpg" | "jpeg" | "gif"} ImageFormat
     */

}

module.exports = User;