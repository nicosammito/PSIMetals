const Event = require('../Event');

class Identify extends Event{


    handle = (obj, client) => {
        client.gateway.sendHandler.HEARTBEAT.handle(null, client);

        //set sessionId for resuming connection
        client.gateway.sessionID = obj.content.sessionId;
        client.emit("ready");
    }

}

module.exports = Identify;