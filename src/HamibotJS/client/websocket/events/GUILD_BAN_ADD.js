'use strict';
/**
 * event (GUILD_BAN_ADD) function is called
 * to handle the incoming event and emit it to client
 *
 * @param client hamibot's client
 * @param data payload received from Hamibot Gateway
 * @author Nico Sammito
 * @since DEV-ALPHA
 */
module.exports = (client, data) => {

    //get guild from cache and add ban
    const guild = client.guilds._addOne(data.guild_id, null);
    const ban = guild.bans._addOne(data.user.id, data);

    /**
     * Emitted whenever a user is banned from guild
     *
     * @event Events.GUILD_BAN_ADD
     * @param {GuildBan} ban The banned user and related guild
     */
    client.emit("guildBanAdd", ban);

}