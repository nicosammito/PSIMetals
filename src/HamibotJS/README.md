# HamibotJS

HamibotJS is a library for creating Hamibot Modules

## Installation

Use the package manager npm to install HamibotJS.

```
npm install hamibot.js
```

## Usage

```js
const Hamibot = require("hamibot.js");
const client = new Hamibot.Client("your token"); //you can get a token by creating a Hamibot developer account 

client.on("ready", () => {
    console.log("Hamibot Module is up and running");
})
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)