function initPage() {
	$(".imgGoalsDelete").live("click", DeleteRow);
	$(".imgGoalsAdd").live("click", AddGoalsRow);
	setupData();
	$(".submitbtn").click(prepareSave);
}

/**
 * This function setup the data from txtJsonData value
 * 
 * @return
 */
function setupData() {
	var wgt = zk.Widget.$($("$txtJsonData").attr("id"));
	var data = {};
	if ($.isEmptyObject(wgt)) {
		return;
	} else {
		data = $.parseJSON(wgt.getValue());
	}
	$("#txtNotes").val(data.notes);
	$("#txtDueDate").val(data.duedate);

	/* setup goals */
	if (data.goals.length > 0) {
		var buffer = new Array();
		/* first row will be with no delete image */
		buffer.push('<tr>');
		buffer
				.push('<td>Goal &nbsp;<input type="text" size="40" style="width:70%" value="' + data.goals[0].goal + '" /></td>');
		buffer
				.push('<td>For &nbsp;<input type="text" size="40" style="width:70%" value="' + data.goals[0].time + '"/></td>');
		if ($("#isReadonly").val() != "true" ) {
		buffer.push('<td><img src="/img/add.png" class="imgGoalsAdd" /></td>');
		}
		buffer.push('</tr>');
		/* for rest of the data add a new row */
		for ( var i = 1, len = data.goals.length; i < len; i++) {
			buffer.push('<tr>');
			buffer
					.push('<td>Goal &nbsp;<input type="text" size="40" style="width:70%" value="' + data.goals[i].goal + '"/></td>');
			buffer
					.push('<td>For &nbsp;<input type="text" size="40" style="width:70%" value="' + data.goals[i].time + '"/></td>');
			
			if ($("#isReadonly").val() != "true" ) {
				buffer
				.push('<td><img src="/img/delete.png" class="imgGoalsDelete" /><img src="/img/add.png" class="imgGoalsAdd" /></td>');
			}
			buffer.push('</tr>');
		}
		$("#tblGoals > tbody").html(buffer.join(''));
	}

	/* set up the program goals */
	if (data.programs.length == 4) {
		var rows = $("#tblPrograms > tbody > tr");
		for ( var i = 0; i < 4; i++) {
			$(rows[i]).find("td:nth-child(2)").find("input").val(data.programs[i].ccare);
			$(rows[i]).find("td:nth-child(3)").find("input").val(data.programs[i].mcare);
		}
	}
}

/**
 * This function adds a new row in goals
 * 
 * @return
 */
function AddGoalsRow() {
	var buffer = new Array();
	buffer.push('<tr>');
	buffer.push('<td>Goal &nbsp;<input type="text" size="40" style="width:70%" /></td>');
	buffer.push('<td>For &nbsp;<input type="text" size="40" style="width:70%" /></td>');
	buffer
			.push('<td><img src="/img/delete.png" class="imgGoalsDelete" /><img src="/img/add.png" class="imgGoalsAdd" /></td>');
	buffer.push('</tr>');
	$("#tblGoals > tbody").append(buffer.join(''));
	$("#tblGoals > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblGoals > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * This function deletes goals row
 * 
 * @return
 */
function DeleteRow() {
	$(this).parents("tr").remove();
	$("#tblGoals > tbody > tr:even").removeClass("z-listbox-odd");
	$("#tblGoals > tbody > tr:odd").addClass("z-listbox-odd");
}

/**
 * This function prepares data for saving
 * 
 * @return
 */
function prepareSave() {
	/* Prepare goals objects */
	var goals = new Array();
	$("#tblGoals > tbody > tr").each(function() {
		var goal = $.trim($(this).find("td:first input").val());
		if (goal == "") {
			return true;
		}
		var time = $.trim($(this).find("td:nth-child(2)").find("input").val());
		goals.push( {
			'goal' : goal,
			'time' : time
		});
	});

	/* prepare programs */
	var programs = new Array();
	$("#tblPrograms > tbody > tr").each(function(index) {
		/* Only first 4 rows are programs */
		if (index > 3) {
			return false;
		}
		var ccare = $.trim($(this).find("td:nth-child(2)").find("input").val());
		var mcare = $.trim($(this).find("td:nth-child(3)").find("input").val());
		var program = $.trim($(this).find("td:first").text());

		programs.push( {
			'program' : program,
			'ccare' : ccare,
			'mcare' : mcare
		});
		console.log(programs);
	});

	var retObj = {
		'goals' : goals,
		'programs' : programs,
		'notes' : $.trim($("#txtNotes").val()),
		'duedate' : $.trim($("#txtDueDate").val())
	};
	$("$txtJsonData").val($.toJSON(retObj)).blur();
	return true;
}

/**
 * Navigates to prev/next page
 * 
 * @param direction
 * @return
 */
function navigate(direction) {
	if (direction == "Next") {
		var wgt = zk.Widget.$($("$tbtnEMI").attr("id"));
		zUtl.go(wgt._href);
	} else {
		var wgt = zk.Widget.$($("$tbtnMedHistory").attr("id"));
		zUtl.go(wgt._href);
	}
}