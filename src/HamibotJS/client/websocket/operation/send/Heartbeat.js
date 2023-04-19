const Event = require('../Event')

class Heartbeat extends Event {

    /**
     *
     * @param {Object} obj
     * @param {Client} client
     */
    handle = (obj, client) => {

        //create heartbeat object
        const heartbeat = {
            "operation": 1
        };

        //send heartbeat every minute
        this._interval = setInterval(() => {
            client.gateway.send(JSON.stringify(heartbeat));

            setTimeout(() => {
                if((new Date().getTime() - client.gateway.heartbeat) > 70*1000){
                    client.gateway.socket.close(4005);
                }
            }, 10*1000)
        }, 60 * 1000);

    }

    stopHeartbeat = () => {

        //stop sending heartbeat
        clearInterval(this._interval);
    }




}

module.exports = Heartbeat;