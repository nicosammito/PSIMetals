'use strict';

const Message = require("../../structures/Message");

module.exports = (client, data) => {

    //get related guild and channel
    const guild = client.guilds._addOne(data.guild_id, null);
    const channel = guild.channels._addOne(data['channel_id'], null);
    const message = channel.messages._remove(data['id']) ?? new Message(client, data['id'])._patch(data);
    message.deleted = true;
    /**
     * Emitted whenever a user deletes a message in a channel
     * @event Events.MESSAGE_DELETE
     * @param {Message} message The message that was deleted
     */
    client.emit("messageDelete", message);

}