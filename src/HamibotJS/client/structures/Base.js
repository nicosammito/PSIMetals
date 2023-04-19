class Base {

    constructor(client) {
        this.client = client;
        this._isPatched = false;
    }

    _patch() {
        this._isPatched = true;

        //do things with data

        return this;
    }

    _patched() {
        this._isPatched = true;
    }

}

module.exports = Base;