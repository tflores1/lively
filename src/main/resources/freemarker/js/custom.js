$(".dropdown-menu li a").click(function(){
  var selText = $(this).html();
  $(this).parents('.dropdown').find('.dropdown-toggle')
         .html(selText+'<span class="caret"></span>');
});