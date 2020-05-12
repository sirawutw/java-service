	var mapPo = new Map();
	var mapSo = new Map();
	var JSONSo = "";
	var JSONPo = "";	
	var param = {};
	var functionDone = false;
	
	setTimeout(function() {
		var paramPackage = {};
		var setSo;
		paramPackage.order_id = $('#ORDER_ID').val();
		$.post('./getSvcForPackage',paramPackage).done(function(data){
			JSONPackage = JSON.parse(data);
			console.log(JSONPackage);
			var checkmdn;
			JSONPackage.forEach(function(data){
				if(checkmdn!=data.MSISDN){
					setSo = "";
				}
				for(var i=0;i<JSONPackage.length;i++){
					if(data.MSISDN == $('#MSISDN_'+i).val()){
						$('#lsPo_'+i).text(mapPo.get(parseInt(data.PACKAGE_ID_1)));
						console.log(mapPo.get(mapPo.get(data.PACKAGE_ID_1)));
						for(var j=0;j<JSONPackage.length;j++){
							console.log(data.MSISDN);
							if(data.MSISDN == $('#MSISDN_'+j).val()){
								checkmdn = data.MSISDN;
								console.log(mapSo.get(parseInt(data.PACKAGE_ID_2)));
								setSo += mapSo.get(parseInt(data.PACKAGE_ID_2))+",";
								console.log(setSo);
							}
						}
						setSo = setSo.split('undefined').pop();
						$('#lsSo_'+i).text(setSo.slice(0, -1));
					}
					if(i==JSONPackage.length-1){
						$("#loader").hide();
					}
				}
			});
		});
	    functionDone = true;
	}, 5000);

