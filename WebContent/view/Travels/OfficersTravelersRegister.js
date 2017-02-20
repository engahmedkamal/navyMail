function print_travelers_report() {
	var report_params = {};
	report_params.mission_id = current_mission_id;
	report_params.type = 1;
	report_params.ship_id = GlobalShipEntryID;
	report_params.mission_type = (ma2morya_type == 1) ? (2) : (1);

	superConnect(api_source + 'Report/Print', report_params, function(obj) {
		var win = window.open(
				'http://' + host_name + ':3000/OfficerReport.pdf', '_blank');
		win.focus();
	});
}
function print_travelers_report_units() {
	var report_params = {};
	report_params.mission_id = current_mission_id;
	report_params.type = 1;
	report_params.ship_id = GlobalShipEntryID;
	report_params.mission_type = (ma2morya_type == 1) ? (2) : (1);

	superConnect(api_source + 'ReportUnits/Print', report_params,
			function(obj) {
				var win = window.open('http://' + host_name
						+ ':3000/OfficerReport.pdf', '_blank');
				win.focus();
			});
}
function goBack() {
	if (Traveler_Back_Button == 2)
		loadFile("Travels/Details.html", "html", ".bgcontent", function() {
		});
	else
		loadFile("Travels/TravelersRegisterForms.html", "html", ".bgcontent",
				function() {
				});
}

function Push_Passengers_Array() {
	passenger_position_options =  '<option value ='+1+'>'+ 'أساسى' + '</option>'+'<option value ='+2+'>'+ 'إحتياطى' + '</option>' ;
	passenger_security_options =   '<option value ='+1+'>'+ 'لا يوجد' + '</option>' +'<option value ='+2+'>'+ 'يوجد' + '</option>';
	// var passenger_position = "<input id='traveler_position'
	// class='form-control'>";
	var military_mumber = "<input id='m_num' type='number' autofocus class='form-control'>";
	var rank_select = "<select id='traveler_rank' class='form-control'>"
			+ passengers_ranks + "</select>";
	var passenger_name = "<input id='traveler_name' class='form-control'>";
	var aqdamya_number = "<input id='traveler_aqdamya_num' class='form-control'>";
	var passenger_unit = "<select id='traveler_unit' class='form-control'>"
			+ passengers_units + "</select>";
	var passenger_position = "<select id='traveler_position' class='form-control'>"
			+ passenger_position_options + "</select>";
	var notes = "<input class='form-control' id='traveler_add_notes' >";
	// var security_status = "<input id='traveler_security_status'
	// type='checkbox'>";
	var security_status = "<select id='traveler_security_status' class='form-control'>"
			+ passenger_security_options + "</select>";

	Passengers_Number += 1;

	Passengers_Array.push([ , , military_mumber, rank_select, passenger_name,
			aqdamya_number, passenger_unit, passenger_position,
			security_status, notes ]);

	ViewPassengersTable();
}

