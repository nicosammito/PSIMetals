'use strict';

module.exports = (client, data) => {

    //get guild or create new guild
    const guild = client.guilds._addOne(data.guild_id, null);

    /**
     * Emitted whenever a guild channel is created
     *
     * @event Events.CHANNEL_CREATE
     * @param {GuildChannel} channel The created GuildChannel instance
     */
    client.emit("channelCreate", guild.channels._addOne(data.id, data));

}