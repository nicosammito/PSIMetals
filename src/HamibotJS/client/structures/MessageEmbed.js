const Base = require("./Base");

class MessageEmbed extends Base {

    constructor(client) {
        super(client);
    }

    _patch(data) {

        this._patched();

        this.title = data['title'] ?? this.title;
        this.description = data['description'] ?? this.description;
        this.url = data['url'] ?? this.url;
        this.timestamp = 'timestamp' in data ? new Date(data['timestamp']).getTime : this.timestamp;
        this.color = data['color'] ?? this.color;
        this.footer = data['footer'] ?? this.footer;
        this.image = data['image'] ?? this.image;
        this.thumbnail = data['thumbnail'] ?? this.thumbnail;
        this.video = data['video'] ?? this.video;
        this.provider = data['provider'] ?? this.provider;
        this.author = data['author'] ?? this.author;
        this.fields = 'fields' in data ? data['fields'].map(field => field) : this.fields;

        return this;
    }

}

module.exports = MessageEmbed;