function ViewPassengersTable() {
	var index = 1;
	var NewPassenger = "";
	var position_tag = "";
	for (var row = 0; row < Passengers_Number; row++) {
		if (row != Passengers_Number - 1) {
			if (Passengers_Array[row][7] == 1) {
				position_tag = "أساسى";
			} else if ((Passengers_Array[row][7] == 2)) {
				position_tag = "إحتياطى";
			}
		} else {
			position_tag = Passengers_Array[row][7];
		}
		var remove_tag = (row != Passengers_Number - 1) ? '<a target="_blank" href="javascript:void(0)" class="btn btn-danger btn-sm" onClick=removetraveler('
				+ row + ')><i class="icon-remove red"></i></a>'
				: "";
		var rank_name_tag = (row != Passengers_Number - 1) ? rank_name[Passengers_Array[row][3]]
				: Passengers_Array[row][3];
		var unit_name_tag = (row != Passengers_Number - 1) ? internal_units_name[Passengers_Array[row][6]]
				: Passengers_Array[row][6];
		var sec_status_tag = "";

		if ((row != Passengers_Number - 1)) {
			if (Passengers_Array[row][8] == 1)
				sec_status_tag = "لا يوجد";
			else
				sec_status_tag = "يوجد";
		} else
			sec_status_tag = Passengers_Array[row][8];

		/*
		 * if (Passengers_Array[row][8] == 1) new_row_tag = "<tr style='font-size:16px;color:black;text-align:center;background:green;'>" ;
		 * else new_row_tag = "<tr style='font-size:16px;color:black;text-align:center;background:red;'>" ;
		 */

		NewPassenger += "<tr style='font-size:15px;color:black;text-align:center;'>"
				+ "<td>"
				+ remove_tag
				+ "</td>"
				+ "<td>"
				+ index++
				+ "</td>"
				+ "<td >"
				+ Passengers_Array[row][2]
				+ "</td>"
				+ "<td style='white-space: nowrap;text-align: center;'>"
				+ rank_name_tag
				+ "</td>"
				+ "<td style='white-space: nowrap;text-align: center;'>"
				+ Passengers_Array[row][4]
				+ "</td>"
				+ "<td>"
				+ Passengers_Array[row][5]
				+ "</td>"
				+ "<td style='white-space: nowrap;text-align: center;'>"
				+ unit_name_tag
				+ "</td>"
				+ "<td>"
				+ position_tag
				+ "</td>"
				+ "<td>" + sec_status_tag + "</td>" + "<td>"
				// if (Passengers_Array[row][9] != 'null')
				+ Passengers_Array[row][9]
				// else
				// +''
				+ "</td>" + "</tr>";

	}

	$("#Passengers_Body").html(NewPassenger);
	$("#m_num")
			.bind(
					'keypress',
					function(e) {
						var index = military_num_array.indexOf($("#m_num")
								.val()); //
						if (e.keyCode == 13) {
							if (index != -1) {
								alert("هذا الرقم العسكرى تم تسجيله من قبل");
								return;
							}
							var rankid = $("#traveler_rank").find(
									"option:selected").attr('value');
							var unitid = $("#traveler_unit").find(
									"option:selected").attr('value');
							if (rankid == -1 || unitid == -1) {
								alert("تأكد من إدخال جميع البيانات");
								return;
							}
							if ($("#traveler_add_notes").val().indexOf('/') != -1
									|| $("#traveler_add_notes").val().indexOf(
											'0') != -1) {
								alert("عنوان المكاتبة لا يجب أن يحتوى على العلامات / أو . ");
								return;
							}
							military_num_array.push($("#m_num").val());
							var traveler_sec_status = $(
									"#traveler_security_status").find(
									"option:selected").attr('value');
							console.debug(">>>>>>>>>>>>>>>>>>"
									+ traveler_sec_status)
							/*
							 * if ($("#traveler_security_status").val()=='on')
							 * sec_status = 0 ; else sec_status = 1 ;
							 */
							traveler_position = $("#traveler_position").find(
									"option:selected").attr('value');
							Passengers_Array[Passengers_Number - 1] = [ , ,
									$("#m_num").val(), rankid,
									$("#traveler_name").val(),
									$("#traveler_aqdamya_num").val(), unitid,
									traveler_position, traveler_sec_status,
									$("#traveler_add_notes").val() ];

							var data = Passengers_Array[Passengers_Number - 1];

							// add_traverler_to_database(Passengers_Array[Passengers_Number-1]);

							var traverlers_array = {};
							traverlers_array.military_num = data[2];
							traverlers_array.rank_id = data[3];
							traverlers_array.name = data[4];
							traverlers_array.seniority_num = data[5];
							traverlers_array.unit_id = data[6];
							traverlers_array.job_desc = data[7];
							traverlers_array.security_status = data[8];
							traverlers_array.notes = '';
						superConnect(api_source + 'travelers/insert',traverlers_array,function(obj) {
									var params = {};
									params.traveler_id = data[2];
									params.mission_id = current_mission_id;
									params.traveler_ship = GlobalShipEntryID;
									params.traveler_position = data[7];
									params.traveler_note = data[9];
									superConnect(api_source+ 'travelers_missions/insert',params,function(obj) {
									if (obj == null|| obj.error == 1) {
										alert("هذه الرقم العسكرى تم تسجيله على سجم إخر في نفس المأمورية");
									} else {
										Push_Passengers_Array();
									}
											});
								});

						}

					});
	$("#traveler_add_notes")
			.bind(
					'keypress',
					function(e) {
						var index = military_num_array.indexOf($("#m_num")
								.val()); //
						if (e.keyCode == 13) {
							if (index != -1) {
								alert("هذا الرقم العسكرى تم تسجيله من قبل");
								return;
							}
							var rankid = $("#traveler_rank").find(
									"option:selected").attr('value');
							var unitid = $("#traveler_unit").find(
									"option:selected").attr('value');
							if (rankid == -1 || unitid == -1) {
								alert("تأكد من إدخال جميع البيانات");
								return;
							}
							if ($("#traveler_add_notes").val().indexOf('/') != -1
									|| $("#traveler_add_notes").val().indexOf(
											'0') != -1) {
								alert("عنوان المكاتبة لا يجب أن يحتوى على العلامات / أو . ");
								return;
							}
							military_num_array.push($("#m_num").val());
							var traveler_sec_status = $(
									"#traveler_security_status").find(
									"option:selected").attr('value');
							console.debug(">>>>>>>>>>>>>>>>>>"
									+ traveler_sec_status)
							/*
							 * if ($("#traveler_security_status").val()=='on')
							 * sec_status = 0 ; else sec_status = 1 ;
							 */
							traveler_position = $("#traveler_position").find(
									"option:selected").attr('value');
							Passengers_Array[Passengers_Number - 1] = [ , ,
									$("#m_num").val(), rankid,
									$("#traveler_name").val(),
									$("#traveler_aqdamya_num").val(), unitid,
									traveler_position, traveler_sec_status,
									$("#traveler_add_notes").val() ];

							var data = Passengers_Array[Passengers_Number - 1];

							// add_traverler_to_database(Passengers_Array[Passengers_Number-1]);

							var traverlers_array = {};
							traverlers_array.military_num = data[2];
							traverlers_array.rank_id = data[3];
							traverlers_array.name = data[4];
							traverlers_array.seniority_num = data[5];
							traverlers_array.unit_id = data[6];
							traverlers_array.job_desc = data[7];
							traverlers_array.security_status = data[8];
							traverlers_array.notes = '';
							superConnect(
									api_source + 'travelers/insert',
									traverlers_array,
									function(obj) {
										var params = {};
										params.traveler_id = data[2];
										params.mission_id = current_mission_id; // Need
										// to
										// be
										// changed
										// (
										// why
										// mission_id
										// default
										// value
										// = 0
										// ) (
										// Gives
										// me
										// an
										// error
										// in
										// the
										// console
										// of
										// the
										// eclipse
										// when
										// Inserting
										// new
										// array
										// )
										params.traveler_ship = GlobalShipEntryID;
										params.traveler_position = data[7];
										params.traveler_note = data[9];
										superConnect(
												api_source
														+ 'travelers_missions/insert',
												params,
												function(obj) {
													if (obj == null
															|| obj.error == 1) {
														alert("هذه الرقم العسكرى تم تسجيله على سجم إخر في نفس المأمورية");
													} else {
														Push_Passengers_Array();
													}
												});
									});
						}
					});

	$("#m_num").on("input", function() {
		Search_Traveler_By_Id();
	});

}

