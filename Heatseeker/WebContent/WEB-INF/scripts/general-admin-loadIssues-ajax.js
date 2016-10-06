
$(document).ready(function(){
	setTimeout(function(){
		$("#alerts").css("visibility","visible").hide().fadeIn(700);
	},300);
	

});


var initIssuesSize = 0;
	var initResponse;
	var initPage = 'AllGeneralIssues';
	var updates=0;

	var loadIssues = function(page, ele) {

		$
				.ajax({
					type : "GET",
					url : page,
					dataType : "html",
					success : function(response) {

						if (response !== initResponse
								|| (response.length - initIssuesSize) > 600) {
							initIssuesSize = response.length;
							initResponse = response;
							ele.html(response);
							dropDown();

							addUpdate();
							changeDepartment()

							$(".issue-row")
									.on(
											"click",
											function(e) {
												clearInterval(updates);

												$
														.ajax({
															type : "POST",
															url : 'setIssueId',
															data : {
																"issue_id" : $(
																		this)
																		.children(
																				"td:nth-child(2)")
																		.text()
															},
															cache : false,
															success : function(
																	data) {

															}

														});

											updates = 	window
														.setInterval(
																function() {
																	loadUpdates(
																			'getUpdates',
																			$(".allTabIssueUpdates"))
																}, 800);

												e.preventDefault();

											});

						} else if (initPage != page) {
							initIssuesSize = response.length;
							initPage = page;
							ele.html(response);
							dropDown();
							addUpdate();
							changeDepartment()
							$(".issue-row")
									.on(
											"click",
											function(e) {
												clearInterval(updates);

												$
														.ajax({
															type : "POST",
															url : 'setIssueId',
															data : {
																"issue_id" : $(
																		this)
																		.children(
																				"td:nth-child(2)")
																		.text()
															},
															cache : false,
															success : function(
																	data) {

															}

														});

											updates=	window
														.setInterval(
																function() {
																	loadUpdates(
																			'getUpdates',
																			$(".allTabIssueUpdates"))
																}, 800);

												e.preventDefault();

											});

						}

					}

				});
	};

	var interval = window.setInterval(function() {
		loadIssues('AllGeneralIssues',
				$("#All>div"));
	}, 300);

	$("#tabs").on("click", "a", function(event) {

		clearInterval(interval);

		row = $('#' + $(this).text() + ">div");
		path = $(this).text() + "GeneralIssues";

		interval = window.setInterval(function() {
			loadIssues(path, row);
		}, 300);

	});

	function addUpdate() {
		$('form.addIssueUpdateForm').on('submit', function(e) {
			$.post('addIssueUpdate', $(this).serialize(), function(data) {

			}).error(function() {

			});
			$(this).closest('form').find("input[type=text], textarea").val("");
			var submitButton = $('#addIssueUpdateButton');
			submitButton.prop("disabled", true);
			setTimeout(function() {
				submitButton.prop("disabled", false);
			}, 1000)
			e.preventDefault();
		});
	}

	var initUpdates;
	var loadUpdates = function(page, ele) {

		$.ajax({
			type : "GET",
			url : page,
			dataType : "html",
			success : function(response) {

				if (response !== initUpdates || initPage != page) {
					initUpdates = response;
					ele.html(response);
				}
			}
		});
	};
	
	
	

	function changeDepartment(){
		

		setTimeout(function(){
			$
			.ajax({
				type : "POST",
				url : 'removeIssueMessage',
				data : null,
				cache : false,
				success : function(
						data) {
				}

			});
		},100)
		
		
		
		setTimeout(function() {
			$("#alerts>div").css("opacity","0");
		}, 4500);
	
	
	var close = document.getElementsByClassName("closebtn");
	var i;

	for (i = 0; i < close.length; i++) {
		
		
	    close[i].onclick = function(){
	        var div = this.parentElement;
	        div.style.opacity = "0";
	        setTimeout(function(){ div.style.display = "none"; }, 900);
	    }
	}

 
     $(".changeDepartmentForm").on('submit',function(e) {
    
    	 $.post('changeDept', $(this).serialize(), function(data) {
    		
			}).error(function() {

			});
		
    	 
    setTimeout(function(){
    	
    	$
		.ajax({
			type : "POST",
			url : 'setDepartmentMessage',
			data : null,
			cache : false,
			success : function(
					data) {
				window.location.replace("genIssues");
			}

		});
    	
    },300)
    		 
    e.preventDefault();
    	
    
     });
		
		
	}

	

	
	
	