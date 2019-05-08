var winston = require('winston');
console.log('create logger');
var logger = winston.createLogger({
    level: 'info',
    format: winston.format.combine(
        winston.format.timestamp(),
        winston.format.json()
    ),
    transports: [
        new winston.transports.Console({}),
        new winston.transports.File({
            filename: './logs/logEvent.log',
            "timestamp": true
        })
    ]
});
module.exports = logger;