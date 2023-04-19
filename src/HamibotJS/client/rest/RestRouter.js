const methods = ['get', 'post', 'delete', 'patch', 'put'];
const nothing = () => {}


module.exports = (manager) => {

    //local variables for route building
    let route = ["https://api.hamibot.io"];
    let method;

    //proxy handler
    const handler = {
        get(target, property) {

            //check weather property is requested method
            if (methods.includes(property)) {
                method = property;
                return options => {

                    //check some important stuff, otherwise request can't be performed
                    if (route.length === 1) throw new Error("Internal route building failed: Route isn't defined");
                    if (!methods.includes(method)) throw new Error("Internal route building failed: Method isn't defined");

                    //check weather argument is typeof json object
                    if (options) {
                        try {
                            JSON.parse(JSON.stringify(options))
                        } catch (e) {
                            throw new Error("Internal route building failed: Argument isn't a JSON object")
                        }
                    }

                    //perform request to api
                    return manager.makeRequest(method, route.join("/"), options)
                }
            }

            //check parts of route
            if (!/^([0-9]{15})/.test(route[route.length - 1]) && /^([0-9]{15})/.test(property.toString()))
                route.push(property.toString());
            else if (/^([0-9]{15})/.test(route[route.length - 1]) && !/^([0-9]{15})/.test(property.toString())) {
                route.push(property.toString());
            } else if (route[route.length - 1] === "https://api.hamibot.io" && !/^([0-9]{15})/.test(property.toString())) {
                route.push(property.toString());
            } else {
                throw new Error("Internal route building failed: Invalid route");
            }
            return new Proxy(nothing, handler);
        }
    }
    return new Proxy(nothing, handler);

}