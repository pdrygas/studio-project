jQuery(document).ready(function ($) {
	$('#debug').text('test');

	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/rest/hello'

	}).done(function (data, textStatus, jqXHR) {
		// showMeYourJqXHR('When GET /rest/hello is done', jqXHR);
		// showMeYourCookies('When GET /rest/hello is done');

		var csrfToken = jqXHR.getResponseHeader('X-CSRF-TOKEN');
		if (csrfToken) {
			var cookie = JSON.parse($.cookie('helloween'));
			if(cookie != null) {
				cookie.csrf = csrfToken;
				$.cookie('helloween', JSON.stringify(cookie));
			} else {
				var cookie = JSON.stringify({method: 'GET', url: '/', csrf: jqXHR.getResponseHeader('X-CSRF-TOKEN')});
				$.cookie('helloween', cookie);
			}
			
		}

		// $('#helloweenMessage').html(data.message);

	}).fail(function (jqXHR, textStatus, errorThrown) {
		// showMeYourJqXHR('When GET /rest/hello fails', jqXHR);
		// showMeYourCookies('When GET /rest/hello fails');

		if (jqXHR.status === 401) { // HTTP Status 401: Unauthorized
			var cookie = JSON.stringify({method: 'GET', url: '/', csrf: jqXHR.getResponseHeader('X-CSRF-TOKEN')});
			$.cookie('helloween', cookie);
		} else {
			console.error('Houston, we have a problem...');
		}
	});

	$('#loginform').submit(function (event) {
		event.preventDefault();

		// showMeYourCookies('At loginform submission');

		var cookie = JSON.parse($.cookie('helloween'));
		var data = 'username=' + $('#username').val() + '&password=' + $('#password').val();
		$.ajax({
			data: data,
			headers: {'X-CSRF-TOKEN': cookie.csrf},
			timeout: 1000,
			type: 'POST',
			url: 'http://localhost:8080/login'

		}).done(function(data, textStatus, jqXHR) {
			// showMeYourJqXHR('When loginform is done', jqXHR);
			// showMeYourCookies('When loginform is done');

			$('#debug').text('dziala');
			$('#loginform').hide();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			// showMeYourJqXHR('When loginform fails', jqXHR);
			// showMeYourCookies('When loginform fails');

			$('#debug').text('zle passy');
		});
	});
});