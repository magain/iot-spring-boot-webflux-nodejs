/*
    Include libraries
 */
var winston = require('winston');
var path = require("path");
var fs = require("fs");

// ensure log directory exists
var logDirectory = path.resolve(".", "logs");
fs.existsSync(logDirectory) || fs.mkdirSync(logDirectory);

// create and configure winston logger
var logger = winston.createLogger({
    level: 'info',
    format: winston.format.combine(
        winston.format.timestamp(),         // the timestamp when logging
        winston.format.json()               // the log event JSON
    ),
    transports: [
        new winston.transports.Console({}),
        new winston.transports.File({
            filename: './logs/logEvent.log',
            "timestamp": true
        })
    ]
});

// export the winston logger
module.exports = logger;