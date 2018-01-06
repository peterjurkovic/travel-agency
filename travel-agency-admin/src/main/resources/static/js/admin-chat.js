$(function(){
	var conversationUrl = $.trim($('#conversationUrl').text());
	var conversationId = $.trim($('#conversationId').text());
	var participant = $.parseJSON( $('#participant').text() );
	
	var socket = new SockJS(conversationUrl + '/ws');
	var stompClient = Stomp.over(socket);
	var headers = { participantId : participant.id };
	var autoconnect = false; 
	
	console.log(conversationUrl, conversationId, participant);
	
	loadLasMessage();
	var joinBtn = $('.btn.join');
	if(joinBtn.length){
		$(document).on('click', '.btn.join', function(){
			var url = conversationUrl + '/conversations/' + conversationId + "/join";
			console.log('joining conversation...' + url);
			$.putJSON(url, participant)
			.done(function(agent){
				joinBtn.remove();
				connectToConversation(stompClient, headers);
				showForm();
				agent.active = true;
				$('.participants').append(renderParticipant(agent));
				console.log('Joined conversation', agent);
			})
			.fail(function(){
				alert('Unable to join the conversation, please try it again later.');
			});
			return false;
		});
	}else{
		showForm();
	}
	
	stompClient.connect( headers, function(frame) {
		console.log('connected');

		
		stompClient.subscribe("/app/participants/"+conversationId, function(message) {
			console.log('Refreshing participants');
			printParticipants(message);
		});
		
		
		stompClient.subscribe("/topic/participants/"+conversationId, function(message) {
			var participantEvent = $.parseJSON(message.body);
			console.log('Event occured', participantEvent);
			
				$('.participants li').each(function(){
					var li = $(this);
					var id = li.attr('data-id');
					
					if(id === participantEvent.id){
						if(participantEvent.type === 'LEFT'){
							li.removeClass('true');
						}else{
							li.addClass('true');
						}
					}
				});
			
			
			
		});
		
		connectToConversation(stompClient, headers);
		
		
	} );
	
	
	$(document).on('click', '.send-message',submitMessage)
	$(document).on('keypress', function(e){
		if(e.which == 13) submitMessage();
	});
	
	function submitMessage(){
		var messageContent = $.trim( $('.chat-input-val').val() );
		
		if(messageContent){
			var message = {
				content : messageContent,
				conversationId : conversationId,
				participantId : participant.id
			};
			console.log('Sending message: ' + messageContent);
			stompClient.send("/app/chat/"+ conversationId , {},  JSON.stringify(message));
			$('.chat-input-val').val('');
		}else{
			console.log('Nothing to be sent..');
		}
		return false;
	}
	
	function showForm(){
		$('.chat-input').removeClass('hidden');
	}
	
	function connectToConversation(stompClient, headers){
		console.log('subscribing to conversation..');
		stompClient.subscribe("/topic/chat/"+conversationId, function(message) {
			var body = $.parseJSON(message.body);
			console.log(body);
			var html = renderMessage(body);
			$('.chat-messages').append(html);
			scrollDown();
		}, headers);
	}
	
	function printParticipants(message){
		var list = $.parseJSON(message.body);
		var html = '';
		
		for(var i in list){
			html += renderParticipant(list[i]);
		}
		$('.participants').html(html);
		
	}
	
	function renderParticipant(p){
			return '<li data-id="'+p.id+'" class="list-group-item '+p.active+'">'+
				'<i class="fa fa-user" aria-hidden="true"></i> &nbsp;'+
				'<strong>'+p.name+'</strong>'+
				'</li>';	
	}
	
	
	function renderMessage(message){
		var time = message.created;
		return  '<li class="list-group-item">'+
			'<span class="chat-head">'+
				'<span class="chat-time">'+ time_ago(time) +'</span>'+
				'<span class="chat-user">'+message.participant.name+'</span>'+
			'</span>'+
			'<span class="chat-content">'+message.content +'</span>'+
		'</li>';
	}
	
	function loadLasMessage(){
		$.getJSON(conversationUrl + '/conversations/'+conversationId+'/messages', function(data){
			var html = '';
			
			for(var i in data){
				html += renderMessage( data[i] );
			}
			
			$('.chat-messages').html(html);
			scrollDown();
		});
	}
	
	function scrollDown(){
		var ul = $('.chat-messages');
		ul.parent().scrollTop(ul[0].scrollHeight);
	}

});



function initTimeRefresher(){
	 setInterval(function(){
		 $('#chat-wrapp .chat li').each(function(){
			var msg = $(this);
			msg.find('.datetime').text(time_ago(msg.attr('data-time')));
		 });
	 }, 5000);
}

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



