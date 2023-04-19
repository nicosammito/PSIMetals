'use strict';

module.exports = (client, data) => {

    //get related guild
    const guild = client.guilds._addOne(data.guild_id, null);
    const channel = guild.channels._addOne(data['channel_id'], null);
    const command = channel.commands._addOne(data['id'], data);

    /**
     * Emitted whenever a user updates a command in a channel
     * @event Events.COMMAND_UPDATE
     * @param {Command} command The command that was updated
     */
    client.emit("commandUpdate", command);

}