function removetraveler(index) {
	if (Object.size(Passengers_Array) == 1) {
		remove_traverler_from_database(military_num_array[0]);
		Passengers_Array = [];
		military_num_array = [];
		ViewPassengersTable();
		return;
	}
	Passengers_Array.splice(index, 1);
	remove_traverler_from_database(military_num_array[index]);
	military_num_array.splice(index, 1);
	Passengers_Number--;
	ViewPassengersTable();
}

function Search_Traveler_By_Id() {
	var traveler_params = {};
	traveler_params.military_num = $("#m_num").val();
	passenger_position_options =  '<option value ='+1+'>'+ 'أساسى' + '</option>'+'<option value ='+2+'>'+ 'إحتياطى' + '</option>' ;
	passenger_security_options =   '<option value ='+1+'>'+ 'لا يوجد' + '</option>' +'<option value ='+2+'>'+ 'يوجد' + '</option>';
	superConnect(
			api_source + 'travelers/search',
			traveler_params,
			function(obj) {
				if (obj == null || Object.size(obj) == 0 || obj.error == 1
						|| obj[0].military_num == 0) {
					$("#traveler_rank").html(passengers_ranks);
					$("#traveler_name").val("");
					$("#traveler_unit").html(passengers_units);
					$("#traveler_position").html(passenger_position_options);
					$("#traveler_security_status").html(
							passenger_security_options);
					$("#traveler_add_notes").val("");
					return;
				}
				traveler_rank = '<option value=' + obj[0].rank_id
						+ ' selected>' + rank_name[obj[0].rank_id]
						+ '</option>';
				traverler_unit = '<option value=' + obj[0].unit_id
						+ ' selected>' + internal_units_name[obj[0].unit_id]
						+ '</option>';
				// traveler_position = (obj[0].job_description == 1)?('<option
				// selected value ='+1+'>'+ 'أساسى' + '</option>'):('<option
				// selected value ='+2+'>'+ 'إحتياطى' + '</option>') ;
				traveler_sec_status = (obj[0].security_status == 1) ? ('<option selected value ='
						+ 1 + '>' + 'لا يوجد' + '</option>')
						: ('<option selected value =' + 2 + '>' + 'يوجد' + '</option>');
				$("#traveler_rank").html(traveler_rank);
				$("#traveler_name").val(obj[0].name);
				$("#traveler_aqdamya_num").val(obj[0].seniority_num);
				$("#traveler_unit").html(traverler_unit);
				// $("#traveler_position").html(traveler_position) ;
				$("#traveler_security_status").html(traveler_sec_status);
				$("#traveler_add_notes").val(obj[0].notes);

			});
}

