
$(document).ready(function(){
	setTimeout(function(){
		$("#alerts").css("visibility","visible").hide().fadeIn(700);
	},300);
	

});

$(document).ready(function(){
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
		},2000)
		
		
		
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


	})




 
     $("#createIssueForm").on('submit',function(e) {
    	 
    		$
    															.ajax({
    																type : "POST",
    																url : 'createIssueMessage',
    																data : {
    																	"message" : "A new issue has been created."
    																},
    																cache : false,
    																success : function(
    																		data) {
    																}

    															});
    	
     });



