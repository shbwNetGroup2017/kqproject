(function(_window, $) {
	var BootstrapDialog = function(option) {
		var defaultOption = {
			title : "警告",
			content : undefined,
			onclose : undefined,
			isConfirm : false,
			onOk : undefined,
			textAlign:"center",
			onCancel : undefined,
			extClass:"",
			ok : "确认",
			cancel : "取消",
			dragable:true,
			mode : true
		};
		this.option = this._extendOption(defaultOption, option);
		this.id = this._generateId();
	};
	_window.BootstrapDialog = BootstrapDialog;

	BootstrapDialog.prototype._extendOption = function(defaultOption, option) {
		for ( var pro in option) {
			if (option.hasOwnProperty(pro)) {
				defaultOption[pro] = option[pro];
			}
		}
		return defaultOption;
	};
	BootstrapDialog.prototype.close = function() {
		var $dialog = $("#" + this.id + "_dialog ");
		var $mask = $("#" + this.id + "_mask");
		if (this.option.onclose) {
			this.option.onclose(this.id);
		}
		$dialog.animate({
			top : 0,
			opacity : 0
		}, 'slow', 'linear', function() {
			$dialog.remove();
			$mask.remove();
		});
	}

	BootstrapDialog.prototype._getCurrentDialogposition = function() {
		var $dialog = $("#" + this.id + "_dialog ");
		return $dialog.offset();
	}

	BootstrapDialog.prototype._getCurrentMouseposition = function(event) {
		var e = event || window.event;
		return {
			left : e.clientX,
			top : e.clientY
		};
	}

	BootstrapDialog.prototype._resetDialogPosition=function(event){
		var $dialog = $("#" + this.id + "_dialog ");
		var me=this;
		var currentMousePosition = me._getCurrentMouseposition(event);
		$dialog.offset({
			left : (parseInt(currentMousePosition.left)
					+ parseInt(me.mousedownDialogPosition.left) - parseInt(me.mousedownPosition.left)),
			top : (parseInt(currentMousePosition.top)
					+ parseInt(me.mousedownDialogPosition.top) - parseInt(me.mousedownPosition.top))
		});
	}
	BootstrapDialog.prototype._drag=function(){
		var $dialog = $("#" + this.id + "_dialog ");

		var me = this;
		$dialog.find(".panel-heading").bind("mousedown", function(event) {
			me.isMouseDown = true;
			me.mousedownPosition = me._getCurrentMouseposition(event);
			me.mousedownDialogPosition = me._getCurrentDialogposition();
		});
		$dialog.find(".panel-heading").bind("mousemove",function(event) {
							if (me.isMouseDown) {
								me._resetDialogPosition(event);
							}
			$dialog.find(".panel-heading").css({
				cursor : 'move'
			});
						});
		$dialog.find(".panel-heading").bind( "mouseup",function(event) {
					if (me.isMouseDown) {
						me._resetDialogPosition(event);
						me.isMouseDown=false;
						$dialog.find(".panel-heading").css({
						cursor : 'default'
						});
					}
				});
	}
	BootstrapDialog.prototype._bindEvent = function() {
		var $dialog = $("#" + this.id + "_dialog ");

		var me = this;
		$dialog.find(".glyphicon-remove").bind("click", function() {
			me.close();
		});
	
		if(me.option.dragable){
			me._drag();
		}
		$dialog.find(".dialog-submit").bind("click", function() {
			if (me.option.onOk) {
				me.option.onOk(me.id);
			}
			if (!me.option.isConfirm) {
				me.close();
			}
		});
		$dialog.find(".btn-warning").bind("click", function() {
			if (me.option.onCancel) {
				me.option.onCancel(me.id);
			}
			me.close();
		});
	}

	BootstrapDialog.prototype.open = function() {
		var body = $("body");
		if (this.option.mode) {
			this._generateMask(this.id);
		}
		var $this = this;
		body.append(this._generateDialog(this.id));
		var $dialog = $("#" + this.id + "_dialog");
		$dialog.css({
			zIndex : 200
		});
		var position = this._generatePosition();
		$dialog.offset({
			left : position.left
		});
		$dialog.animate({
			top : position.top,
			opacity : 1
		}, 'slow', 'linear',function(){
			$this._reSizeMask($this.id);
		});
		this.dialogDom = $dialog;
		this._bindEvent();
		
	};
	BootstrapDialog.prototype.rePosition = function() {
        var position = this._generatePosition();
        var $dialog = $("#" + this.id + "_dialog");
        $dialog.animate({
            top : position.top,
            left:position.left,
            opacity : 1
        }, 'slow', 'linear');
    };
	BootstrapDialog.prototype._generatePosition = function() {
		var $dialog = $("#" + this.id + "_dialog");
		var bodySize = this._getCurrentVistableSize();
		var width = $dialog.width();
		var height = $dialog.height();  
		var top =(window.document.body.clientHeight/2- height / 2)+window.document.body.scrollTop;
		var left = (window.document.body.clientWidth / 2 - width / 2);
		if(top<50){
			top=50;
		}
		return {
			top : top,
			left : left
		};
	};
	BootstrapDialog.prototype.getDialogDom=function(){
		return this.dialogDom;
	}
	BootstrapDialog.prototype._generateDialog = function(id) {
		var dialogHtml = '<div  style="top:0px;position:absolute;opacity:0;" id="'
				+ id + '_dialog"><div class="panel panel-primary '+this.option.extClass+'">';
		dialogHtml += ' <div class="panel-heading" >'
				+ this.option.title
				+ '<span class="glyphicon glyphicon-remove" style="float: right;cursor: pointer;"></span></div>';
		dialogHtml += ' <div class="panel-body" style="min-width: 250px;text-align: '+this.option.textAlign+';">';
		dialogHtml += this.option.content;
		dialogHtml += ' </div>';
		if (this.option.isConfirm) {
			dialogHtml += ' <div class="panel-footer"> <div class="row"><div class="col-md-6"><button type="button" class="btn btn-primary dialog-submit" style="float:right;"><span class="glyphicon glyphicon-ok"></span> '
					+ this.option.ok
					+ '</button></div><div class="col-md-6"> <button type="button" class="btn btn-warning "> <span class="glyphicon glyphicon-remove"></span>'
					+ this.option.cancel + ' </button></div></div></div>'
		} else {
			dialogHtml += ' <div class="panel-footer"> <div class="row"><div class="col-md-12" style="text-align: center;"><button type="button" class="btn btn-primary dialog-submit"><span class="glyphicon glyphicon-ok"></span> '
					+ this.option.ok + '</button></div></div></div>'
		}
		dialogHtml += '</div>';
		return dialogHtml;
	}
	BootstrapDialog.prototype._reSizeMask=function(id){
		var bodySize = this._getCurrentPageSize();
		$("#"+id+"_mask").css({height:bodySize.height});
	}
	BootstrapDialog.prototype._generateMask = function(id) {
		var body = $("body");
		var bodySize = this._getCurrentPageSize();
		var maskHtml = '<div id="'
				+ id
				+ '_mask" style="width:'
				+ bodySize.width
				+ 'px;height:'
				+ bodySize.height
				+ 'px;opacity:0.2;filter:alpha(opacity=20);background-color:#333;z-index:199;top:0;left:0;position:absolute;"></div>';
		body.append(maskHtml);
	};
	BootstrapDialog.prototype._generateId = function() {
		return Math.uuid();
	};
	BootstrapDialog.prototype._getCurrentVistableSize = function() {
		return {
			width : window.document.body.clientWidth,
			height : window.document.body.clientHeight
		};
	};
	BootstrapDialog.prototype._getCurrentPageSize = function() {
		return {
			width : window.document.body.scrollWidth,
			height : window.document.body.scrollHeight
		};
	};
})(window, $);