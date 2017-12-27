
(function($) {
	 $.postJSON = function(url, data) {
		 return $.doReq('POST', url, data);
	    };
	    
	    $.putJSON = function(url, data) {
	    	return $.doReq('PUT', url, data);
	    };
	    
	    $.doReq = function(method, url, data) {
	    	return $.ajax({
		  		  dataType: "json",
		  		  method : method,
		  		  url: url,
		  		  data: JSON.stringify(data),
		  		  contentType: 'application/json', 
		  		  accepts: { json: "application/json"}
	    	});
	    };
}(jQuery));


setTimeout(function(){
var connection = new WebSocket('ws://localhost:8002/websocket/conversation',['soap', 'xmpp']);
// When the connection is open, send some data to the server
connection.onopen = function () {
  connection.send('Ping'); // Send the message 'Ping' to the server
};

// Log errors
connection.onerror = function (error) {
  console.log('WebSocket Error ' + error);
};

// Log messages from the server
connection.onmessage = function (e) {
  console.log('Server: ' + e.data);
};
}, 1000);