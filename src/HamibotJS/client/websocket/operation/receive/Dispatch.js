const Event = require('../Event');
const Events = require('../../events');

class Dispatch extends Event{

    /**
     *
     * @param {Object} obj
     * @param {Client} client
     *
     */
    handle = (obj, client) => {

        //this is for resuming connection
        client.gateway.eventId = obj.eventId;

        //find event
        const func = Events[obj.eventType];
        if (func) func(client, obj.event);
    }

}

module.exports = Dispatch;