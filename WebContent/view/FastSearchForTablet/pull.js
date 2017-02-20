function pull_kaed() {
	var r = confirm("سيتم سحب مكاتبات السيد القائد");
	if(r){
		superConnect(api_source + 'tablet/kaed', {}, function(obj) {
			$("#da5ly").html(obj.x);
			$("#khargy").html(obj.y);
			var e = document.getElementById("status");
			e.style.display = 'block';
			setStatus();
		});
	}
}

function pull_arkan() {
	var r = confirm("سيتم سحب مكاتبات السيد رئيس الأركان");
	if(r){
		superConnect(api_source + 'tablet/arkan', {}, function(obj) {
			$("#da5ly").html(obj.x);
			$("#khargy").html(obj.y);
			var e = document.getElementById("status");
			e.style.display = 'block';
			setStatus();
		});
	}
}

$(document).ready(function() {
		
});