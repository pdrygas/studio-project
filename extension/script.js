jQuery(document).ready(function ($) {
    var port = chrome.extension.connect({
        name: "Background Page commmunication"
    });

    port.postMessage({command: "isTokenSet"});
    port.onMessage.addListener(function(msg) {
    	if (msg.command == "tokenState")
    		if (msg.state == "SET") {
                $('#loginform').hide();
                $('#loggedIn').show();
            }
    });

	$('#loginform').submit(function (event) {
		event.preventDefault();
		var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: 'http://localhost:8080/login'
		}).done(function(data, textStatus, jqXHR) {
            var xtoken = jqXHR.getResponseHeader('X-AUTH-TOKEN');
			port.postMessage({command: "save", token: xtoken});
			$('#loginform').hide();
			$('#loggedIn').show();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			$('#debug').text('zle passy');
		});
	});
});
