function save_photos(){
  	
  var id = $("#edited_doc_id").val();
  var img = $("#img_path").val();
  var file = $("#img_file").get(0).files[0];

  var operation =  0;
  // 1 means Insert , 2 means edit
  if($("#operation").val()=="insert")
	  operation=1;
  else
	  operation=2;
  
  var title = documentSader_details.doc_number + ' ' + documentSader_details.doc_date;

  var formData = new FormData();
  formData.append('uploadfile', file);
  formData.append('id', id);
  formData.append('operation', operation);
  formData.append('img_path', img);
  formData.append('title', title);

  $.ajax({
  url: "/rest/image/editSader",
  type: 'POST',
  xhr: function() {  // Custom XMLHttpRequest
    var myXhr = $.ajaxSettings.xhr();
    return myXhr;
  },
  // beforeSend: beforeSendHandler,
  success: function(data) {
		documentSader_details = data[0];
		loadFile("Sader/editSader.html", "html", ".bgcontent", function() {});
  },
  // Form data
  data: formData,
  //Options to tell jQuery not to process data or worry about content-type.
  cache: false,
  contentType: false,
  processData: false
  });


}

function edit_img_panel(li_element){
	$("#operation").val("edit");
	var img_li, img_tag;
	img_li = $(li_element).parent().parent().parent().parent().parent();
	img_loc = $(img_li).find("img").attr("src");
	$("#img_path").val(img_loc.replace(img_loc.substring(img_loc.indexOf("?"), img_loc.length), ""));
}

function btnInsertImg(el) {
	$("#operation").val("insert");
	var img_li, img_tag;
	img_li = $(el).parent().parent().parent().parent().parent();
	img_loc = $(img_li).find("img").attr("src");
    $("#img_path").val(img_loc.replace(img_loc.substring(img_loc.indexOf("?"), img_loc.length), ""));
}


$(document).ready(function() {
	$("#edited_doc_id").val(documentSader_details.id);
	
	$('#edit_img_modal input[type=file]').ace_file_input({
		style : 'well',
		btn_choose : ' اضغط لتحديد الصورة..',
		btn_change : null,
		no_icon : 'icon-cloud-upload',
		droppable : true,
		thumbnail : 'large',
		before_change : function(files, dropped) {
			var allowed_files = [];
			for (var i = 0; i < files.length; i++) {
				var file = files[i];
				if ( typeof file === "string") {
					//IE8 and browsers that don't support File Object
					if (! (/\.(jpe?g|png|gif|bmp)$/i).test(file))
						return false;
				} else {
					var type = $.trim(file.type);
					if ((type.length > 0 && ! (/^image\/(jpe?g|png|gif|bmp)$/i).test(type) ) || (type.length == 0 && ! (/\.(jpe?g|png|gif|bmp)$/i).test(file.name) ))//for android's default browser which gives an empty string for file.type
						continue;
					//not an image so don't keep this file
				}
				allowed_files.push(file);
			}
			if (allowed_files.length == 0)
				return false;
			return allowed_files;
		}
	});
});

