'use strict';

module.exports = (client, data) => {

    //get related guild
    const guild = client.guilds._addOne(data.guild_id, null);
    const channel = guild.channels._addOne(data['channel_id'], {guild_id: data.guild_id});
    const message = channel.messages._addOne(data['id'], data);

    //set last message on channel to new received message
    channel.lastMessage = message;

    /**
     * Emitted whenever a user send a message into a channel
     * @event Events.MESSAGE_CREATE
     * @param {Message} message The message that was send
     */
    client.emit("messageCreate", message);

}