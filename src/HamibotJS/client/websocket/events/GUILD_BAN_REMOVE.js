'use strict';
const GuildBan = require("../../structures/GuildBan");

/**
 * event (GUILD_BAN_REMOVE) function is called
 * to handle the incoming event and emit it to client
 *
 * @param client hamibot's client
 * @param data payload received from Hamibot Gateway
 * @author Nico Sammito
 * @since DEV-ALPHA
 */
module.exports = (client, data) => {

    //get guild from cache and remove ban
    const guild = client.guilds._addOne(data.guild_id, null);
    const ban = guild.bans._remove(data.user.id) ?? new GuildBan(client, data.guild_id);

    /**
     * Emitted whenever a user is unbanned from guild
     *
     * @event Events.GUILD_BAN_REMOVE
     * @param {GuildBan} ban The unbanned user and related guild
     */
    client.emit("guildBanRemove", data ? ban._patch(data) : ban);

}