(function($){
    $(function() {
        // Автодополнение населённых пунктов
        $( '[name="location"]' ).kladr({
            token: '51dfe5d42fb2b43e3300006e',
            key: '86a2c2a06f1b2451a87d05512cc2c3edfdf41969',
            type: $.kladr.type.city,
            select: function( obj ) {
                // Изменения родительского объекта для автодополнения улиц
                $( '[name="street"]' ).kladr('parentId', obj.id);
            }
        });

        // Автодополнение улиц
        $( '[name="street"]' ).kladr({
            token: '51dfe5d42fb2b43e3300006e',
            key: '86a2c2a06f1b2451a87d05512cc2c3edfdf41969',
            type: $.kladr.type.street,
            parentType: $.kladr.type.city
        });
    });
})(jQuery);