function add_traverler_to_database(data) {

	var traverlers_array = {};
	traverlers_array.military_num = data[2];
	traverlers_array.rank_id = data[3];
	traverlers_array.name = data[4];
	traverlers_array.seniority_num = data[5];
	traverlers_array.unit_id = data[6];
	traverlers_array.job_desc = data[7];
	traverlers_array.security_status = data[8];
	traverlers_array.notes = data[9];
	superConnect(
			api_source + 'travelers/insert',
			traverlers_array,
			function(obj) {
				var params = {};
				params.traveler_id = data[2];
				params.mission_id = current_mission_id; // Need to be changed (
				// why mission_id
				// default value = 0 ) (
				// Gives me an error in
				// the console of the
				// eclipse when
				// Inserting new array )
				params.traveler_ship = GlobalShipEntryID;
				params.traveler_position = data[7];
				superConnect(
						api_source + 'travelers_missions/insert',
						params,
						function(obj) {
							if (obj == null || obj.error == 1) {
								alert("هذه الرقم العسكرى تم تسجيله على سجم إخر في نفس المأمورية");
								Passengers_Number--;
								ViewPassengersTable();
							}
						});
			});
}

function remove_traverler_from_database(m_num) {
	var params = {};
	params.traveler_id = m_num;
	params.mission_id = current_mission_id;
	superConnect(api_source + 'travelers_missions/remove', params,
			function(obj) {
			});
}

passengers_units = '<option value="-1">---  الوحدة   ---</option>';
passengers_ranks = '<option value="-1">--الرتبة---</option>';
var global_ship_name = ship_name[GlobalShipEntryID];

$(document).ready(
		function() {
			size = Object.size(internal_units);
			for (var i = 0; i < size; i++)
				passengers_units += '<option value="' + internal_units[i].id
						+ '">' + internal_units[i].name + '</option>';

			size = Object.size(rank);
			for (var i = 0; i < size; i++)
				if (rank[i].rank_type == 1)
					passengers_ranks += '<option value="' + rank[i].id + '">'
							+ rank[i].rank_name + '</option>';

			if (ma2morya_type == 1) {
				$('#Ma2morya_name').html(
						"تسجيل بيانات طاقم مأمورية التدريب المشترك" + " " + "("
								+ nashat_type + ")" + " " + nashat_name
								+ " على متن " + global_ship_name);
			} else {
				$('#Ma2morya_name').html(
						"تسجيل بيانات طاقم مأمورية رحلة الطلبة" + " ("
								+ nashat_type + ") " + nashat_name
								+ " على متن " + global_ship_name);
			}

			Push_Passengers_Array();

		});
