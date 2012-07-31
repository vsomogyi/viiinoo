/**
 * @author PTihomir
 */
var debug = {};
$(document).ready(function () {
	
	debug.imageupload = new Imageupload($('.image-list'));
	
	$('#wineEnthusiast').validationEngine('attach', {promptPosition : "topRight"});

});

var Imageupload = function (container) {
	this.container = container;
	
	this.uploadTile = container.find('.upload-image');
	
	this.uploadTile.find('label').hide();
	
	this.setDOMHandlers();
	
};

Imageupload.prototype = {
		
    options: {
        // The maximum allowed file size:
        maxFileSize: 1000000,
        minFileSize: 2000,
        // The regular expression for allowed file types, matches
        // against either file type or file name:
        acceptFileTypes:  /(gif|jpeg|png)$/i,
        // The regular expression to define for which files a preview
        // image is shown, matched against the file type:
        previewFileTypes: /^image\/(gif|jpeg|png)$/,
        // The maximum width of the preview images:
        previewMinWidth: 150,
        // The maximum height of the preview images:
        previewMinHeight: 150,
        // By default, preview images are displayed as canvas elements
        // if supported by the browser. Set the following option to false
        // to always display preview images as img elements:
        previewAsCanvas: true,
        // The file upload template that is given as first argument to the
        // jQuery.tmpl method to render the file uploads:

    },
	
	setDOMHandlers: function () {
		
		var that = this;
 
		this.uploadTile.delegate('input', 'change', {imagelist: this}, function (e) {
			
			// validate files
			var files = that.validate(this.files);
			
			// TODO on error
			that.renderUpload(this.files);
				
		});
		
	},

    formatFileSize: function (file) {
        if (typeof file.size !== 'number') {
            return '';
        }
        if (file.size >= 1000000000) {
            return (file.size / 1000000000).toFixed(2) + ' GB';
        }
        if (file.size >= 1000000) {
            return (file.size / 1000000).toFixed(2) + ' MB';
        }
        return (file.size / 1000).toFixed(2) + ' KB';
    },

	
    renderUpload: function (files) {
        var that = this,
        	uploadTile = that.uploadTile,
        	options = that.options;
 
        uploadTile.find('.preview').each(function (index, node) {
        	if (files[index].error) {
            	$(node).children().remove();
        		that.uploadTile.addClass('error-image');
        		that.uploadTile.find('input').replaceWith('<input type="file" name="file[0]"/>');;
                var cont = $('<ul class="hide-parent"></ul>').appendTo(node),
                	file = files[index],
                	error = file.error,
                	msg;
                
                $('<li></li>').text('Name: ' + file.name).appendTo(cont);
                $('<li></li>').text('Size: ' + that.formatFileSize(file)).appendTo(cont);
                if (file.error === 'maxFileSize') {
                	msg = 'File is too big';
                } else if (file.error === 'minFileSize') {
                	msg = 'File is too small';
                } else if (file.error === 'acceptFileTypes') {
                	msg = 'Filetype not allowed';
                } else if (file.error === 'maxNumberOfFiles') {
                	msg = 'Max number of files exceeded';
                } else {
                	msg = file.error;
                }
                $('<li class="error-message"></li>').text('Error: ' + msg).appendTo(cont);
                return;
        	}

        	that.uploadTile.removeClass('error-image'); 

        	that.loadImage(
                files[index],
                function (img) {
                	var width = img.width,
                		height = img.height;
                	
                	$(node).children().remove();
                    
                    var cont = $('<div class="hide-parent"></div>').appendTo(node);
                    
                    $(img)
                    .hide()
                    .appendTo(cont)
                    .css({
                    	'top': (options.previewMinHeight - height) / 2,
                    	'left': (options.previewMinWidth - width) / 2,
                    	'position': 'absolute'
                    })
                    .show();
                },
                {
                    minWidth: that.options.previewMinWidth,
                    minHeight: that.options.previewMinHeight,
                    fileTypes: that.options.previewFileTypes,
                    canvas: that.options.previewAsCanvas,
                    image: node.find
                }
            );
        });
        return;
    },
    
    // Loads an image for a given File object.
    // Invokes the callback with an img or optional canvas
    // element (if supported by the browser) as parameter:
    loadImage: function (file, callback, options) {
        var that = this,
            url,
            img;
        if (!options || !options.fileTypes ||
                options.fileTypes.test(file.type)) {
            url = this.createObjectURL(file);
            img = $('<img>').bind('load', function () {
                $(this).unbind('load');
                that.revokeObjectURL(url);
                callback(that.scaleImage(img[0], options));
            }).css('position','absolute').prop('src', url);
            if (!url) {
                this.loadFile(file, function (url) {
                    img.prop('src', url);
                });
            }
        }
    },
	
	createObjectURL: function (file) {
	    var undef = 'undefined',
	        urlAPI = (typeof window.createObjectURL !== undef && window) ||
	            (typeof URL !== undef && URL) ||
	            (typeof webkitURL !== undef && webkitURL);
	    return urlAPI ? urlAPI.createObjectURL(file) : false;
	},
	
    revokeObjectURL: function (url) {
        var undef = 'undefined',
            urlAPI = (typeof window.revokeObjectURL !== undef && window) ||
                (typeof URL !== undef && URL) ||
                (typeof webkitURL !== undef && webkitURL);
        return urlAPI ? urlAPI.revokeObjectURL(url) : false;
    },
	
    // Scales the given image (img HTML element)
    // using the given options.
    // Returns a canvas object if the canvas option is true
    // and the browser supports canvas, else the scaled image:
    scaleImage: function (img, options) {
        options = options || {};
        var canvas = document.createElement('canvas'),
            scale = Math.max(
                (options.maxWidth || img.width) / img.width,
                (options.maxHeight || img.height) / img.height
            );
        if (scale >= 1) {
            scale = Math.min(
                (options.minWidth || img.width) / img.width,
                (options.minHeight || img.height) / img.height
            );
        }
        img.width = parseInt(img.width * scale, 10);
        img.height = parseInt(img.height * scale, 10);
        if (!options.canvas || !canvas.getContext) {
            return img;
        }
        canvas.width = img.width;
        canvas.height = img.height;
        canvas.getContext('2d')
            .drawImage(img, 0, 0, img.width, img.height);
        return canvas;
    },

    // Loads a given File object via FileReader interface,
    // invokes the callback with a data url:
    loadFile: function (file, callback) {
        if (typeof FileReader !== 'undefined' &&
                FileReader.prototype.readAsDataURL) {
            var fileReader = new FileReader();
            fileReader.onload = function (e) {
                callback(e.target.result);
            };
            fileReader.readAsDataURL(file);
            return true;
        }
        return false;
    },

    hasError: function (file) {
        if (file.error) {
            return file.error;
        }
        // Files are accepted if either the file type or the file name
        // matches against the acceptFileTypes regular expression, as
        // only browsers with support for the File API report the type:
        if (!(this.options.acceptFileTypes.test(file.type) ||
                this.options.acceptFileTypes.test(file.name))) {
            return 'acceptFileTypes';
        }
        if (this.options.maxFileSize &&
                file.size > this.options.maxFileSize) {
            return 'maxFileSize';
        }
        if (typeof file.size === 'number' &&
                file.size < this.options.minFileSize) {
            return 'minFileSize';
        }
        return null;
    },

    validate: function (files) {
        var that = this,
            valid;
        $.each(files, function (index, file) {
            file.error = that.hasError(file);
            valid = !file.error;
        });
        return valid;
    }
};
