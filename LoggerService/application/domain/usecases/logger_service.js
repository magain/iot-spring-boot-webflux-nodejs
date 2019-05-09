/*
    Import Infrastructure Adapter
 */
var logger = require('../../../infrastructure/logger_adapter');
/*
    Exported Functions
 */
exports.logToFile = function(data) {
    logger.info("timestamp: " + data.getTimestamp() + ", deviceId: " + data.getDeviceid() + ", event: " + data.getEvent());
};