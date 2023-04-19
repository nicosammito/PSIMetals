'use strict';

module.exports = (client, data) => {

    //get guild or create new guild
    const guild = client.guilds._addOne(data.guild_id, null);

    /**
     * Emitted whenever a guild channel is updated
     *
     * @event Events.CHANNEL_UPDATE
     * @param {GuildChannel} channel The updated GuildChannel instance
     */
    client.emit("channelUpdate", guild.channels._addOne(data.id, data));

}