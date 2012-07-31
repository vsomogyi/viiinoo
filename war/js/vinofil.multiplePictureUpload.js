/**
 * @author PTihomir
 */
var debug = {};
$(document).ready(function () {
	
	debug.imagelist = new Imagelist($('.picture-container'));

});

var Imagelist = function (container) {
	this.container = container;
	this.options.dropZone = this.list = this.container.find('.image-list');
	
	container.find('.upload-image:not(:first)').remove();
	
	this.uploadTile = container.find('.upload-image');
	
	this.container.find('li').addClass('rich-image');
	
	this.uploadTile.find('label').hide();
	
	this.setDOMHandlers();
	
	this.counter = 1;
	
	// set default tile
	this.list
		.find('input[type=radio]:checked')
		.closest('li')
		.addClass('default');
	
	
};

Imagelist.prototype = {
		
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
        previewMaxWidth: 200,
        // The maximum height of the preview images:
        previewMaxHeight: 200,
        // By default, preview images are displayed as canvas elements
        // if supported by the browser. Set the following option to false
        // to always display preview images as img elements:
        previewAsCanvas: true,
        // The file upload template that is given as first argument to the
        // jQuery.tmpl method to render the file uploads:
        uploadTemplate: $('#template-upload'),


    },
	
	setDOMHandlers: function () {
		
		var that = this;
        this.options.dropZone
        	.bind('dragover', {imagelist: this}, this.onDragOver)
        	.bind('drop', {imagelist: this}, this.onDrop);

		this.uploadTile.delegate('input', 'change', {imagelist: this}, function (e) {
			
			// add new input to add-image Tile, the old one will be moved to other tile
			$(this).parent().append('<input type="file" name="file"/>');
			
			// validate files
			var files = that.validate(this.files);
			
			// create the new tile
			var tmpl = that.renderUpload(this.files).insertAfter($(this).closest('li'));
			
			// move tile to new tile, with detach all the informations remains
			$(this)
				.detach()
				.appendTo(tmpl.find('.image-input'));
				
			tmpl.fadeIn();
			
		});
		
		this.container.delegate('.default-select','click', function (e) {
			
			// in case this tile is already selected as default, then leave
			if($(this).closest('li').hasClass('default')) {
				return;
			}
			
			// check the radio button to become default
			$(this).find('input[type=radio]').prop('checked', true);
			
			// add class to li and remove class from others
			$(this).closest('li').addClass('default').siblings().removeClass('default');
		});
		
		this.container.delegate('.del-checkbox','click', function (e) {
			
			// read old value
			var toggleValue = $(this).find('input[type=checkbox]').prop('checked');
			// set inverted value
			$(this).find('input[type=checkbox]').prop('checked', !toggleValue);
			
			// toggle class delete
			$(this).closest('li').toggleClass('delete');
			
			// if it is default, then delete default status
			if ($(this).closest('li').hasClass('default')) {
				$(this).closest('li').removeClass('default');
			}
			
		});
		
		this.container.delegate('.cancel','click', function (e) {
			
			// just delete tile
			$(this).closest('li').fadeOut(function () {$(this).remove();});
			
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
	
    uploadTemplateHelper: function (file) {
        file.sizef = this.formatFileSize(file);
        return file;
    },

    renderUploadTemplate: function (files) {
        var that = this;
        return $.tmpl(
            that.options.uploadTemplate,
            $.map(files, function (file) {
                return that.uploadTemplateHelper(file);
            })
        );
    },

    renderUpload: function (files) {
        var that = this,
            tmpl = this.renderUploadTemplate(files);
        if (!(tmpl instanceof $)) {
            return $();
        }
        tmpl.css('display', 'none');

        tmpl.find('.cancel button').slice(1).remove().end().first()
            .button({
                text: false,
                icons: {primary: 'ui-icon-cancel'}
            });
        tmpl.find('.preview').each(function (index, node) {
        	if (files[index].error) {
        		return;
        	}
            that.loadImage(
                files[index],
                function (img) {
                    $(img).hide().appendTo(node).fadeIn();
                },
                {
                    maxWidth: that.options.previewMaxWidth,
                    maxHeight: that.options.previewMaxHeight,
                    fileTypes: that.options.previewFileTypes,
                    canvas: that.options.previewAsCanvas
                }
            );
        });
        return tmpl;
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
            }).prop('src', url);
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
                scale = Math.min(
                    (options.maxWidth || img.width) / img.width,
                    (options.maxHeight || img.height) / img.height
                );
            if (scale >= 1) {
                scale = Math.max(
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
