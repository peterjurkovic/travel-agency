
(function($) {
	 	$.postJSON = function(url, data) {
	 		return $.doReq('POST', url, data);
	    };
	    
	    $.getJSONp = function(url, data) {
	 		return $.doReq('POST', url, data);
	    };
	    
	    $.putJSON = function(url, data) {
	    	return $.doReq('PUT', url, data);
	    };
	    
	    $.doReq = function(method, url, data) {
	    	return $.ajax({
		  		  dataType: "json",
		  		  method : method,
		  		  crossDomain: true,
		  		  url: url,
		  		  data: JSON.stringify(data || {} ),
		  		  contentType: 'application/json', 
		  		  accepts: { json: "application/json"}
	    	});
	    };
}(jQuery));

