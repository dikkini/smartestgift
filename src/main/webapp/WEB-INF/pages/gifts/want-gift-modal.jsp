<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<fmt:requestEncoding value="utf-8"/>

<jsp:useBean id="now" class="java.util.Date" scope="page" />

<div class="modal fade" id="want-gift-modal" tabindex="-1" role="dialog"
     aria-labelledby="want-gift-modal-title" data-backdrop="static" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="want-gift-modal-title"></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-6">
                        <label for="end-date-input">Choose end date</label>
                        <input id="end-date-input" name="birthdate" class="form-control">
                    </div>
                    <div class="col-xs-6">
                        <label for="internet-shop-select">Choose price and internet shop</label>
                        <select id="internet-shop-select" class="form-control">
                            <option value="default" disabled="disabled">Choose shop and price</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Don't want</button>
                <button id="accept-want-gift-btn" type="button" class="btn btn-primary">Want it!</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function() {

    var endDateObj = $("#end-date-input");

    endDateObj.datepicker({
        minDate: "0",
        dateFormat: 'dd.mm.yy' // TODO вывести константу
    });

    endDateObj.prop('disabled', true);

    $("#internet-shop-select").change(function() {
        var price = parseInt($(this).find(':selected').data("price"));
        var priceIndex = Math.round((price / 3000));
        var maxDate;
        if (priceIndex <= 1) {
            maxDate = 1
        } else {
            maxDate = priceIndex;
        }
        maxDate = "+"+maxDate+"M";
        endDateObj.datepicker('option', 'maxDate', maxDate);
        endDateObj.prop('disabled', false);
    });

    $("#accept-want-gift-btn").click(function(e) {
        var endDate = endDateObj.val();
        var giftShopUuid = $("#internet-shop-select").val();


        // TODO date check (or it would be dont by field checker in a future world
        if ($.trim(endDate) == "" || giftShopUuid == "") {
            alert("wrong end date or shop");
            e.preventDefault();
            return;
        }

        $.ajax({
            type: "post",
            url: "/gifts/wantGift",
            cache: false,
            data: "giftShopUuid=" + giftShopUuid + "&endDate=" + endDate,
            success: function (response) {
                alert(response.success);
            },
            error: function (response) {
                window.location = "500";
            }
        });
    });
});
</script>