var mongo = require('mongodb').MongoClient;
var client = require('socket.io').listen(4000).sockets;
//Connect to mongo db
mongo.connect();

// Connect to mongo
mongo.connect('mongodb://root:keerthi44@ds115569.mlab.com:15569/asedemo', function(err, db){
    if(err){
        throw err;
    }
console.log('MongoDB connected success');
});

