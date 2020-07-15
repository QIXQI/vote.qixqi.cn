$(document).ready(function(){
	var data;
	var columns = [];
	var el;
	var vote;
	var optionCount;
	var isAdded = false;
	var eTarget;
	var demon = 'http://localhost:8080';
	var isUpdating = false;		// 上锁
	
	const VoteType = {
		NORMBAL_OPTION: 0,
		IMG_OPTION: 1,
		AUDIO_OPTION: 2,
		VIDEO_OPTION: 3
	}
	
	var voteName = getQueryString('voteName');
	// 获取投票信息
	$.ajax({
		url: 'getVoteByName.do',
		type: 'POST',
		dataType: 'json',
		data: {voteName: voteName},
		async: true,
		success: function(message){
			if (message.vote == undefined){
				alert(voteName + ' 还未发布！');
				console.error(voteName + ' 还未发布！');
			} else{
				vote = message.vote;
				console.log(vote);
				changeInputFile();	// 改变 input['file']属性
				optionCount = addColumns(message.vote);
				console.log('optionCount: ' + optionCount);
				el = jexcel($('#addOption').get(0), {
					data: [],
					columns: columns,
					minDimensions: [optionCount, 10],
					colWidths: [120, 120, 120, 120, 120, 120]
				});
				/* el.setStyle({
					color: 'red'
				}); */
				console.log(columns);
				console.log(el.getStyle());
			}
		},
		error: function(err){
			alert('出现错误，请查看日志！');
			console.error(err.responseText);
		}
	});
	
	$('#delete').click((event) => {
		alert('删除 ' + vote.voteId);
	});
	
	$('#submit').click((event) => {
		if (isUpdating){
			alert('文件正在上传，请稍后提交');
			return;
		}
		
		data = el.getJson();
		console.log(data);
		data = data.map((row, index) => {
			if (row[0].trim() !== ''){
				var temp = {};
				var j;
				for (j=0; j<optionCount; j++){
					temp['optionDesc' + (j+1)] = row[j].trim();
				}
				
				if (vote.voteType === VoteType.NORMBAL_OPTION){
					if (!isAdded){
						isAdded = true;
					}
				} else if (row[j].trim() === ''){
					return null;
				} else if (!isAdded){
					isAdded = true;
				}
				if (vote.voteType === VoteType.IMG_OPTION){
					temp['imgUrl'] = row[j].trim();
				} else if (vote.voteType === VoteType.AUDIO_OPTION){
					temp['audioUrl'] = row[j].trim();
				} else if (vote.voteType === VoteType.VIDEO_OPTION){
					temp['videoUrl'] = row[j].trim();
				}
				return temp;
			} else{
				return null;
			}
		});
		console.log(data);
		console.log(JSON.stringify(data));
		
		// isAdded = false;
		if (!isAdded){
			alert('您还未填写有效数据');
		} else{
			// 发送post请求
			$.ajax({
				url: 'addOptions.do',
				type: 'POST',
				dataType: 'json',
				data: {
					optionType: vote.voteType,
					voteId: vote.voteId,
					options: JSON.stringify(data)
				},
				async: true,
				success: function(message){
					alert(message.result);
					console.log(message);
				},
				error: function(err){
					alert('提交出错');
					console.error(err.responseText);
				}
			});
		}
	});
	
	function getQueryString(name) {
	    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	    let urlSearch = decodeURIComponent(window.location.search);
	    let r = urlSearch.substr(1).match(reg);
	    if (r != null) {
	        return unescape(r[2]);
	    };
	    return null;
	}
	
	function addColumns(vote){
		var count = 1;
		columns.push({type: 'text', title: vote.voteDesc1 + ' *', width: 120});
		if (vote.voteDesc2 != null && vote.voteDesc2 != undefined){
			columns.push({type: 'text', title: vote.voteDesc2, width: 120});
			count = count + 1;
		}
		if (vote.voteDesc3 != null && vote.voteDesc3 != undefined){
			columns.push({type: 'text', title: vote.voteDesc3, width: 120});
			count = count + 1;
		}
		if (vote.voteDesc4 != null && vote.voteDesc4 != undefined){
			columns.push({type: 'text', title: vote.voteDesc4, width: 120});
			count = count + 1;
		}
		if (vote.voteDesc5 != null && vote.voteDesc5 != undefined){
			columns.push({type: 'text', title: vote.voteDesc5, width: 120});
			count = count + 1;
		}
		if (vote.voteType === VoteType.IMG_OPTION){
			columns.push({type: 'text', title: 'imgUrl'});
		}
		else if (vote.voteType === VoteType.AUDIO_OPTION){
			columns.push({type: 'text', title: 'audioUrl'});
		}
		else if (vote.voteType === VoteType.VIDEO_OPTION){
			columns.push({type: 'text', title: 'videoUrl'});
		}
		return count;
	}
	
	$(document).dblclick((event) => {
		if ($(event.target).data('x') === optionCount && $(event.target).attr('title') === undefined){
			
			if(isUpdating){
				alert('文件正在上传中，请稍后继续');
				return;
			}
			
			console.log('拨开云雾见天日');
			// alert($(event.target).attr('title'));
			$('#fileUpload').click();
			eTarget = event.target;
		}
	});
	
	$('#fileUpload').change((event) => {
		if ($('#fileUpload').get(0).files.length !== 0){
			uploadFile();
		}
	});
	
	function changeInputFile(){
		if (vote.voteType === VoteType.NORMBAL_OPTION){
			$('#fileUpload').attr('disabled', 'disabled');
		} else if (vote.voteType === VoteType.IMG_OPTION){
			$('#fileUpload').attr('accept', 'image/*');
		} else if (vote.voteType === VoteType.AUDIO_OPTION){
			$('#fileUpload').attr('accept', 'audio/*');
		} else if (vote.voteType === VoteType.VIDEO_OPTION){
			$('#fileUpload').attr('accept', 'video/*');
		}
	}
	
	function uploadFile(){
		// alert('即将上传文件');
		// alert($(eTarget).data('x'));
		var formData = new FormData();
		formData.append('optionFile', $('#fileUpload').get(0).files[0]);
		$.ajax({
			url: 'optionFileUpload.do',
			type: 'POST',
			async: true,
			dataType: 'json',
			data: formData,
			processData: false,		// 数据不作处理
			contentType: false, 	// 不要设置Content-Type请求头
			success: function(message){
				console.log(message);
				if (message.result === 'success'){
					// 提交文件成功
					if (vote.voteType === VoteType.IMG_OPTION){
						el.setValueFromCoords($(eTarget).data('x'), $(eTarget).data('y'), message.fileUrl);
						$(eTarget).html('<img src="' + demon + message.fileUrl + '"/>');
						el.setWidth(optionCount, 200);
					} else if (vote.voteType === VoteType.AUDIO_OPTION){
						el.setValueFromCoords($(eTarget).data('x'), $(eTarget).data('y'), message.fileUrl);
						$(eTarget).html('<audio controls><source src="' + demon + message.fileUrl + '"></source> 您的浏览器不支持 html5 audio元素！</audio>');
						el.setWidth(optionCount, 320);
					} else if (vote.voteType === VoteType.VIDEO_OPTION){
						el.setValueFromCoords($(eTarget).data('x'), $(eTarget).data('y'), message.fileUrl);
						$(eTarget).html('<video width="320" height="240" controls><source src="' + demon + message.fileUrl + '"></source> 您的浏览器不支持 html5 video元素！</video>');
						el.setWidth(optionCount, 350);
					}
					$('#fileUpload').val('');
				} else{
					alert('提交文件失败: ' + message.result);
					$('#fileUpload').val('');
				}
				
				// 没有文件在上传
				isUpdating = false;
			},
			error: function(err){
				alert('上传文件出错，请查看日志！');
				console.log(err.responseText);
				$('#fileUpload').val('');
				
				// 没有文件在上传
				isUpdating = false;
			}
		});
	}
	
	/* jexcel($('#addOption').get(0), {
		data: [],
		columns: [
	        { type: 'text', title:'Car', width:120 },
	        { type: 'dropdown', title:'Make', width:200, source:[ "Alfa Romeo", "Audi", "Bmw" ] },
	        { type: 'calendar', title:'Available', width:200 },
	        { type: 'image', title:'Photo', width:120 },
	        { type: 'checkbox', title:'Stock', width:80 },
	        { type: 'numeric', title:'Price', width:100, mask:'$ #.##,00', decimal:',' },
	        { type: 'color', width:100, render:'square', }
		]
	});*/
});