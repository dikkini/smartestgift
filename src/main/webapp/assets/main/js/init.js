$(document).ready(function($) {

	// validation messages
	$.extend($.validator.messages, {
		required: validationMessages['validate.required'],
		equalTo: validationMessages['validate.equalTo'],
		email: validationMessages['validate.email'],
		maxlength: $.validator.format(validationMessages['validate.maxlength']),
		minlength: $.validator.format(validationMessages['validate.minlength'])
	});

	// Prevent disabled buttons
	$('button').click(function(e) {
		if ($(this).hasClass("disabled")) {
			e.preventDefault();
			return false;
		}
	});

	// GA
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

	ga('create', 'UA-89956285-1', 'auto');
	ga('send', 'pageview');
});