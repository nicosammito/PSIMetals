'use strict';

const GuildChannel = require("../../structures/GuildChannel");
module.exports = (client, data) => {

    //get guild or create new guild
    const guild = client.guilds._addOne(data.guild_id, null);
    let channel = guild.channels._remove(data.id);

    if (!channel) channel = new GuildChannel(client, data.id);


    /**
     * Emitted whenever a guild channel is deleted
     *
     * @event Events.CHANNEL_DELETE
     * @param {GuildChannel} channel The deleted GuildChannel instance
     */
    client.emit("channelDelete", data ? channel._patch(data) : channel);

}