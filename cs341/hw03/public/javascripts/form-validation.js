$(document).ready(function() {
    $(".cheesecake-form").submit(function(event) {
        event.preventDefault();
        let notes = $("#notes").val();
        let toppings = $("topping").val();
        alert(toppings);
        if (notes.includes("vegan")) {
            alert("Note: Cheesecakes contain dairy!");
        }
                
        $(".cheesecake-form").replaceWith("<h2 class='cheesecake-form'>Thank you for your order!</h2>");
    });

});
