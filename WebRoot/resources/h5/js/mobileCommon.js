(function($){
    
    $(window).on("scroll swipeUp",function(){
        var ws = $(window).scrollTop();
        if (ws>0) {
            $(".gotop").show();
        }else{
            $(".gotop").hide();
        }
    });

    $(".gotop").on("click",function(){
        $(window).scrollTop(0);
    });

})(jQuery)       


