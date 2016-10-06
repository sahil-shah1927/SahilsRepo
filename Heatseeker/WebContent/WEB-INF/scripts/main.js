$(document).ready(function() {

    $('[data-toggle="tooltip"]').tooltip();
    
    
});

function dropDown() {

	$(".issue-row").next().addClass("hid");
	$(".issue-row").next().fadeOut(0);
	$(".issue-row").click(function() {

		if( $(this).next().hasClass("hid") ){

			$(".issue-row").next().addClass("hid");
			$(".issue-row").next().fadeOut(500);
			$(this).next().removeClass("hid");
			$(this).next().fadeIn(500);
		}
		else{
			$(this).next().addClass("hid");
			$(this).next().fadeOut(500);
		}
	});
	
}


$(".home-description").fadeOut(0);

$(".home-box").click(function(){

	$(".home-description").fadeIn(800);
	$(this).removeAttr("role");
})

