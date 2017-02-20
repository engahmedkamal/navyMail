function navyAdminReady(){

	$(document).ready(function() {
		$('.banner-slider').bjqs({
            height      : 262,
            width       : 1000,
            responsive  : true,
            nexttext    :  "",
            prevtext    :  ""
          });
          
	    $('#simple-menu').sidr();
	    var opened = false;
	    $('#simple-menu').on("click", function(){
		    if(opened == false){
			    $("#left-btn-img").attr("src", "../assets/css/images/prev.png");
			    opened = true;
		    }else{
			    $("#left-btn-img").attr("src", "../assets/css/images/next.png");
			    opened = false;
		    }
	    });
		superConnect(api_source + 'groups/show', {}, function(obj) {
			options = '<option value="">-- اختر مجموعة --</option>';
			$.each(obj, function(key, value) {
				options += '<option value="' + value.id + '">' + value.name + '</option>';
			});
			$('#group').html(options);
			$("#group").on("change", function() {
				user_options =  '<option value="">-- اختر مستخدم --</option>';
				superConnect(api_source + 'users/show/' , {group_id : $("#group").val()}, function(users_obj) {
					$.each(users_obj, function(key, value) {
						user_options += '<option value="' + value.id + '">' + value.username + '</option>';
					});
					$('#username').html(user_options);
					$('#login-btn').removeAttr('disabled');
				    var frm = $('#login-form');
				    frm.submit(function (ev) {
				    	superConnect(api_source + 'users/login/' , {userid : $("#username").val(), password : $("#password").val()}, function(login_obj) {
							if(login_obj.error && login_obj.error == 1){
								alert("كلمة مرور خاطئة");
							}else{
								write_cookie("login_id", login_obj[0].id);
								write_cookie("login_name", login_obj[0].username);
								write_cookie("privilege_id", login_obj[0].privilege_id); // Code Fathy
								window.location.assign("index2.html");
							}
						 });
				        ev.preventDefault();
				    });
				});
			});
		});
	});
};
