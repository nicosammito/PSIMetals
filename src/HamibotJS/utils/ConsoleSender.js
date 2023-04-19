sendToConsoleAsClient = (message) => {
    console.log(`[ Module -> Server ] : ${message}`);
}

sendToConsoleAsGateway = (message) => {
    console.log(`[ Server -> Module ] : ${message}`);
}


module.exports = {
    sendToConsoleAsClient: sendToConsoleAsClient,
    sendToConsoleAsGateway : sendToConsoleAsGateway
};