$( document ).ready(function() {
	$("#loader").hide();
	var tablePortInHistoryAction = $('#tablePortInHistoryAction').DataTable();
	
	var soPackage = [];
	var getPrepaid=0;
	var getPostpaid=0;
	var checkload=0;
	var count=0;
	var setofferPo;
	param.order_id = $('#ORDER_ID').val();
	
	if (!functionDone) {
	
		$.post('./productcatalogPO',param).done(function(data){
			JSONPo = JSON.parse(data);
			console.log(JSONPo);
			var param = {};
			JSONPo.forEach(function(data){
				param.po = data.offeringId;
				mapPo.set(data.offeringId,data.offeringNameEn);
				$.post('./productcatalogSO',param).done(function(dataso){
					JSONSo = JSON.parse(dataso);
					var paramSo = {};
					JSONSo.forEach(function(dataList){
						var paramSo = {};
						mapSo.set(dataList.offeringId,dataList.offeringNameEn);
					});
				});
			});
		});

	var JSONPackage;
	var paramPackage = {};
	var setSo;
	paramPackage.order_id = $('#ORDER_ID').val();
	
	$.post('./getCustomerGroup',param).done(function(data){
		console.log(data);
		JSONData = JSON.parse(data);
		console.log(JSONData);
		$("#customer_segment").html(JSONData[0][0][0].SEGMENT_NAME);
		$("#customer_group").html(JSONData[0][0][0].CATEGORY_NAME);
	});
	
	function hideLoader(){
		$("#loader").hide();
	}
	
	function test(){
		console.log(mapPo.get(51005955));
	}
	
	$('.modalPortInHistoryAction').click(function(){
		var ORDER_ID = $(this).parents('td').find('#ORDER_ID').val();
		var MDN = $(this).parents('td').find('#MDN').val();
		var SRV_SEQ_ID = $(this).parents('td').find('#SRV_SEQ_ID').val();
		$("#msisdn").text(MDN);
		$("#orderId").text(ORDER_ID);
		var param = {};
		param.order_id = ORDER_ID;
		param.svc_seq = SRV_SEQ_ID;
		param.mdn = MDN;
		$.post('./modalportinhistoryaction',param).done(function(data){
			var tableData = [];
			tablePortInHistoryAction.clear().draw();
			JSONData = JSON.parse(data);
			console.log(JSONData);
			JSONData.forEach(function(data){
				tableData = [];
				tableData.push(data.TRANS_SEQ ? data.TRANS_SEQ : "");
				tableData.push(data.TRANS_DTM ? data.TRANS_DTM : "");
				tableData.push(data.TRANS_USER ? data.TRANS_USER : "");
				tableData.push(data.SVC_STATUS ? data.SVC_STATUS : "");
				tablePortInHistoryAction.row.add(tableData);
				tablePortInHistoryAction.draw();
			});
			$('#modal_port_in_history_action').modal();
		});
	});
	
	var tableEditReason = $('#tableEditReason').DataTable();
	
	$('#viewEditReason').click(function(){
		$("#e_orderId").text($("#order_id").text());
		var param = {};
		param.order_id = $("#order_id").text();
		$.post('./modaleditreasonlist',param).done(function(data){
			var tableData = [];
			tableEditReason.clear().draw();
			JSONData = JSON.parse(data);
			console.log(JSONData);
			test();
			var i = 1;
			JSONData.forEach(function(data){
				tableData = [];
				tableData.push(i++);
				tableData.push(data.LOG_TIME ? data.LOG_TIME : "");
				tableData.push(data.EMPLOYEEID ? data.EMPLOYEEID : "");
				tableData.push((data.REASON_CODE ? data.REASON_CODE : "")+" "+(data.REASON_TEXT ? data.REASON_TEXT : ""));
				tableEditReason.row.add(tableData);
				tableEditReason.draw();
			});
			$('#modal_edit_reason_list').modal();
		});
	});
	
	function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	}
	
	function exportBill(order_id){
		console.log(order_id);
		var param = {};
		param.order_id = order_id;
		param.original = "F";
		$.post('./exportbilltohtml',param).done(function(data){

		});
	}

	$('#printReceipt').click(function(){	
		console.log("printReceipt");
		window.open('./invoiceprint?order_Id='+$("#order_id").text(), '_blank');
	});
	
	function ckButton(){
		if($("#cancel_payment_reason").val()==""){
			$("#submitCancelPayment").attr("disabled", true);
		}else{
			$("#submitCancelPayment").attr("disabled", false);
		}
	}
	
	$('#cancelReceipt').click(function(){
		ckButton();
		$('#modal_cancel_receipt').modal();
	});
	
	$("#cancel_payment_reason").change(function(){
		ckButton();
	});
	
	$('#submitCancelPayment').click(function(){
		param = {};
		param.orderId = $("#order_id").text();
		param.receipt_id = $('#RECEIPT_ID_0').text();
		param.cancel_reason = $("#cancel_payment_reason").val();
		param.cancel_by = $("#userLogin").val();
		console.log(param);
		var length = $('#tableList tr').length - 1;
		$.post('./docancelorderpayment',param).done(function(data){
			console.log("docancelorderpayment: "+data);
			JSONData = JSON.parse(data);
			if(JSONData[0]=="0"){
				swal("ยกเลิกใบเสร็จ", "ทำรายการสำเร็จ", "success");
				$('#modal_cancel_receipt').modal('hide');
				//$("#printReceipt").addClass('disabled');
				//$("#cancelReceipt").addClass('disabled');
				$("#printReceipt").attr("disabled", true);
				$("#cancelReceipt").attr("disabled", true);
				$("#PORTIN_STATUS").text("Initial Request");
				for(i=0; i<length; i++){
					$('#SRV_STATUS_'+i).text("Initial Request");
				}
				//location.reload();
			}else{
				swal("ยกเลิกใบเสร็จ", "ทำรายการไม่สำเร็จ", "error");
			}
		});
	});
	
	$('.buttonTerminate').click(function(){
		var INDEX = $(this).parents('tr').find('#INDEX').val();
		var ORDER_ID = $(this).parents('tr').find('#ORDER_ID').val();
		var MSISDN = $(this).parents('tr').find('#MDN').val();
		var PORT_ID = $(this).parents('tr').find('#PORT_ID').val();
		var ORDER_USER = $("#userLogin").val();
		var arrayMSISDN = [];
		var arrayPORT_ID = [];
		arrayMSISDN[0] = MSISDN;
		arrayPORT_ID[0] = PORT_ID;
		var param = {"arrayPORT_ID[]": arrayPORT_ID,
					 "orderId": ORDER_ID,
					 "arrayMSISDN[]": arrayMSISDN,
					 "orderUser": ORDER_USER
					};
		console.log(param);
		/*
		$.post('./docreateportterminate',param).done(function(data){
			JSONTerminateData = JSON.parse(data);
			if(JSONTerminateData[0]=="0"){
				sweetAlert("สถานะรายการ", "Send Port Terminate Success", "success");
			}
		});
		*/
		swal({
			  title: "ยืนยันการทำรายการ",
			  text: "ต้องการ Terminate หมายเลข "+MSISDN,
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonClass: "btn-danger",
			  confirmButtonText: "ทำรายการ",
			  cancelButtonText: "ยกเลิก",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm) {
			  if (isConfirm) {
				  $.post('./docreateportterminate',param).done(function(data){
						JSONTerminateData = JSON.parse(data);
						if(JSONTerminateData[0]=="0"){
							swal("สถานะรายการ", "ทำรายการสำเร็จ", "success");
							$("#buttonTerminate_"+INDEX).attr("disabled", true);
						}else{
							swal("สถานะรายการ", "ทำรายการไม่สำเร็จ", "error");
						}
					});
			  } else {
				  swal("สถานะรายการ", "ยกเลิกการทำรายการ", "error");
			  }
			});
	});
	
	$("#backPage").click(function(){
		var action = "search_portinStatus="+$("#search_portinStatus").val();
		action += "&search_startReceiveDate="+$("#search_startReceiveDate").val();
		action += "&search_endReceiveDate="+$("#search_endReceiveDate").val();
		action += "&search_mdn="+$("#search_mdn").val();
		action += "&search_orderId="+$("#search_orderId").val();
		action += "&search_fname="+$("#search_fname").val();
		action += "&search_lname="+$("#search_lname").val();
		action += "&search_userid="+$("#search_userid").val();
		action += "&search_fname="+$("#search_userid").val();
		action += "&search_docno="+$("#search_docno").val();
		action += "&search_portid="+$("#search_portid").val();
		action += "&search_order_type="+$("#search_order_type").val();
		$("#formSearchPortIn").attr("action", "./"+$("#search_page").val()+"?" + action);
		$('#formSearchPortIn').submit();
	});
	}
});