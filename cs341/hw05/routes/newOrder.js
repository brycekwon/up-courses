var express = require('express');
var router = express.Router();

var { dbquery } = require('../utils/dbms.js')
var mysql = require('mysql');

router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/', function(req, res, next) {
  let months = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
  dbquery(`SELECT MAX(ORDERID) FROM ORDERS`, (err, res) => {
    if (err) {
      console.log(err)
    }

    let id = res[0]['MAX(ORDERID)'] + 1;
    let month = months[Math.floor(Math.random() * months.length)]
    let day = Math.floor(Math.random() * 31);
    let quantity = req.body.quantity
    let notes = req.body.notes;
    let topping = req.body.topping;


    let sql = 'INSERT INTO ORDERS (ORDERID, MONTH, DAY, QUANTITY, TOPPING, NOTES) VALUES (?, ?, ?, ?, ?, ?)';
    let inserts = [id, month, day, quantity, topping, notes];
    sql = mysql.format(sql, inserts);

    // dbquery(`INSERT INTO ORDERS (ORDERID, MONTH, DAY, QUANTITY, TOPPING, NOTES) VALUES (${id}, "${month}", ${day}, ${quantity}, "${topping}", "${notes}")`, (err, res) => {
    dbquery(sql, (err, res) => {
      if (err) {
        console.log(err)
      }
      console.log(`INSERTED AT ID ${id}`)
    })
  })
});

module.exports = router;
