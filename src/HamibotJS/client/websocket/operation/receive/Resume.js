const Event = require('../Event');

class Resume extends Event{

    /**
     *
     * @param {Object} obj
     * @param {Client} client
     *
     */
    handle = (obj, client) => {

        //start sending heartbeats again
        client.gateway.sendHandler.HEARTBEAT.handle(null, client);

        //set sessionId for resuming connection
        client.gateway.sessionID = obj.content.sessionId;
    }

}

module.exports = Resume;