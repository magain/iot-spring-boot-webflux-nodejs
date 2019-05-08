/*
    Import protobuf files
 */
var LogEventMessage       = require('../application/domain/model/LogEventMessage_pb.js');
/*
    Import Application Service
 */
var logger_service        = require('../application/domain/usecases/logger_service.js');
/*
    Exported Functions
 */
exports.handleMessage = function(message) {
    var bytes = Array.prototype.slice.call(message, 0);

    // Deserialize the protobuf
    var converted_message = proto.LogEventMessage.deserializeBinary(bytes);

    // log the received LogEvent message
    logger_service.logToFile(converted_message);
};