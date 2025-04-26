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

    // $("#form1").on('change', function() {
    //     $.post('/orders', function(data, textStatus) {
    //         if (data.data["error"] === null) {
    //             return;
    //         }
    //
    //         plain = `${data.data['1']['quantity']} ${data.data["1"]["topping"]} `
    //         $('#plain_stats').text(plain)
    //
    //         cherry = `${data.data['0']['quantity']} ${data.data["0"]["topping"]} `
    //         $('#cherry_stats').text(cherry)
    //
    //         choco = `${data.data['2']['quantity']} ${data.data["2"]["topping"]} `
    //         $('#chocolate_stats').text(choco)
    //
    //     }, "json");
    // });
});

