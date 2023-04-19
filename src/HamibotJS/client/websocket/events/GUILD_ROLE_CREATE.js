'use strict';

module.exports = (client, data) => {

    //get guild or create new guild
    const guild = client.guilds._addOne(data.guild_id, null);

    //add role to guild
    const role = guild.roles._addOne(data['role'].id, data);
    role._patch(data['role']);

    /**
     * Emitted whenever a guild role is created
     *
     * @event Events.GuildRoleCreate
     * @param {GuildRole} role The created GuildRole instance
     */
    client.emit("guildRoleCreate", role);

}