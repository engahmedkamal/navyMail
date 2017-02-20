function load_doc_imgs(doc_id, successFunction) {

	$.ajax({
		type : "GET",
		url : '/documents/load_document_imgs/' + doc_id + '.json',
		data : {},
		success : function(res) {
			var img_html = '<li> <div> <img  style="width: 138; height: 138;" src="====="> <div class="text"> <div class="inner"><span> %%%% </span> <div class="buttons-controls"><a href="#"  class="btnRemoveImg"> <i class="icon-remove red"></i> </a><a href="#edit_img_modal" role="button" data-toggle="modal" id="btnEditImage" onclick="edit_image(this)"> <i class="icon-pencil"></i> </a><a href="#edit_img_modal" role="button" data-toggle="modal" onclick="btnInsertImg(this);"> <i class="icon-reply icon-only"></i></a> </div></div> </div> </a> </div> </li>';

			for (var i = 1; i < res.length; i++) {
				$(".ace-thumbnails").append(img_html.replace(/=====/g, res[i].replace("/public", "") + "?cache=" + Math.random()).replace(/%%%%/g, res[i].replace(/^.*[\\\/]/, '')));
			};
			$('#document_img_no').val(res.length);
			if (successFunction) {
				successFunction();
			};

		},
		dataType : "json"
	});

}

function btnInsertImg(el) {
	$("#operation").val("insert");
	var img_li, img_tag;
	img_li = $(el).parent().parent().parent().parent().parent();
	img_loc = $(img_li).find("img").attr("src");
	$("#img_path").val(img_loc.replace(img_loc.substring(img_loc.indexOf("?"), img_loc.length), ""));

}


$(document).ready(function() {
//	$("#document_doc_type,#document_target_id").attr('disabled','disabled');
	
	load_doc_imgs($("#doc_id").val(), function() {
		var img_li, img_tag;

		$(".btnRemoveImg").click(function(event) {
			event.preventDefault();
			img_li = $(this).parent().parent().parent().parent().parent();
			img_loc = $(img_li).find("img").attr("src");
			img_loc = img_loc.replace(img_loc.substring(img_loc.indexOf("?"), img_loc.length), "");
			if (confirm("هل انت متاكد من المسح؟؟")) {
				$.ajax({
					type : "POST",
					url : '/documents/delete_doc_image.json',
					data : {
						doc_id : $("#doc_id").val(),
						img : img_loc
					},
					dataType : "json",
					complete : function(res) { debugger;
						if (res.responseText == "true") {
							location = "/documents/" + $("#doc_id").val() + "/edit";
						};
					},
					beforeSend : function(xhr) {
						xhr.setRequestHeader('X-CSRF-Token', $('meta[name="csrf-token"]').attr('content'));
					}
				});
			};
		});
	});
	//let's add a custom loading icon
	var docs = $("#related_topics").html();
	$("#doc_follow").html(docs);
	$("#related_topics").remove();
	$("#edited_doc_id").val($("#doc_id").val());

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
					if ((type.length > 0 && ! (/^image\/(jpe?g|png|gif|bmp)$/i).test(type) ) || (type.length == 0 && ! (/\.(jpe?g|png|gif|bmp)$/i).test(file.name) )//for android's default browser which gives an empty string for file.type
						)
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

function edit_image(li_element) {
	$("#operation").val("edit");
	var img_li, img_tag;
	img_li = $(li_element).parent().parent().parent().parent().parent();
	img_loc = $(img_li).find("img").attr("src");
	$("#img_path").val(img_loc.replace(img_loc.substring(img_loc.indexOf("?"), img_loc.length), ""));
}

