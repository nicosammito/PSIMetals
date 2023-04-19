'use strict';

module.exports = (client, data) => {

    //get guild or create new guild
    const guild = client.guilds._addOne(data.guild_id, null);

    //update role in guild
    const role = guild.roles._addOne(data['role'].id, data);
    role._patch(data['role']);

    /**
     * Emitted whenever a guild role is updated
     *
     * @event Events.GuildRoleUpdate
     * @param {GuildRole} role The updated GuildRole instance
     */
    client.emit("guildRoleUpdate", role);

}