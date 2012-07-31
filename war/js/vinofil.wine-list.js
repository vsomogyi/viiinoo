/**
 * @author PTihomir
 */
$(document).ready(function () {
	
	// hide edit button
	$('.wine-review-ratings .actions').width(0);
	// hide rating list
	$('.wine-reviews').hide();
	
	$('.ellipsis').each(function() {
		var that = $(this),
			url = that.siblings('.wine-name').children('a').attr('href');
		
		that.ellipsis(false, url);
	});
	setDOMEvents();
});

function setDOMEvents () {
	var timeout = null;

	var timeoutEditButton = null;
	
	$('.wine-info').bind('mouseenter',function () {
		var $this = $(this);
		var $rating = $this.find('div.rating-icon');
		$rating.addClass('rating-hover');
		
		if (!$rating.hasClass('rating-clicked')) {
			timeout = setTimeout(function(){
			
				var detail = $('<div class="details-btn">REVIEWS</div>')
					.hide()
					.appendTo($rating)
					.slideDown(200);
				
			}, 1000);
		}
		
	});
	
	$('.wine-info').bind('mouseleave',function () {
		clearTimeout(timeout);
		var $this = $(this)
		var $rating = $this.find('div.rating-icon');
		
		if ($rating.children('div').length) {
			$rating.children('div').stop('true').slideUp(200, function(){
				$(this).remove();
				$rating.removeClass('rating-hover');
			});
		} else {
			$rating.removeClass('rating-hover');
		}
	});

	$('.wine-info div.rating-icon').toggle(function () {
			clearTimeout(timeout);
			
			var $this = $(this);
			
			if ($this.children('div').length) {
				$this.children('div').stop('true').slideUp(200, function(){
					$(this).remove();
					$this.removeClass('rating-hover');
				});
			} else {
				$this.removeClass('rating-hover');
			}
			
			$this.addClass('rating-clicked');
			
			
			
			var header = $this.parent().next();
			
			header.slideDown(300);		
			$this.children('div').remove();
		},
		function () {
			var $this = $(this)
			
			var header = $this.parent().next();
			
			header.slideUp(300, function () {
				$this.removeClass('rating-clicked');
			});		

	});	
	
	
	$('.wine-review-ratings .rating-cell').bind('mouseenter', function () {
		var $this = $(this);
		timeoutEditButton = setTimeout(function () {
			$this.find('.actions').stop(true).animate({width: '50px'},500);		
		}, 1000);
	});
	
	$('.wine-review-ratings .rating-cell').bind('mouseleave', function () {
		clearTimeout(timeoutEditButton);
		$(this).find('.actions').stop(true).animate({width: 0},500);
	});
	
}
