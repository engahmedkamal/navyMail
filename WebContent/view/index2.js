function navyAdminReady() {
	$(document).ready(function() {
		stack = [];
		//loadFile("search_form.html", "html", ".bgcontent",function() {});
		//loadFile("Travels/Home.html", "html", ".bgcontent",function() {});
		//loadFile("FastSearchForTablet/currentStatus.html", "html", "#elmawkef",function() {});
		
		/**** Code Samir and Bassiouny*******/
	   //alert((document.cookie=privilege_id))
		console.debug(privilege_id);
		$('#simple-menu').sidr();
		var opened = false;
		$('#currentStatus').on("click",function() {
			if (opened == false) {
				$("#right-btn-img").attr("src","../assets/css/images/prev.png");
				opened = true;
			} else {
				$("#right-btn-img").attr("src","../assets/css/images/next.png");
				opened = false;
			}
		});
		$("#simple-menu").on("click", function() {
			write_cookie("login_id", "");
			write_cookie("login_name", "");
			window.location.assign("index.html");
		});
	});
};

//function logout(){
//	write_cookie("login_id", "");
//	write_cookie("login_name", "");
//	window.location.assign("index.html");
//}
function open_wared_form(){
	stack = [];
	window.location.assign("home.html");
	}

function open_sader_form(){
	if(privilege_id==1||privilege_id==3||privilege_id==4){
	stack = [];
	loadFile("Sader/sader_search_form.html", "html", ".bgcontent", function() {});
	}
	else
		alert("غير مسموح لك بالدخول الى هذه المنظومة");
}

function ahmed(){
	stack = [];
	loadFile("test.html", "html", ".bgcontent", function() {});
}

function open_create_sader_form(){
	if(privilege_id==1||privilege_id==3||privilege_id==5){
	stack = [];
	loadFile("Sader/NewSader.html", "html", ".bgcontent", function() {});
	}else
		alert("غير مسموح لك بالدخول الى هذه المنظومة");
}
function GoMissionsForm()
{if(privilege_id==1){
	stack = [];
	loadFile("Travels/Home.html", "html", ".bgcontent",function() {});
}else
	alert("غير مسموح لك بالدخول الى هذه المنظومة");
}
function Print_Form()
{if(privilege_id==1||privilege_id==3||privilege_id==4){
	stack = [];
	loadFile("Motba/Motb3a.html", "html", ".bgcontent",function() {});
}else
	alert("غير مسموح لك بالدخول الى هذه المنظومة");
}
function Print_Form_units()
{if(privilege_id==1||privilege_id==3||privilege_id==4){
	stack = [];
	loadFile("Motba/unitreport.html", "html", ".bgcontent",function() {});
}else
	alert("غير مسموح لك بالدخول الى هذه المنظومة");
}
function Motb3a_Search_Form()
{if(privilege_id==1||privilege_id==3||privilege_id==4){
	stack = [];
	loadFile("Motba/search_form.html", "html", ".bgcontent",function() {});
}else
	alert("غير مسموح لك بالدخول الى هذه المنظومة");
}