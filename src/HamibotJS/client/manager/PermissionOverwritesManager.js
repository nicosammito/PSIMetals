const CacheManager = require("./CacheManager");
const PermissionOverwrite = require("../structures/PermissionOverwrite");

class PermissionOverwritesManager extends CacheManager{

    _create(client, unique, data) {

        //create new PermissionOverwrite object and patch data
        const permissionOverwrite = new PermissionOverwrite(client, unique, data.channel);
        return permissionOverwrite._patch(data);

    }

}

module.exports = PermissionOverwritesManager;