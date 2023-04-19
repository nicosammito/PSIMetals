const IDENTIFY = require('../operation/send/Identify');
const HEARTBEAT = require('../operation/send/Heartbeat');
const RESUME = require('../operation/send/Resume');

module.exports = {
    IDENTIFY: new IDENTIFY(),
    HEARTBEAT: new HEARTBEAT(),
    RESUME: new RESUME()
}