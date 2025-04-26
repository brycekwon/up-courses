$(document).ready(function() {
  $(".cheesecake-form").submit(function(event) {
    event.preventDefault();
    let quantity = $("#quantity").val();
    let notes = $("#notes").val();
    let toppings = $('input[name="topping"]:checked').val();
    alert(toppings);
    if (notes.includes("vegan")) {
      alert("Note: Cheesecakes contain dairy!");
    }

    $.post('/neworder', { quantity: quantity, notes: notes, topping: toppings }, function(data, textStatus) {
      console.log("FEF")
      
    }, "json");

    $(".cheesecake-form").replaceWith("<h2 class='cheesecake-form'>Thank you for your order!</h2>");
  });

});

