'use strict';

const GuildMember = require("../../structures/GuildMember");
/**
 * event (GUILD_MEMBER_REMOVE) function is called
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

    //remove member from GuildMember cache
    const member = guild.members._remove(data['user'].id) ?? new GuildMember(client,data['user'].id)._patch(data);

    /**
     * Emitted whenever a member is removed from a guild
     * @event Events.GUILD_MEMBER_REMOVE
     * @param {GuildMember} guildMember that was removed
     */
    client.emit("guildMemberRemove", member);

}