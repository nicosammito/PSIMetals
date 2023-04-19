'use strict';

/**
 * event (GUILD_MEMBER_ADD) function is called
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

    //add member to GuildMember cache
    const member = guild.members._addOne(data.user && data.user.id ? data.user.id : null, data)

    /**
     * Emitted whenever a member joined a guild
     * @event Events.GUILD_MEMBER_ADD
     * @param {GuildMember} guildMember that was added
     */
    client.emit("guildMemberAdd", member);

}