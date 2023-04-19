'use strict';

const Invite = require("../../structures/Invite");

module.exports = (client, data) => {

    //get guild and delete invite
    const guild = client.guilds._addOne(data.guild_id, null);
    let invite = guild.invites._remove(data.code);

    if (!invite) invite = new Invite(client, data.code)._patch(data);

    /**
     * Emitted whenever a user deletes a invite for a guild
     * @event Events.INVITE_DELETE
     * @param {Invite} invite The invite of guild that was deleted
     */
    client.emit("inviteDelete", invite);

}