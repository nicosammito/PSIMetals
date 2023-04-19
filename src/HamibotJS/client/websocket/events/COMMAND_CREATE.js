'use strict';

module.exports = (client, data) => {

    //get related guild
    const guild = client.guilds._addOne(data.guild_id, null);
    const channel = guild.channels._addOne(data['channel_id'], {guild_id: data.guild_id});
    const command = channel.commands._addOne(data['id'], data);

    /**
     * Emitted whenever a user sends a command into a channel
     * @event Events.COMMAND_CREATE
     * @param {Command} command The command that was send
     */
    client.emit("commandCreate", command);

}