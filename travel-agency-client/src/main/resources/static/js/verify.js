$(function(){
	
	$(document).on('submit','form', function(){
		
		var form = $(this);
		var btn = form.find('button');
 		$(this).find('.alert').addClass('hidden');
		
		var pin = getPin();
		
		if(pin.length === 4){
			btn.hide();
			$.postJSON('/verify', { "code" : pin})
			.done(function(res){
				if(res.status === 'OK'){
					form.find('.alert-success').removeClass('hidden');
					setTimeout(function(){
						document.location.href = res.redirectUrl;
					}, 2000);
				}else{
					form.find('.alert-danger').removeClass('hidden');
					btn.show();
				}
				
			})
			.fail(function(){
				form.find('.alert-danger').removeClass('hidden');
				btn.show();
			}).always(function(){
				
			});
		}
		
		return false;
		
		
	});
	
	$(document).on('click', '.resend', function(){
		var link = $(this);
		link.hide();
		$.putJSON('/verify', {}).always(function(){
			link.show();
			alert('The code was sent');
		});
	})
	
	$(document).on('keyup', 'input.number', function(){
		var input = $(this);
		if(input.val()){
			input.parent().next().find('.number').focus();
		}
		var submitBtn = $('.verify-form button');
		var pin = getPin();
		if(pin && pin.length === 4){
			submitBtn.removeClass('disabled');
		}else{
			submitBtn.addClass('disabled');
		}
	});
	
	function getPin(){
		var pin = '';
		for(var i = 1; i < 5; i++){
			pin += digit(i);
		}
		function digit(n){
			return $('input[name=d'+n+']').val();
		}
		return pin;
	}
	
	$('input[name=d1]').focus();
});