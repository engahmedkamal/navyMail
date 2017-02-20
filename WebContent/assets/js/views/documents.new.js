function load_doc_imgs(suc) {

	$.ajax({
		type : "GET",
		url : '/documents/load_imgs_from_dir/' + $("#folder").val() + '.json',
		data : {},
		success : function(res) {
			var img_html = '<li> <a href="=====" data-lightbox="documnet" class="cboxElement"> <img  style="width: 138; height: 138;" src="====="> <div class="text"> <div class="inner"> %%%% </div> </div> </a> </li>';

			$(".ace-thumbnails li").remove();

			for (var i = 0; i < res.length; i++) {
				$(".ace-thumbnails").append(img_html.replace(/=====/g, res[i] + "?cache=" + Math.random()).replace(/%%%%/g, res[i].replace(/^.*[\\\/]/, '')));
			};
			$('#document_img_no').val(res.length);
			if (suc) {
				suc();
			};
		},
		dataType : "json"
	});

}

function refresh_scanar_imgs() {
	var take_count = parseInt($('#document_img_no').val());
	var img_count = $(".ace-thumbnails li").length;
	if (take_count > img_count) {
		take_count = img_count;
		$('#document_img_no').val(take_count);
	} else if (take_count < 1) {
		take_count = 1;
		$('#document_img_no').val(take_count);
	}

	for (var i = 0; i < img_count; i++) {
		if (i > take_count - 1)
			$(".ace-thumbnails li:eq(" + i + ")").hide();
		else
			$(".ace-thumbnails li:eq(" + i + ")").show();
	};
}


$(document).ready(function() {
	if ($.cookie('scanner')) {
		$("#folder").val($.cookie('scanner'));
	}
	
	$(".nav-list:eq(0) .submenu>li:eq(0)").addClass("active");
	$("#document_status_id").val(1);
	$(".doc_save_number").next().remove();
	$(".doc_save_number").remove();
	$("#btnRefreshFolder").click(function() {
		load_doc_imgs();
		return false;
	});

	load_doc_imgs(function() {

		$('#document_img_no').on('change', function() {
			refresh_scanar_imgs();
		});

	});
	$("#folder").change(function(e) {
		e.preventDefault();
		load_doc_imgs();
		$.cookie('scanner', $(this).val(), {
			expires : 60,
			path : '/'
		});
	});

});
