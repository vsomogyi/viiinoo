(function($) {
	$.fn.ellipsis = function(enableUpdating, readMoreUrl){
		var s = document.documentElement.style;
		return this.each(function(){
			var el = $(this);
			if(el.css("overflow") == "hidden"){
				var originalText = el.html();
				var w = el.width();
				
				var t = $(this.cloneNode(true)).hide().css({
                    'position': 'absolute',
                    'width': 'auto',
                    'overflow': 'visible',
                    'max-width': 'inherit',
                    'white-space': 'nowrap'
                });
				el.after(t);
				
				var text = originalText;
				while(text.length > 0 && (t.width() / 1.5) > el.width()){
					text = text.substr(0, text.length - 1);
					if (readMoreUrl) {
						t.html(text + '<a href="' + readMoreUrl + '"> more...</a>');
					} else {
						t.html(text + '<a href="#">...</a>');
					}
				}
				el.html(t.html());
//				t.remove();

				// if it is just 3 dots, on click show the original text
				if (!readMoreUrl) {
					el.find('a').bind('click', function (e) {
						$(this).parent().html(originalText);
						e.preventDefault();
					});
				}
				
				if(enableUpdating == true){
					var oldW = el.width();
					setInterval(function(){
						if(el.width() != oldW){
							oldW = el.width();
							el.html(originalText);
							el.ellipsis();
						}
					}, 200);
				}
			}
		});
	};
})(jQuery);