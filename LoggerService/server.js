/*
    Include libraries
 */
var dgram                 = require('dgram');
/*
    Include my modules
 */
var udp_controller_adapter  = require('./presentation/udp_controller_adapter.js');

// Configure the UDP Server
var PORT = 7070;
var HOST = '127.0.0.1';
var server = dgram.createSocket('udp4');


server.on('listening', function () {
    var address = server.address();
    console.log('UDP Server listening on ' + address.address + ":" + address.port);
});

// Gets invoked when message comes in
server.on('message', function (message, rinfo) {
    udp_controller_adapter.handleMessage(message);
    var ack = Buffer.from("ack");

    // send ACK message back to sender
    // detect sender information (ip & port) rinfo object as part of the received message
    server.send(ack, 0, ack.length, rinfo.port, rinfo.address, function(err, bytes) {
        // do nothing
    });
});

// Start the UDP server
server.bind(PORT, HOST);