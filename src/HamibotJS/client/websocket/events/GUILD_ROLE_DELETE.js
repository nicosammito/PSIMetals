'use strict';
const GuildRole = require("../../structures/GuildRole");

module.exports = (client, data) => {

    //get guild or create new guild
    const guild = client.guilds._addOne(data.guild_id, null);

    //delete role in guild
    const role = guild.roles._remove(data['role_id']) ?? new GuildRole(client, data['role_id'])._patch(data);

    /**
     * Emitted whenever a guild role is deleted
     *
     * @event Events.GuildRoleDelete
     * @param {GuildRole} role The deleted GuildRole instance
     */
    client.emit("guildRoleDelete", role);

}