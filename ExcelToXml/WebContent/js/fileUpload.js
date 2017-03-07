//=========================文件上传=======================================
function FileUpload(){
	var obj = this;
	var swfu = null;
	
	this.init = function(settings){
		var settings_object = {
				flash_url : baseUrl+"/commons/js/swfupload/swfupload.swf",	
			    upload_url: settings.upload_url,
				file_post_name: "uploadFile",
				post_params :{
					"savePath":settings.savePath?settings.savePath:""
				},

		            			
				file_size_limit : settings.file_size_limit?settings.file_size_limit:"10 MB",	//10MB
				file_types : settings.file_types?settings.file_types:"*",					//*.xls
				file_types_description : settings.file_types_description?settings.file_types_description:"",	//
				file_upload_limit : 100,
				file_queue_limit : 1,
				custom_settings : {
					progressTarget : settings.progressTarget?settings.progressTarget:null,
					upload_success_callback:settings.upload_success_callback?settings.upload_success_callback:null
				},
				
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : settings.queue_complete_handler?settings.queue_complete_handler:queueComplete,	// Queue plugin event

//				file_queued_handler : this.startUpload,//
//				upload_error_handler : settings.upload_error_handler?settings.upload_error_handler:function(){},//指定上传异常处理事件
//				upload_success_handler : settings.upload_success_handler?settings.upload_success_handler:function(){},//指定上传成功事件
			
				button_image_url : baseUrl+"/commons/images/browser.gif",
				button_placeholder_id : settings.button_placeholder_id?settings.button_placeholder_id:"",//根据ID绑定浏览按钮及事件
				button_width: 69,
				button_height: 21,
				
				debug: false
			};
			
			swfu=new SWFUpload(settings_object);
	};
	//alert(settings.button_placeholder_id + "    " + contextPath);
	
	this.startUpload=function()
	{
		swfu.startUpload();
	};
	this.cancelQueue = function(){
		swfu.cancelQueue();
	};
	
}
function fileQueued(file)
{
	
}