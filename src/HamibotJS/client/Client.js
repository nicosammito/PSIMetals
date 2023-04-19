const EventEmitter = require("events").EventEmitter;
const SocketClient = require('./websocket/SocketClient');
const GuildManager = require("./manager/GuildManager");
const RestManager = require("./rest/RestManager");

class Client extends EventEmitter {

    /**
     *
     * @param {String} token
     * @since DEV-ALPHA
     * @author Nico Sammito
     *
     */
    constructor(token) {

        //super constructor of class EventEmitter
        super();

        this._guilds = new GuildManager(this);

        //set users module token
        this.token = token;

        //set socketClient and start connection to hamibot gateway
        this.gateway = new SocketClient(this);

        //set rest manager
        this.rest = new RestManager(this);

    }

    /**
     * starts the hamibot client in debug mode
     * you will get all incoming and out going events and more useful data to
     * troubleshot your error
     *
     * @since DEV-ALPHA
     * @author Nico Sammito
     */
    startWithDebug() {
        this.debug = true;
    }

    /**
     * stores all guild instances teh bot has access to
     *
     * @return {GuildManager}
     * @author Nico Sammito
     * @since DEV-ALPHA
     */
    get guilds() {
        return this._guilds;
    }
}

//export Client class
module.exports = Client;