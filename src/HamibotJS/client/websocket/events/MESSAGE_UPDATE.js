'use strict';

module.exports = (client, data) => {

    //get related guild and channel
    const guild = client.guilds._addOne(data.guild_id, null);
    const channel = guild.channels._addOne(data['channel_id'], null);

    /**
     * Emitted whenever a user updates a message in a channel
     * @event Events.MESSAGE_UPDATE
     * @param {Message} message The message that was updated
     */
    client.emit("messageUpdate", channel.messages._addOne(data['id'], data));

}