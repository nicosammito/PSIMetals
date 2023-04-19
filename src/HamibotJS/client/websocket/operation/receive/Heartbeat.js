const Event = require('../Event');

class Heartbeat extends Event{

    /**
     *
     * @param {Object} obj
     * @param {Client} client
     *
     */
    handle = (obj, client) => {
        client.gateway.heartbeat = new Date().getTime();
    }

}

module.exports = Heartbeat;