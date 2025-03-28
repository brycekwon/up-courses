var express = require('express');
var router = express.Router();

cheesecake_data =
{
    error: null,
    data: {
        "0": {
            "topping": "cherry",
            "quantity": "2",
        },
        "1": {
            "topping": "plain",
            "quantity": "6",
        },
        "2": {
            "topping": "chocolate",
            "quantity": "3",
        },
    },
},

router.get('/', function(req, res, next) {
    res.json(cheesecake_data)
});

router.post('/', function(req, res, next) {
    res.json(cheesecake_data)
});

module.exports = router;

