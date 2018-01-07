$(function(){
	var conversationUrl = $.trim($('#conversationUrl').text());
	
	var socket = new SockJS(conversationUrl + '/ws');
	var stompClient = Stomp.over(socket);
	
	stompClient.connect( {}, function(frame) {
		console.log('connected');

		
		stompClient.subscribe("/app/conversations", function(message) {
			printItems(message);
		});
		
		stompClient.subscribe("/topic/conversations", function(message) {
			var item = $.parseJSON(message.body);
			console.log('NEW: ', item);
			$('#items').prepend(renderItem(item));
			higlightFist();
		});
	
		
	});
	
	function higlightFist(){
		var firstItem = $('#items tr').eq(0);
		firstItem.addClass('new');
		setTimeout(function(){
			firstItem.removeClass('new');
		},3000);
	}
	
	function printItems(message){
		var list = $.parseJSON(message.body);
		var html = '';
		for(var i in list){
			html += renderItem(list[i]);
		}
		$('#items').html(html);
	}
	
	
	
	function renderItem(item){
		return '<tr>'+
        	 '<td>'+time_ago(item.date)+'</td>'+
        	 '<td>'+item.participants[0].name+'</td>'+
	         '<td>'+agentName(item)+'</td>'+
	         '<td>'+(item.phoneNumber ? item.phoneNumber : '-')+'</td>'+
	         '<td>'+
	        	'<a href="/conversations/'+item.id+'">Show</a>'+
	         '</td>'+
	       '</tr>';
		
		function agentName(item){
			for(var j in item.participants){
				if(item.participants[j].type === 'AGENT')
					return item.participants[j].name; 
			}
			return 'Not assigned';
		}
	}
})


function time_ago(time) {

	  switch (typeof time) {
	    case 'number':
	      break;
	    case 'string':
	      time = +new Date(time);
	      break;
	    case 'object':
	      if (time.constructor === Date) time = time.getTime();
	      break;
	    default:
	      time = +new Date();
	  }
	  var time_formats = [
	    [60, 'seconds', 1], // 60
	    [120, '1 minute ago', '1 minute from now'], // 60*2
	    [3600, 'minutes', 60], // 60*60, 60
	    [7200, '1 hour ago', '1 hour from now'], // 60*60*2
	    [86400, 'hours', 3600], // 60*60*24, 60*60
	    [172800, 'Yesterday', 'Tomorrow'], // 60*60*24*2
	    [604800, 'days', 86400], // 60*60*24*7, 60*60*24
	    [1209600, 'Last week', 'Next week'], // 60*60*24*7*4*2
	    [2419200, 'weeks', 604800], // 60*60*24*7*4, 60*60*24*7
	    [4838400, 'Last month', 'Next month'], // 60*60*24*7*4*2
	    [29030400, 'months', 2419200] // 60*60*24*7*4*12, 60*60*24*7*4
	  ];
	  var seconds = (+new Date() - time) / 1000,
	    token = 'ago',
	    list_choice = 1;

	  if (seconds < 5) {
	    return 'Just now'
	  }
	  if (seconds < 0) {
	    seconds = Math.abs(seconds);
	    token = 'from now';
	    list_choice = 2;
	  }
	  var i = 0,
	    format;
	  while (format = time_formats[i++])
	    if (seconds < format[0]) {
	      if (typeof format[2] == 'string')
	        return format[list_choice];
	      else
	        return Math.floor(seconds / format[2]) + ' ' + format[1] + ' ' + token;
	    }
	  return time;
	}