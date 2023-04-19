const { Events } = require("../../util/Constants");

const events = {};

for (const name of Object.keys(Events)) {
    events[name] = require(`./${name}.js`);
}

module.exports = events;