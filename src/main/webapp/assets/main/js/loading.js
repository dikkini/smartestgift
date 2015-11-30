(function( $ ){
    var methods = {
        init : function( options ) {

            return this.each(function(){
                $(this).width(options.width);
                $(this).hide();
                $(this).html('<img src="/assets/main/images/loading.gif" width="' + options.width
                    + '"/><span style="text-align: center; color: #9a9a9a; font-size: 12px"> '
                    + options.text + '</span>');

                if (!$(this).hasClass('loading')) {
                    $(this).addClass('loading');
                }
            });

        },
        start: function() {
            $(this).fadeIn(500);
        },
        stop: function() {
            $(this).fadeOut(500);
        }
    };

    $.fn.loading = function(method, options) {
        if ( methods[method] ) {
            return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.loading' );
        }
    };
})( jQuery );