pub mod minichess;
use minichess::*;
use minichess::Color::*;

/// runs the application
///  * set up the board
///  * run the game
///  * print the result
fn main()
{
    //use this to test out your print function
    //print_board(start_board());

    let mut board = start_board();
    match run_game(&mut board)
    {
        White => {println!("\n\nWhite won!");}
        Black => {println!("\n\nBlack won!");}
    }
    print_board(&board);
}
