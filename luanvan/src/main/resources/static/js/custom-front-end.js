jQuery(".departments-menu-v2-title").click(function(){
	jQuery(".bg-black").css("display","block");
});

jQuery(".item-reviewed").hover(
	function(){
		jQuery(".bg-black").css("display","block");
	},
	function() {
		jQuery( ".bg-black" ).css("display","none");
	}
);

window.onclick = function(event) {
  if (!event.target.matches('.departments-menu-v2-title')) {
	  jQuery(".bg-black").css("display","none");
  }
}

	function ResCarouselCustom() {
        var items = $("#dItems").val(),
            slide = $("#dSlide").val(),
            speed = $("#dSpeed").val(),
            interval = $("#dInterval").val()

        var itemsD = "data-items=\"" + items + "\"",
            slideD = "data-slide=\"" + slide + "\"",
            speedD = "data-speed=\"" + speed + "\"",
            intervalD = "data-interval=\"" + interval + "\"";


        var atts = "";
        atts += items != "" ? itemsD + " " : "";
        atts += slide != "" ? slideD + " " : "";
        atts += speed != "" ? speedD + " " : "";
        atts += interval != "" ? intervalD + " " : ""

        //console.log(atts);

        var dat = "";
        dat += '<h4 >' + atts + '</h4>'
        dat += '<div class=\"resCarousel\" ' + atts + '>'
        dat += '<div class="resCarousel-inner">'
        for (var i = 1; i <= 14; i++) {
            dat += '<div class=\"item\"><div><h1>' + i + '</h1></div></div>'
        }
        dat += '</div>'
        dat += '<button class=\'btn btn-default leftRs\'><i class=\"fa fa-fw fa-angle-left\"></i></button>'
        dat += '<button class=\'btn btn-default rightRs\'><i class=\"fa fa-fw fa-angle-right\"></i></button>    </div>'
        $("#customRes").html(null).append(dat);

        if (!pageRefresh) {
            ResCarouselSize();
        } else {
            pageRefresh = false;
        }
        //ResCarouselSlide();
    }

    $("#eventLoad").on('ResCarouselLoad', function() {
        //console.log("triggered");
        var dat = "";
        var lenghtI = $(this).find(".item").length;
        if (lenghtI <= 15) {
            for (var i = lenghtI; i < lenghtI; i++) {
                dat += '<div class="item"><div class="tile"><div><h1>' + (i + 1) + '</h1></div><h3>Title</h3><p>content</p></div></div>'
            }
            $(this).append(dat);
        }
    });


	$('.overflow-detail-product').css({height:'200px', overflow:'hidden'});

    $('.show-more').on('click', function() {
        
        $('.overflow-detail-product').animate({height:'100%'});

        $(this).css("display","none");
        $('.close-show-more').css("display","block");
    });

    $('.close-show-more').click(function(){
        $('.overflow-detail-product').animate({height:'200px'});

        $(this).css("display","none");
        $('.show-more').css("display","block");
    })

