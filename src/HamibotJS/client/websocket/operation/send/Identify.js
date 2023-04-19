const Event = require('../Event');

class Identify extends Event {


    /**
     *
     * @param {Object} obj
     * @param {Client} client
     */
    handle = (obj, client) => {
        const identify = {
            "operation": 2,
            "content": {
                "token": client.token
            }
        };
        client.gateway.send(JSON.stringify(identify));
    }
}

module.exports = Identify;