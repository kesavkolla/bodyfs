/**
 * @author kesav
 */
(function($){
	$.widget("ui.annotateImage", {
		_init: function(){
			var self = this;
			this.mode = 'view';
			this.notes = new Array();
			
			// Add the canvas
			this.canvas = $('<div class="image-annotate-canvas"><div class="image-annotate-view"></div><div class="image-annotate-edit"><div class="image-annotate-edit-area"></div></div></div>');
			this.canvas.children('.image-annotate-edit').hide();
			// this.canvas.children('.image-annotate-view').hide();
			this.element.after(this.canvas);
			
			// Give the canvas and the container their size and background
			var imgheight = this.element.height();
			var imgwidth = this.element.width();
			this.canvas.css({
				'height': imgheight,
				'width': imgwidth,
				'background-image': 'url("' + this.element.attr('src') + '")'
			});
			this.canvas.children('.image-annotate-view, .image-annotate-edit').css({
				'height': imgheight,
				'width': imgwidth
			});
			
			// Add the behavior: hide/show the notes when hovering the picture
			/*
			 * this.canvas.hover(function(){ if ($(this).children('.image-annotate-edit').css('display') == 'none') {
			 * $(this).children('.image-annotate-view').show(); } }, function(){
			 * $(this).children('.image-annotate-view').hide(); });
			 * 
			 * this.canvas.children('.image-annotate-view').hover(function(){ $(this).show(); }, function(){
			 * $(this).hide(); });
			 */
			
			// load the notes
			if (this.useAjax) {
				this._ajaxLoad();
			}
			else {
				this.load(this.options.notes);
			}
			
			// Add the "Add a note" button
			if (this.options.editable) {
				this.button = $('<a class="image-annotate-add" id="image-annotate-add" href="#">Add Note</a>');
				this.button.click(function(){
					self._add();
				});
				this.canvas.after(this.button);
			}
			
			// Hide the original
			this.element.hide();
		},
		/**
		 * Loads the annotations from the "getUrl" property passed in on the options object
		 */
		_ajaxLoad: function(){
			var self = this;
			$.getJSON(image.getUrl + '?ticks=' + self._getTicks(), function(data){
				self.options.notes = data;
				self._load();
			});
		},
		/**
		 * Gets a count og the ticks for the current date. This is used to ensure that URLs are always unique and not
		 * cached by the browser.
		 */
		_getTicks: function(){
			var now = new Date();
			return now.getTime();
		},
		/**
		 * Loads the annotations from the notes property passed in on the options object.
		 */
		load: function(notes){
			if (!$.isArray(notes) || notes.length <=0) {
				return;
			}
			for (var i = 0, len = notes.length; i < len; i++) {
				var note = notes[i];
				this._annotateView(note);
				this.notes[this.notes.length] = note;
			}
		},
		/**
		 * Adds a note to the image.
		 */
		_add: function(){
			if (this.mode != 'view') {
				return;
			}
			this.mode = "edit";
			
			var newNote = new Object();
			newNote.id = Math.uuid();
			newNote.top = 30;
			newNote.left = 30;
			newNote.width = 30;
			newNote.height = 30;
			newNote.text = "";
			
			// Create/prepare the editable note elements
			this._annotateEdit(newNote, false);
		},
		/**
		 * Defines an editable annotation area.
		 */
		_annotateEdit: function(note, showdelete){
			var self = this;
			// Set area
			var area = self.canvas.children('.image-annotate-edit').children('.image-annotate-edit-area');
			area.css({
				'height': note.height + 'px',
				'width': note.width + 'px',
				'left': note.left + 'px',
				'top': note.top + 'px'
			});
			
			// Show the edition canvas and hide the view canvas
			self.canvas.children('.image-annotate-view').hide();
			self.canvas.children('.image-annotate-edit').show();
			
			// Add the note (which we'll load with the form afterwards)
			var form = $('<div id="image-annotate-edit-form"><form><textarea id="image-annotate-text" name="text" rows="3" cols="30">' + note.text + '</textarea></form></div>');
			
			$('body').append(form);
			form.css({
				'left': area.offset().left + 'px',
				'top': (parseInt(area.offset().top) + parseInt(area.height()) + 7) + 'px'
			});
			
			// Set the area as a draggable/resizable element contained in the image canvas.
			// Would be better to use the containment option for resizable but buggy
			area.resizable({
				handles: 'all',
				
				stop: function(e, ui){
					form.css({
						'left': area.offset().left + 'px',
						'top': (parseInt(area.offset().top) + parseInt(area.height()) + 2) + 'px'
					});
				}
			}).draggable({
				containment: self.canvas,
				drag: function(e, ui){
					form.css({
						'left': area.offset().left + 'px',
						'top': (parseInt(area.offset().top) + parseInt(area.height()) + 2) + 'px'
					});
				},
				stop: function(e, ui){
					form.css({
						'left': area.offset().left + 'px',
						'top': (parseInt(area.offset().top) + parseInt(area.height()) + 2) + 'px'
					});
				}
			});
			
			var btnok = $('<a class="image-annotate-edit-ok">OK</a>');
			btnok.click(function(evt){
				return self._handleok.call(self, evt, this, note);
			});
			form.append(btnok);
			
			if (showdelete) {
				var btndel = $('<a class="image-annotate-edit-delete">Delete</a>');
				btndel.click(function(evt){
					return self._handledelete.call(self, evt, this, note);
				});
				form.append(btndel);
			}
			
			var btncancel = $('<a class="image-annotate-edit-close">Cancel</a>');
			btncancel.click(function(evt){
				return self._handlecancel.call(self, evt, this);
			});
			form.append(btncancel);
		},
		/**
		 * Handle click on ok button
		 */
		_handleok: function(event, target, note){
			var form = $('#image-annotate-edit-form form');
			var text = $('#image-annotate-text').val();
			this.mode = "view";
			
			// Save via AJAX
			if (this.options.useAjax) {
				this._appendPosition(form, note);
				$.ajax({
					url: this.options.saveUrl,
					data: form.serialize(),
					error: function(e){
						alert("An error occured saving that note.")
					},
					success: function(data){
						if (data.annotation_id != undefined) {
							note.id = data.annotation_id;
						}
					},
					dataType: "json"
				});
			}
			var area = this.canvas.children('.image-annotate-edit').children('.image-annotate-edit-area');
			var areapos = area.position();
			note.left = areapos.left;
			note.top = areapos.top;
			note.width = area.width();
			note.height = area.height();
			note.editable = true;
			note.text = text;
			if ($("#" + note.id).length == 0) {
				this._annotateView(note);
				this.notes[this.notes.length] = note;
			}
			else {
				this._resetPosition(note);
			}
			
			this._destroy();
			return true;
		},
		
		/**
		 * Handles the cancel button
		 * 
		 * @param {Object} event
		 * @param {Object} target
		 */
		_handlecancel: function(event, target){
			this._destroy();
			this.mode = "view";
			return true;
		},
		
		/**
		 * Handle click on delete button
		 * 
		 * @param {Object} event
		 * @param {Object} target
		 * @param {Object} note
		 */
		_handledelete: function(event, target, note){
			var form = $('#image-annotate-edit-form form');
			if (this.options.useAjax && this.options.deleteUrl.length > 0) {
				this._appendPosition(form, note);
				$.ajax({
					url: this.options.deleteUrl,
					data: form.serialize(),
					error: function(e){
						alert("An error occured deleting that note.")
					}
				});
			}
			
			this.mode = 'view';
			this._destroy();
			this._destroynote(note);
		},
		
		/**
		 * Defines a annotation area.
		 * 
		 * @param {Object} note
		 */
		_annotateView: function(note){
			var self = this;
			var editable = note.editable && this.options.editable;
			
			// Add the area
			var area = $('<div class="image-annotate-area' + (editable ? ' image-annotate-area-editable' : '') + '" id="area-' + note.id + '"><div></div></div>');
			this.canvas.children('.image-annotate-view').prepend(area);
			
			// Add the note
			var form = $('<div class="image-annotate-note" id="' + note.id + '">' + note.text + '</div>');
			form.hide();
			this.canvas.children('.image-annotate-view').append(form);
			form.children('span.actions').hide();
			
			// Sets the position of an annotation.
			area.children('div').css({
				'height': (parseInt(note.height) - 2) + 'px',
				'width': (parseInt(note.width) - 2) + 'px'
			});
			
			area.css({
				'left': (note.left) + 'px',
				'top': (note.top) + 'px'
			});
			form.css({
				'left': (note.left) + 'px',
				'top': (parseInt(note.top) + parseInt(note.height) + 7) + 'px'
			});
			
			// Add the behavior: hide/display the note when hovering the area
			area.hover(function(){
				form.fadeIn(250);
				if (!editable) {
					area.addClass('image-annotate-area-hover');
				}
				else {
					area.addClass('image-annotate-area-editable-hover');
				}
			}, function(){
				form.fadeOut(250);
				area.removeClass('image-annotate-area-hover');
				area.removeClass('image-annotate-area-editable-hover');
			});
			
			// Edit a note feature
			if (editable) {
				area.click(function(evt){
					return self._edit.call(self, evt, this);
				});
			}
		},
		/**
		 * Edits the annotation.
		 */
		_edit: function(evt, target){
			if (this.mode != 'view') {
				return;
			}
			this.mode = "edit";
			var noteid = $(target).attr("id").substring(5);
			var note = null;
			for (var i = 0, len = this.notes.length; i < len; i++) {
				if (this.notes[i].id == noteid) {
					note = this.notes[i];
					break;
				}
			}
			this._annotateEdit(note, true);
		},
		
		/**
		 * Sets the position of an annotation.
		 * 
		 * @param {Object} note
		 */
		_resetPosition: function(note){
			var area = $("#area-" + note.id);
			var form = $("#" + note.id);
			form.html(note.text);
			form.hide();
			// Resize
			area.children('div').css({
				'height': note.height + 'px',
				'width': note.width + 'px'
			});
			
			area.css({
				'left': (note.left) + 'px',
				'top': (note.top) + 'px'
			});
			form.css({
				'left': (note.left) + 'px',
				'top': (parseInt(note.top) + parseInt(note.height) + 7) + 'px'
			});
		},
		/**
		 * Appends the annotations coordinates to the given form that is posted to the server.
		 */
		_appendPosition: function(form, note){
			var fields = new Array();
			fields.push('<input type="hidden" value="' + note.height + '" name="height"/>');
			fields.push('<input type="hidden" value="' + note.width + '" name="width"/>');
			fields.push('<input type="hidden" value="' + note.top + '" name="top"/>');
			fields.push('<input type="hidden" value="' + note.left + '" name="left"/>');
			fields.push('<input type="hidden" value="' + note.id + '" name="id"/>');
			form.append(fields.join(""));
		},
		
		/**
		 * Destroys all the editable stuff
		 */
		_destroy: function(){
			var area = this.canvas.children('.image-annotate-edit').children('.image-annotate-edit-area');
			var form = $("#image-annotate-edit-form");
			area.resizable('destroy');
			area.draggable('destroy');
			area.removeAttr("style");
			form.remove();
			this.canvas.children('.image-annotate-edit').hide();
			this.canvas.children('.image-annotate-note').hide();
			this.canvas.children('.image-annotate-view').show();
		},
		/**
		 * Destroys the given note
		 * 
		 * @param {Object} note
		 */
		_destroynote: function(note){
			$("#" + note.id).remove();
			$("#area-" + note.id).remove();
			var indx = -1;
			for (var i = 0, len = this.notes.length; i < len; i++) {
				if (this.notes[i].id == note.id) {
					indx = i;
					break;
				}
			}
			if (indx != -1) {
				this.notes.splice(indx, 1);
			}
		},
		/**
		 * Retruns the array of note objects
		 */
		getNotes: function(){
			return this.notes;
		},
		/**
		 * Clears all the notes
		 */
		clear: function(){
			if (this.notes.length <=0) {
				return;
			}
			do {
				this._destroynote(this.notes[0]);
			}
			while (this.notes.length > 0);
			this._destroy();
		}
	});
	
	/**
	 * Plugin Defaults
	 */
	$.extend($.ui.annotateImage, {
		getter: "getNotes",
		defaults: {
			getUrl: '',
			saveUrl: '',
			deleteUrl: '',
			editable: true,
			useAjax: false,
			notes: new Array()
		}
	});
	
})(jQuery);
