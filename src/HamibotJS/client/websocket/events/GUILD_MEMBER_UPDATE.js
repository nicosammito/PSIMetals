'use strict';

/**
 * event (GUILD_MEMBER_UPDATE) function is called
 * to handle the incoming event and emit it to client
 *
 * @param client hamibot client
 * @param data payload received from Hamibot Gateway
 * @author Nico Sammito
 * @since DEV-ALPHA
 */
module.exports = (client, data) => {

    //search for guild or create new guild instance
    const guild = client.guilds._addOne(data.guild_id, null);

    //update member in GuildMembers cache
    const member = guild.members._addOne(data['user'].id, data)

    /**
     * Emitted whenever a member is updated
     * @event Events.GUILD_MEMBER_UPDATE
     * @param {GuildMember} guildMember that was updated
     */
    client.emit("guildMemberUpdate", member);

}