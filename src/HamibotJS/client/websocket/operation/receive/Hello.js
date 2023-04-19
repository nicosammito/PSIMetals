const Event = require('../Event');

class Hello extends Event {


    /**
     *
     * @param {Object} obj
     * @param {Client} client
     *
     */
    handle = (obj, client) => {
        //console.log("Connected to Hamibot Gateway!");
        client.gateway.heartbeat = new Date().getTime();

        if(client.gateway.sessionID != null){
            client.gateway.sendHandler.RESUME.handle(null, client);
        }else {
            client.gateway.sendHandler.IDENTIFY.handle(null, client);
        }
    }
}

module.exports = Hello;