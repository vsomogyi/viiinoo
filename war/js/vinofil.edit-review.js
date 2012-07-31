/**
 * @author zeus
 */

$(document).ready(function () {
	
	// hide add new wine
	$('div.new-wine').hide();
	
	setReviewEvents();
	
	$('.score-widget input').vinoScore();
	
	$('#content form').validationEngine('attach',
		{
			promptPosition : "topRight",
			onValidationComplete: function(form, status){
			    var scoreStatus = true;
			    $('.score-widget input').each(function () {
			    	var $input = $(this),
			    		val = $input.val(),
			    		required = $input.is('[class*=required]');
			    	
			    	if (val !== "" || required) {
			    		if (!(/^[1-5]$/.test(val))) {
			    			scoreStatus = false;
			    			$input.siblings('.score').validationEngine('showPrompt','Select score from 1 to 5', 'error', 'topRight:-100', true);
			    		}
			    	}
			    	
			    });
			    return status && scoreStatus;
			}  			
		
		});
	
});


function setReviewEvents () {
	
	$('a#add-new-wine').toggle(function(e) {
		e.preventDefault();
		
		$('a#add-new-wine').text('Select a wine');
		
		$('.select-wine').slideUp(300);
		$('.new-wine').slideDown(300);
		
		$('#content form').validationEngine('hideAll');
		
	},
	function(e) {
		e.preventDefault();
		
		$('a#add-new-wine').text('Add new wine');
		
		$('.new-wine').slideUp(300);
		$('.select-wine').slideDown(300);
		
		$('#content form').validationEngine('hideAll');
		
	});	
}
