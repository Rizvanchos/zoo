function addToCart(ticketId, quantity) {
    $.post("/cart/additem", {
            ticketId: ticketId,
            quantity: quantity
        },
        function () {
            refreshCartData();
            $.toast('Item added to Cart', 3000);
        }
    ).fail(function () {
        alert("Failed to add item");
    });
}

function updateCartItem(ticketId, newQuantity) {
    $.post(
        "/cart/setitem",
        {
            ticketId: ticketId,
            newQuantity: newQuantity
        },
        function () {
            refreshCartData();
        }
    ).fail(function () {
        alert("Failed to update item");
    });
}

function removeCartItem(ticketId) {
    $.post("/cart/removeitem", {
            ticketId: ticketId
        },
        function () {
            refreshCartData();
        }
    ).fail(function () {
        alert("Failed to remove item");
    });
}

function clearCart() {
    $.post("/cart/clear", {}, function () {
            refreshCartData();
        }
    );
}


function checkoutCart() {
    $("#addToCartModal").modal('toggle');
    window.location.href = "/checkout/"
}


function refreshCartData() {

    $.get("/cart", function (data) {
        var element = $("#addToCartModal .modal-body");
        element.html(data);

        var btnCart = $("#btnCart");
        var badgeCart = $("#badgeCart");

        var btnCheckout = $("#addToCartModal #btnCheckout ");
        var btnClear = $("#addToCartModal #btnClear ");

        var cartModalBody = element[0].children[0];
        if (cartModalBody.tagName == "TABLE") {

            btnCheckout.removeClass("disabled");
            btnClear.removeClass("disabled");

            if (btnCart.hasClass("btn-default"))
                btnCart.removeClass("btn-default");

            btnCart.addClass("btn-success");

            var cartModalTableBody = cartModalBody.children[1];
            badgeCart.html(cartModalTableBody.children.length);
            badgeCart.removeAttr('hidden');
            badgeCart.show();
        }
        else {
            btnCheckout.addClass("disabled");
            btnClear.addClass("disabled");

            if (btnCart.hasClass("btn-success"))
                btnCart.removeClass("btn-success");

            btnCart.addClass("btn-default");

            badgeCart.hide();
        }
    });
}

$(document).ready(function () {
    refreshCartData();
});