var express = require('express');
var router = express.Router();

var { dbquery } = require('../utils/dbms.js');

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
  
  let stats = function() {
    return new Promise(function(resolve, reject) {
      let data = {
        error: null,
        data: {
          "0": {
            "topping": "cherry",
            "quantity": 0,
          },
          "1": {
            "topping": "plain",
            "quantity": 0,
          },
          "2": {
            "topping": "chocolate",
            "quantity": 0,
          },
        }
      }
      let month = req.body.topping.substring(0, 3).toUpperCase();
      console.log(month)

      let queries = [
        new Promise((resolve, reject) => {
          dbquery(`SELECT QUANTITY FROM ORDERS WHERE MONTH="${month}" AND TOPPING="Cherry"`, (err, res) => {
            if (err) {
              console.log(err);
              reject(err);
            }

            let quantity = 0;
            for (let i = 0; i < res.length; i++) {
              quantity += res[i].QUANTITY;
            }

            data.data["0"].quantity = quantity.toString();
            resolve();
          })
        }),
        new Promise((resolve, reject) => {
          dbquery(`SELECT QUANTITY FROM ORDERS WHERE MONTH="${month}" AND TOPPING="Plain"`, (err, res) => {
            if (err) {
              console.log(err);
              reject(err);
            }

            let quantity = 0;
            for (let i = 0; i < res.length; i++) {
              quantity += res[i].QUANTITY;
            }

            data["data"]["1"]["quantity"] = quantity.toString();
            resolve();
          })
        }),
        new Promise((resolve, reject) => {
          dbquery(`SELECT QUANTITY FROM ORDERS WHERE MONTH="${month}" AND TOPPING="Chocolate"`, (err, res) => {
            if (err) {
              console.log(err);
              reject(err);
            }

            let quantity = 0;
            for (let i = 0; i < res.length; i++) {
              quantity += res[i].QUANTITY;
            }

            data["data"]["2"]["quantity"] = quantity.toString();
            resolve();
          })
        })
      ];

      Promise.all(queries).then(() => {
        resolve(data);
      }).catch((err) => {
        reject(err);
      });
    })
  }
  
  stats().then((data) => {
    res.json(data);
  }).catch((err) => {
    res.status(500).send(err);
  });

});


module.exports = router;

