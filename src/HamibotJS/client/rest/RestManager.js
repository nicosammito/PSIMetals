const routeBuilder = require('./RestRouter');
const fetch = require('node-fetch');

class RestManager {

    constructor(client) {
        this.client = client;
    }

    get api() {
        return routeBuilder(this);
    }

    async makeRequest(method, url, options) {

        return new Promise((resolve, reject) => {

            //request object for fetch request
            const requestObject = {
                method: method.toString().toUpperCase(),
                headers: {
                    "Authorization": `bearer ${this.client.token}`,
                    'Content-Type': 'application/json',
                },
            }

            //assign body when request has body parameters
            if (options) Object.assign(requestObject, {body: JSON.stringify(options)})

            //perform request
            fetch(url, requestObject).then(response => {

                if (response.status === 200 && response.headers.get("content-type").indexOf("application/json") !== -1)
                    response.json().then(json => resolve(json['response']));
                else if (response.status.toString().startsWith("2"))
                    resolve();
                else
                    response.text().then(json => reject(json));

            }).catch(() => {
                reject("Request Failed: Reason undefined")
            })
        })
    }

}

module.exports = RestManager;