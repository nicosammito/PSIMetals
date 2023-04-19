const WebSocket = require('ws');
const ConsoleSender = require('../../utils/ConsoleSender');
const ReceiveHandler = require('./handler/ReceiveHandler');
const SendHandler = require('./handler/SendHandler');

class SocketClient {

    /**
     *
     * @param {Client} client
     */
    constructor(client) {

        //initializes eventId variable
        this.eventId = null

        //initializes sessionId variable
        this.sessionID = null

        //initializes heartbeat variable
        this.heartbeat = null;

        //set sendHandler
        this.sendHandler = SendHandler;

        this._client = client;

        this._connect(client);
    }


    /**
     *
     * @param {String} message
     */
    send = (message) => {
        if (this.socket.readyState === 1) {
            this.socket.send(message);
            if(this._client.debug) ConsoleSender.sendToConsoleAsClient(message);
        }else if(this.socket.readyState === 0){
            const interval = setInterval(() => {
                if (this.socket.readyState === 1) {
                    this.socket.send(message);
                    if(this._client.debug) ConsoleSender.sendToConsoleAsClient(message);
                    clearInterval(interval);
                }
            }, 1000)
        }
    }


    _connect = (client) => {

        this.eventId = null;

        //set WebSocket
        this.socket = new WebSocket(`ws://185.216.177.253:8025/server/module?token=${client.token}`);

        this.socket.on("open", () => {

            //set listeners for WebSocket
            this.socket.on('error', (error) => this._onError(error, client));
            this.socket.on('message', (data) => this._onMessage(data, client));
            this.socket.on('close', (closeReasonCode) => this._onClose(closeReasonCode, client));

        })

    }

    /**
     * method is called when message was sent from Hamibot Gateway
     *
     * @param message
     * @param {Client} client
     *
     * @version Alpha
     * @author Nico Sammito
     *
     * @internal
     * @private
     */
    _onMessage = (message, client) => {

        //check if debug mode is enabled
        if (client.debug) ConsoleSender.sendToConsoleAsGateway(message);

        //create JSONObject from input
        let jsonObject = null;

        try {
            jsonObject = JSON.parse(message);
        } catch (e) {return;}

        if (jsonObject.operation == null) {
            return;
        }

        //handle operation
        switch (jsonObject.operation) {
            case 10:
                ReceiveHandler.HELLO.handle(jsonObject, client);
                break;
            case 2:
                ReceiveHandler.IDENTIFY.handle(jsonObject, client);
                break;
            case 11:
                ReceiveHandler.HEARTBEAT.handle(jsonObject, client);
                break;
            case 6:
                ReceiveHandler.RESUME.handle(jsonObject, client);
                break;
            case 0:
                ReceiveHandler.DISPATCH.handle(jsonObject, client);
                break;
        }
    }
    /**
     * method is called when connection to gateway is lost
     *
     * @param {int} closeReasonCode
     * @param {Client} client
     *
     * @version Alpha
     * @author Nico Sammito
     *
     * @internal
     * @private
     */
    _onClose = (closeReasonCode, client) => {

        //check if debug mode is enabled
        if (client.debug) {
            //send close reason
            ConsoleSender.sendToConsoleAsGateway(`Session was closed because of ${closeReasonCode}`);
        }

        //stop sending heartbeats
        SendHandler.HEARTBEAT.stopHeartbeat();

        setTimeout(() => {
            switch (closeReasonCode) {
                case 4000:
                    this._connect(client);
                    break;
                case 4001:
                    this._connect(client);
                    break;
                case 4002:
                    this._connect(client);
                    break;
                case 4003:
                    this._connect(client);
                    break;
                case 4004:
                    throw new Error("Token is not valid!");
                case 4005:
                    this._connect(client);
                    break;
                case 4006:
                    this.sessionID = null;
                    this._connect(client);
                    break;
                case 4007:
                    throw new Error("Connection is not secure!")
                case 4008:
                    this.sessionID = null;
                    this._connect(client);
                    break;
                default:
                    this._connect(client);
                    break;
            }
        }, 2000)

    }


    /**
     *
     * @param {Error} error
     * @param {Client} client
     * @private
     */
    _onError = (error, client) => {
        if (client.debug) {
            ConsoleSender.sendToConsoleAsGateway(error.toString());
        }
    }
}

module.exports = SocketClient;