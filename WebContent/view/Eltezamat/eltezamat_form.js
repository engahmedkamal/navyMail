var array = [];
var canAdd = 0;
var cur_index = -1;

function AddNewEntry() {
	if (canAdd != 0)
		return;
	document.getElementById("eltezamprintbutton").style.visibility = "hidden";
	document.getElementById("eltezamsavebutton").style.visibility = "visible";
	canAdd = 1;
	var t = "";
	cur_index = Object.size(array);
	array.push([ , t, t, t, t, t, t, t ]);
	viewTable();
}

function removeEntery(index) {
	if (Object.size(array) == 1) {
		array = [];
		viewTable();
		return;
	}
	array.splice(index, 1);
	viewTable();
}

function save() {
	array[cur_index][1] = $("#date").val();
	array[cur_index][2] = $("#time").val();
	array[cur_index][3] = $("#title").val();
	array[cur_index][4] = $("#place").val();
	array[cur_index][5] = $("#sader_num").val();
	array[cur_index][6] = $("#sader_date").val();
	array[cur_index][7] = $("#unit").val();
	cur_index = -1;
	canAdd = 1;
	viewTable();
}
function saveLastRow() {
	document.getElementById("eltezamsavebutton").style.visibility = "hidden";
	document.getElementById("eltezamprintbutton").style.visibility = "visible";
	canAdd = 0;
	cur_index = -1;
	viewTable();
	if(current_view_eltezamat == 1){
		superConnect(api_source + 'eltezamat/remove/ka2ed', {}, function(obj) {
		});
	}
	else { 
		superConnect(api_source + 'eltezamat/remove/arkan', {}, function(obj) {
		});
	}
	
	
	var size = Object.size(array);

	for ( var row = 0; row < size; row++) {
		var params = {};
		params.seq = (row+1);
		params.date = $.trim(array[row][1]);
		params.time = $.trim(array[row][2]);
		params.title = $.trim(array[row][3]);
		params.place = $.trim(array[row][4]);
		params.sader_num = $.trim(array[row][5]);
		params.sader_date = $.trim(array[row][6]);
		params.unit = $.trim(array[row][7]);
		if(current_view_eltezamat == 1){
			superConnect(api_source + 'eltezamat/save/ka2ed', params, function(obj) {
			});
		}else{
			superConnect(api_source + 'eltezamat/save/arkan', params, function(obj) {
			});
		}
		
		
	}

}

function viewTable() {
	var index = 1;
	var NewEntry = '';
	var size = Object.size(array);
	
	for ( var row = 0; row < size; row++) {
		if (row == cur_index) {
			NewEntry += "<tr id='currow' style=font-size:16px;color:black;text-align:center;>"
					+ "<td><button class='btn btn-sucess' onClick=save()><i class='icon-ok bigger-110'></i>"
					+ "</button></td>";
			NewEntry +=  "<td><textarea id='date' class=form-control>  </textarea></td>"
					+ "<td><textarea  id='time'  class=form-control> </textarea></td>"
					+ "<td><textarea  id='title' maxlength='120' class=form-control> </textarea></td>"
					+ "<td><textarea  id='place'  class=form-control> </textarea></td>"
					+ "<td><textarea  id='sader_num'  class=form-control> </textarea></td>"
					+ "<td><textarea  id='sader_date'  class=form-control> </textarea></td>"
					+ "<td><textarea  id='unit'  class=form-control> </textarea></td>"
					+ "</tr>";
		} else {
			if (canAdd == 1) {
				NewEntry += "<tr id='currow' style=font-size:16px;color:black;text-align:center;>"
						+ "<td><button class=\'btn btn-danger\' onClick=removeEntery("+ row + ")><i class='icon-remove red'></i></button>"
						+ '<button onClick=upCurr(' + row + ')>up</i></button>'
						+ "</td>";
			}else{
				NewEntry += "<tr id='currow' style=font-size:16px;color:black;text-align:center;>"
					+ "<td>"
					+ index++
					+ "</td>";
			}
			NewEntry += "<td>"+ array[row][1]+ "</td>"
					+ "<td>"+ array[row][2]+ "</td>"
					+ "<td>"+ array[row][3]+ "</td>"
					+ "<td>"+ array[row][4]+ "</td>"
					+ "<td>"+ array[row][5]+ "</td>"
					+ "<td>"+ array[row][6]+ "</td>"
					+ "<td>"+ array[row][7] + "</td>" + "</tr>";
		}
	}
	$("#Officers_Body").html(NewEntry);
}

function upCurr(index) {
	if (index == 0)
		return;
	var tmp = array[index];
	array[index] = array[index - 1];
	array[index - 1] = tmp;

	viewTable();
}

$(document).ready(function() {
	if(current_view_eltezamat == 1){
		document.getElementById("editButton").className = "btn btn-danger btn-md btn-block";
		array = eltezamat_ka2ed;
		document.getElementById("eltezamName").innerHTML = "الألتزامات الرئيسية للسيد قائد القوات البحرية";

	}
	else{
		document.getElementById("editButton").className = "btn btn-success btn-md btn-block";
		array = eltezamat_arkan;
		document.getElementById("eltezamName").innerHTML = "الألتزامات الرئيسية للسيد رئيس أركان القوات البحرية";

	}

	viewTable();
});