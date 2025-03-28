// jquery to update stats
$(document).ready(function() {
    $("#form1").on('change', function() {
        $.post('/orders', function(data, textStatus) {
            if (data.data["error"] === null) {
                return;
            }

            plain = `${data.data['1']['quantity']} ${data.data["1"]["topping"]} `
            $('#plain_stats').text(plain)

            cherry = `${data.data['0']['quantity']} ${data.data["0"]["topping"]} `
            $('#cherry_stats').text(cherry)

            choco = `${data.data['2']['quantity']} ${data.data["2"]["topping"]} `
            $('#chocolate_stats').text(choco)

        }, "json");
    });
});
