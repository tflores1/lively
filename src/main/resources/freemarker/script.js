$( document ).ready(function() {
	$(".small").click(function(){
		  if($(".logo").css("left") == "0px") {
		    $(".logo").css("left","-200px");
		    $(".sidebar").css("left","-235px");
		    $("#page-content").css("left","56px");
		    $(".small").html("&raquo");
		  } else {
		    $(".logo").css("left","0");
		    $(".sidebar").css("left","0");
		    $("#page-content").css("left","270px");
		    $(".small").html("&laquo");
		  }
		})
		$("#left li").hover(function(){
		  $("#left li").removeClass("hover")
		  $(this).addClass("hover")
		},function(){
		  $(this).removeClass("hover")
		})
		$("#left li").click(function(){
		  $("#left li").removeClass("active");
		  $(this).addClass("active");
		})
		$(window).resize(function() {
			if($(window).width() < 760) {
		  $(".logo").css("left","-200px");
		  $(".sidebar").css("left","-235px");
		  $(".small").html("&raquo");
		  $("#page-content").css("left","0px");
		  $("#profilepic").css("margin-right","10px");
		  $("#name i").hide();
		  $("#name").css("color","white");
		  $("#name span").css("color","black");
		  $("#name").css("width","calc(100% - 55px)")
		  $("#home-nav li").css({"float":0,"width":"100%"})
		}
	})
	$(".tabs a").click(function(){
		$(".tabs a").removeClass("active");
		$(this).addClass("active");
		
		if($("#account").hasClass("active")){
			
		}
	})
});
