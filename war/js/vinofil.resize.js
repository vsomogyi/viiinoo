/**
 * @author PTihomir
 */
var debug = {};
$(document).ready(function () {
	
	resizeImages(150, 150);

});

function resizeImages(width, height){
	$('.resize').each(function(){
		var scale,
			img = this;
		scale = Math.max(
		        (0 || img.width) / img.width,
		        (0 || img.height) / img.height
		    );
		if (scale >= 1) {
		    scale = Math.min(
		        (width || img.width) / img.width,
		        (height || img.height) / img.height
		    );
		}
		var widtht = parseInt(img.width * scale, 10);
		var heightt = parseInt(img.height * scale, 10);
		img.width = widtht;
		img.height = heightt;
		$(img).css({
			'left': (width - widtht) / 2,
			'top': (height - heightt) / 2,
			'position': 'absolute'
		});

	});
};
