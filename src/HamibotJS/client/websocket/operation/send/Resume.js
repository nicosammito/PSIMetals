const Event = require('../Event');

class Resume extends Event{

    /**
     *
     * @param {Object} obj
     * @param {Client} client
     */
    handle = (obj, client) => {
        const resume = {
            "operation": 6,
            "content": {
                "token": client.token,
                "sessionId": client.gateway.sessionID,
                "eventId": client.gateway.eventId
            }
        };

        client.gateway.send(JSON.stringify(resume));
    }

}

module.exports = Resume;