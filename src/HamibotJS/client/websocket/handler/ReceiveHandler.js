const HELLO = require('../operation/receive/Hello');
const IDENTIFY = require('../operation/receive/Identify');
const HEARTBEAT = require('../operation/receive/Heartbeat');
const DISPATCH = require('../operation/receive/Dispatch');
const RESUME = require('../operation/receive/Resume');

module.exports = {

    HELLO:  new HELLO(),
    IDENTIFY: new IDENTIFY(),
    HEARTBEAT: new HEARTBEAT(),
    DISPATCH: new DISPATCH(),
    RESUME: new RESUME()
}
