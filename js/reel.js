var threelogy = threelogy || {};

threelogy.init = function(){
    isotopeFiltering();
    //onReelVideoClick();
};

var onReelVideoClick = function(){
    $('.popup-vimeo').magnificPopup({
        disableOn: 700,
        type: 'iframe',
        mainClass: 'mfp-fade',
        removalDelay: 160,
        preloader: false,
        fixedContentPos: false
    });
};

var isotopeFiltering = function(){
    var nameRegex;
    var buttonFilter1 = "*";

    var $container = $('.grid').isotope({
        itemSelector: '.reel-image-item',
        layoutMode: 'fitRows',
        filter: function() {
            var $this = $(this);
            var searchResult1 = nameRegex ? $this.text().match( nameRegex ) : true;
            var buttonResult = $this.is( buttonFilter1 );
            return searchResult1 && buttonResult;
        },
    });

    $('#filters-button-group').on( 'click', 'button', function() {
        buttonFilter1 = $( this ).attr('data-filter');
        $container.isotope({});
        return false;
    });




    $('.button-group').each( function( i, slidein ) {
        var $slidein = $( slidein );
        $slidein.on( 'click', 'button', function() {
            $slidein.find('.is-checked').removeClass('is-checked');
            $( this ).addClass('is-checked');
        });
    });


    var $quicksearch1 = $('#search-name').keyup( debounce( function() {
        nameRegex = new RegExp( $quicksearch1.val(), 'gi' );
        $container.isotope();
    }) );

    function debounce( fn, threshold ) {
        var timeout;
        return function debounced() {
            if ( timeout ) {
                clearTimeout( timeout );
            }
            function delayed() {
                fn();
                timeout = null;
            }
            setTimeout( delayed, threshold || 100 );
        };
    }

    function escapeRegExp(str) {
        return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
    }
};

$(function(){
    threelogy.init();
})