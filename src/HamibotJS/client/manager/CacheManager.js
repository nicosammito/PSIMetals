'use strict';

class CacheManager {

    /**
     * @constructor of {@link CacheManager} class
     *
     * @param client
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    constructor(client) {

        //save client
        this._client = client;

        /**
         * cache to store event payloads
         *
         * @type {Map<string, Base>}
         */
        this._cache = new Map();
    }

    /**
     * checks if a data linked to the unique id exists.
     * If no data is found he will call {@link CacheManager#_create} to create
     * a new data entry.
     *
     * @param {string} unique
     * @param {object} data
     * @return {Base | ?}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    _addOne(unique, data) {

        const value = this._cache.get(unique);
        if (value) {
            if (data) value._patch(data);
            return value
        }

        //create instance
        const tmp = this._create(this._client, unique, data);

        //save in cache
        this._cache.set(unique, tmp);

        //return
        return this._addOne(unique, null);

    }

    /**
     * removes a specific linked entry and
     * return's it on success
     *
     * @param unique id to identify entry
     * @return {?Base | ?}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    _remove(unique) {

        //get and check if entry exists
        const value = this._cache.get(unique);
        if (!value) return null;

        //delete entry
        this._cache.delete(unique);

        return value;

    }


    /**
     * return's the cache where all linked data is stored.
     * Its is possible that some data is stored without a linked id,
     * so its highly recommended to iterate through this the cache instead of just
     * use the get method.
     *
     * @return {Map<string, Base | ?>}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get cache() {
        return this._cache;
    }

    /**
     * will create a new Base kind instance
     *
     * @param client
     * @param {string} unique
     * @param {Object} data
     * @return {Base | ?}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    _create(client, unique, data) {
        throw Error("UN_IMPLEMENTED")
    }

    /**
     * will perform a fetch request to Hamibot's API
     *
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    fetch() {
        throw Error("UN_IMPLEMENTED")
    }

}

module.exports = CacheManager;