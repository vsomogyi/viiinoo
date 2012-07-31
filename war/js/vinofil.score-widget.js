/**
 * @author ptihomir
 */
(function ($) {

	$.fn.vinoScore = function () {
		var scoreDOM = '';
			scoreDOM += '<div class="score">'; 
			scoreDOM += '<div class="rating-1 rating-icon" rel="1"></div>'; 
			scoreDOM += '<div class="rating-2 rating-icon" rel="2"></div>'; 
			scoreDOM += '<div class="rating-3 rating-icon" rel="3"></div>'; 
			scoreDOM += '<div class="rating-4 rating-icon" rel="4"></div>'; 
			scoreDOM += '<div class="rating-5 rating-icon" rel="5"></div>';
			scoreDOM += '</div>';
		
		this.each(function () {
			var $input = $(this),
				parentNode = $input.parent(),
				initValue = $input.val(),
				score = $(scoreDOM).appendTo(parentNode),
				ratings = score.children();
			
//			score.attr('id', $input.attr('id'). + '_score');
			// hide all input
			$input.hide();
			// set the init value
			ratings.filter('[rel='+initValue + ']').addClass('selected');
		
			parentNode.bind('mouseleave', function (e) {
				ratings.removeClass('hover');
			});
			
			ratings.bind('mouseenter', function (e) {
				ratings.removeClass('hover');
				$(this).addClass('hover');
				
				e.stopPropagation();
			})
			.bind('click', function (e) {
				var rel = ratings.filter('.selected').attr('rel');
				ratings.removeClass('selected');
				
				if (rel !== $(this).attr('rel')) {
					$(this).addClass('selected');
					$input.val($(this).attr('rel'));
				} else {
					$input.val('');
				}
				
				e.stopPropagation();
			});
		
		});
		
		return this;

	};
})(jQuery);
