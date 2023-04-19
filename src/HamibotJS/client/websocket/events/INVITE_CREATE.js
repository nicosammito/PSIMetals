'use strict';

module.exports = (client, data) => {

    //get related guild
    const guild = client.guilds._addOne(data.guild_id, null);
    const invite = guild.invites._addOne(data.code, data);

    /**
     * Emitted whenever a user create a invite for a guild
     * @event Events.INVITE_CREATE
     * @param {Invite} invite The invite of guild that was created
     */
    client.emit("inviteCreate", invite);

}