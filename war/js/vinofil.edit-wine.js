/**
 * @author zeus
 */
$(document).ready(function () {
	
	// hide add new winery fields
	$('div.new-winery').hide();
	
	// hide additional notes
	$('div.additional-notes').hide();
	
	setDOMEvents();
});

function setDOMEvents () {
	
	// add new winery
	$('a.add-field').click(function(e) {
		e.preventDefault();
		
		var $this = $(this);
		
		$('div.new-winery, div.select-winery').toggle();
		$('#wineryName').validationEngine('hidePrompt');
		$('#newWineryName').validationEngine('hidePrompt');
		$this.toggleClass('add-field-selected');
	});
	
	// add new aged line
	$('a#add-aging-line').click(function(e) {
		e.preventDefault();
		
		var $row = $('div.production-details:last');
		
		var $newRow = $row.clone();

		// replace the name attributes, increment the number in it		
		$newRow.find('input, select').each(function() {
			var $el = $(this);
			
			var name = $el.attr('name');
			var id = $el.attr('id');
			var i = parseInt(name.charAt(9));
			
			name = name.replace(i, i + 1);
			id = id.replace(i, i + 1);
			$el.attr('name', name).attr('id', id).val('');
		});
		
		$newRow.hide().insertAfter($row).slideDown(200, function(){
			$('#content form').validationEngine('reattach');
		});
		
		if ($('div.production-details').length == 10) {
			$('a#add-aging-line').remove();
		}
		
		
	});
	
	// add new varietal composition line
	$('a#add-varietal-line').click(function(e) {
		e.preventDefault();
		
		var $row = $('div.varietal-composition:last');
		
		var $newRow = $row.clone();

		// replace the name attributes, increment the number in it		
		$newRow.find('input, select').each(function() {
			var $el = $(this);
			
			var name = $el.attr('name');
			var id = $el.attr('id');
			var i = parseInt(name.charAt(16));
			
			name = name.replace(i, i + 1);
			id = id.replace(i, i + 1);
			$el.attr('name', name).attr('id', id).val('');
		});
		
		$newRow.hide().insertAfter($row).slideDown(200);
		
		if ($('div.varietal-composition').length == 10) {
			$('a#add-varietal-line').remove();
		}
		
		$('#content form').validationEngine('reattach');
		
	});
	
	// add new note line
	$('a#add-note-line').click(function(e) {
		e.preventDefault();
		
		var $row = $('div.additional-notes:last');
		
		var $newRow = $row.clone();

		// replace the name attributes, increment the number in it		
		$newRow.find('textarea, select').each(function() {
			var $el = $(this);
			
			var name = $el.attr('name');
			var id = $el.attr('id');
			var i = parseInt(name.charAt(14));
			
			name = name.replace(i, i + 1);
			id = id.replace(i, i + 1);
			$el.attr('name', name).attr('id', id).val('');
		});
		
		$newRow.insertAfter($row).slideDown(200);
		
		if ($('div.additional-notes').length == 5) {
			$('a#add-note-line').remove();
		}
		
		$('#content form').validationEngine('reattach');
		
	});
	
}