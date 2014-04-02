(function($){
    $(function() {
        var token = '51dfe5d42fb2b43e3300006e';
        var key = '86a2c2a06f1b2451a87d05512cc2c3edfdf41969';        
        
        var city = $( '[name="city"]' );
        var street = $( '[name="street"]' );
        var building = $( '[name="building"]' );
        var buildingAdd = $( '[name="building-add"]' );

        var map = null;
        var placemark = null;
        var map_created = false;

        // Формирует подписи в autocomplete
        var LabelFormat = function( obj, query ){
            var label = '';
            
            var name = obj.name.toLowerCase();
            query = query.toLowerCase();
            
            var start = name.indexOf(query);
            start = start > 0 ? start : 0;

            if(obj.typeShort){
                label += '<span class="ac-s2">' + obj.typeShort + '. ' + '</span>';
            }

            if(query.length < obj.name.length){
                label += '<span class="ac-s2">' + obj.name.substr(0, start) + '</span>';
                label += '<span class="ac-s">' + obj.name.substr(start, query.length) + '</span>';
                label += '<span class="ac-s2">' + obj.name.substr(start+query.length, obj.name.length-query.length-start) + '</span>';
            } else {
                label += '<span class="ac-s">' + obj.name + '</span>';
            }

            if(obj.parents){
                for(var k = obj.parents.length-1; k>-1; k--){
                    var parent = obj.parents[k];
                    if(parent.name){
                        if(label) label += '<span class="ac-st">, </span>';
                        label += '<span class="ac-st">' + parent.name + ' ' + parent.typeShort + '.</span>';
                    }
                }
            }

            return label;
        };
        
        // Подключение плагина для поля ввода города
        city.kladr({
            token: token,
            key: key,
            type: $.kladr.type.city,
            withParents: true,
            labelFormat: LabelFormat,
            verify: true,
            select: function( obj ) {
                city.parent().find( 'label' ).text( obj.type );
                street.kladr( 'parentType', $.kladr.type.city );
                street.kladr( 'parentId', obj.id );
                building.kladr( 'parentType', $.kladr.type.city );
                building.kladr( 'parentId', obj.id );
                Log(obj);
                AddressUpdate();
                MapUpdate();                
            },
            check: function( obj ) {
                if(obj) {
                    city.parent().find( 'label' ).text( obj.type );
                    street.kladr( 'parentType', $.kladr.type.city );
                    street.kladr( 'parentId', obj.id );
                    building.kladr( 'parentType', $.kladr.type.city );
                    building.kladr( 'parentId', obj.id );
                } 
                
                Log(obj);
                AddressUpdate();
                MapUpdate();                
            }
        });

        // Подключение плагина для поля ввода улицы
        street.kladr({
            token: token,
            key: key,
            type: $.kladr.type.street,
            labelFormat: LabelFormat,
            verify: true,
            select: function( obj ) {
                street.parent().find( 'label' ).text( obj.type );
                building.kladr( 'parentType', $.kladr.type.street );
                building.kladr( 'parentId', obj.id );
                Log(obj);
                AddressUpdate();
                MapUpdate();
            },
            check: function( obj ) {
                if(obj) {
                    street.parent().find( 'label' ).text( obj.type );
                    building.kladr( 'parentType', $.kladr.type.street );
                    building.kladr( 'parentId', obj.id );
                }
                
                Log(obj);
                AddressUpdate();
                MapUpdate();                 
            }
        });

        // Подключение плагина для поля ввода номера дома
        building.kladr({
            token: token,
            key: key,
            type: $.kladr.type.building,
            labelFormat: LabelFormat,
            verify: true,
            select: function( obj ) {
                Log(obj);
                AddressUpdate();
                MapUpdate();
            },
            check: function( obj ) {
                Log(obj);
                AddressUpdate();
                MapUpdate();                
            }
        });
        
        // Проверка названия корпуса
        buildingAdd.change(function(){
            Log(null);
            AddressUpdate();
            MapUpdate(); 
        });
        
        // Обновляет карту
        var MapUpdate = function(){
            var zoom = 12;
            var address = '';
            
            // Город
            var name = null;
            var type = null;            
            var obj = city.kladr('current');
            var value = $.trim(city.val());
            
            if(obj){
                name = obj.name;
                type = obj.type + '.';
            } else if(value){
                name = value;
                type = 'город';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
                zoom = 12;
            }
            
            // Улица
            name = null;
            type = null;            
            obj = street.kladr('current');
            value = $.trim(street.val());
            
            if(obj){
                name = obj.name;
                type = obj.type + '.';
            } else if(value){
                name = value;
                type = 'улица';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
                zoom = 14;
            }
            
            // Дом
            name = null;
            type = null;            
            obj = building.kladr('current');
            value = $.trim(building.val());
            
            if(obj){
                name = obj.name;
                type = 'дом';
            } else if(value){
                name = value;
                type = 'дом';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
                zoom = 16;
            }
            
            // Корпус
            name = null;
            type = null;            
            value = $.trim(buildingAdd.val());
            
            if(value){
                name = value;
                type = 'корпус';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
                zoom = 16;
            }

            if(address && map_created){
                var geocode = ymaps.geocode(address);
                geocode.then(function(res){
                    map.geoObjects.each(function (geoObject) {
                            map.geoObjects.remove(geoObject);
                    });

                    var position = res.geoObjects.get(0).geometry.getCoordinates();

                    placemark = new ymaps.Placemark(position, {}, {});

                    map.geoObjects.add(placemark);
                    map.setCenter(position, zoom);
                });
            }
        }
        
        // Обновляет текстовое представление адреса
        var AddressUpdate = function(){
            var address = '';
            var zip = '';
            
            // Город
            var name = null;
            var type = null;            
            var obj = city.kladr('current');
            var value = $.trim(city.val());
            
            if(obj){
                name = obj.name;
                type = obj.typeShort + '.';
                if(obj.zip) zip = obj.zip;
            } else if(value){
                name = value;
                type = 'г.';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
            }
            
            // Улица
            name = null;
            type = null;            
            obj = street.kladr('current');
            value = $.trim(street.val());
            
            if(obj){
                name = obj.name;
                type = obj.typeShort + '.';
                if(obj.zip) zip = obj.zip;
            } else if(value){
                name = value;
                type = 'ул.';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
            }
            
            // Дом
            name = null;
            type = null;            
            obj = building.kladr('current');
            value = $.trim(building.val());
            
            if(obj){
                name = obj.name;
                type = 'д.';
                if(obj.zip) zip = obj.zip;
            } else if(value){
                name = value;
                type = 'д.';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
            }
            
            // Корпус
            name = null;
            type = null;            
            value = $.trim(buildingAdd.val());
            
            if(value){
                name = value;
                type = 'к.';
            }
            
            if(name){
                if(address) address += ', ';
                address += type + ' ' + name;
            }
            
            address = (zip ? zip + ', ' : '') + address;
            $('#address').text(address);
        }
        
        // Обновляет лог текущего выбранного объекта
        var Log = function(obj){
            var logId = $('#id');
            if(obj && obj.id){
                logId.find('.value').text(obj.id);
                logId.show();
            } else {
                logId.hide();
            }
            
            var logName = $('#name');
            if(obj && obj.name){
                logName.find('.value').text(obj.name);
                logName.show();
            } else {
                logName.hide();
            }
            
            var logZip = $('#zip');
            if(obj && obj.zip){
                logZip.find('.value').text(obj.zip);
                logZip.show();
            } else {
                logZip.hide();
            }
            
            var logType = $('#type');
            if(obj && obj.type){
                logType.find('.value').text(obj.type);
                logType.show();
            } else {
                logType.hide();
            }
            
            var logTypeShort = $('#type_short');
            if(obj && obj.typeShort){
                logTypeShort.find('.value').text(obj.typeShort);
                logTypeShort.show();
            } else {
                logTypeShort.hide();
            }
        }

        ymaps.ready(function(){
            if(map_created) return;
            map_created = true;

            map = new ymaps.Map('map', {
                center: [55.76, 37.64],
                zoom: 12
            });

            map.controls.add('smallZoomControl', { top: 5, left: 5 });
        });
    });
})(jQuery);