class Event {


    /**
     *
     * @param {Object} obj
     * @param {Client} client
     *
     */
    handle = (obj, client) => {
        throw new Error("handle method must be overridden!")
    }

}

module.exports = Event;