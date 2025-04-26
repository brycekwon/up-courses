pub mod hw3;
pub mod types;
use hw3::*;
use types::{var,num,add,sub,mul,div,neg};
use types::{nil, cons};
use types::{leaf, branch};

fn main()
{
    println!("Problem 1: {}", hello_world());
    println!("Problem 2: {}", concat(&cons(1, cons(2, nil())),
                                     &cons(3, cons(4, nil()))));
    println!("Problem 3: {}", reverse(&cons(1,cons(2,cons(3,cons(4,nil()))))));
    println!("Problem 4: {}", remove(&cons("a",cons("b",
                                      cons("b",cons("a",nil())))),"b"));
    println!("Problem 5: {}", example_tree());
    println!("Problem 6: {}", flip(&branch(1,leaf(2),leaf(3))));
    println!("Problem 7: {}", post_order(&branch(1,leaf(2),leaf(3))));
    println!("Problem 8: {}", make_exp());
    println!("Problem 9: {}", vars(&add(add(mul(var('a'),
                                           mul(var('x'),var('x'))),
                                           mul(var('b'),var('x'))),
                                       var('c'))));
    println!("Problem 10: {}", simplify(&neg(neg(num(3)))));
    println!("Problem 10: {}", simplify(&div(
                                        add(num(2),
                                        sub(var('x'),mul(
                                                div(var('y'),var('y')),
                                                var('x')))),
                                        num(2))));
}
