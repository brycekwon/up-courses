use std::io::{stdin,stdout, Write};
use std::error::Error;
use std::fmt::{Display,Formatter};
use std::fmt;
use std::iter::repeat;

/////////////////////////////////////////////
//
// Data structres used in Chess
//
/////////////////////////////////////////////

/// A piece is either
///  * Pawn: can move forward 1 space, or capture diagonally forward 1 space
///  * Rook: can move or capture in cardinal directions
///  * Knight: can move in an L shape
///  * Bishop: can move or capture  diagonally
///  * Queen: can move or capture cardianlly or diagonally
///  * King: cam move or caputre 1 space diagonally or cardinally
#[derive(PartialEq, Clone, Copy)]
pub enum Piece { Pawn, Rook, Knight, Bishop, Queen, King }

/// A Color is either white or black.
#[derive(PartialEq, Clone, Copy)]
pub enum Color { White, Black }

/// A square can either be a white piece, a black piece, or an empty board.
#[derive(PartialEq, Clone, Copy)]
pub enum Square { Full(Color,Piece), Empty }

/// A Chess Board board is a 2d vector of pieces
type Board = Vec<Vec<Square>>;

type Pos = (usize,usize);

/// A move on the board. we move from one square to a new square.
#[derive(PartialEq, Clone, Copy)]
pub struct Move
{
    from : Pos,
    to   : Pos
}

use Square::*;
use Color::*;
use Piece::*;

/// A helper funtion to check the other player.
fn other(c : Color) -> Color
{
    match c
    {
        White => Black,
        Black => White
    }
}

/// returns the color of a Square
/// if the piece is and empty square, then return None
fn color(p : Square) -> Option<Color>
{
    match p 
    {
        Empty         => None,
        Full(White,_) => Some(White),
        Full(Black,_) => Some(Black)
    }
}

/// returns what Piece of a piece it is
/// if the piece is empty, return None.
fn piece(p : Square) -> Option<Piece>
{
    match p 
    {
        Empty          => None,
        Full(_,Pawn)   => Some(Pawn),
        Full(_,Rook)   => Some(Rook),
        Full(_,Knight) => Some(Knight),
        Full(_,Bishop) => Some(Bishop),
        Full(_,Queen)  => Some(Queen),
        Full(_,King)   => Some(King)
    }
}

/////////////////////////////////////////////
//
// Code for printing out the board
//
/////////////////////////////////////////////

/// Prints out the Connect 4 board
/// The format of the board should be
///
///    A B C D E F G H
///   +---------------+
/// 8 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 7 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 6 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 5 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 4 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 3 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 2 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 1 | | | | | | | | |
///   +---------------+
///
/// We print out the white and black pieces with a letter for each piece
/// White letter are upper case and black letters are lower case.
/// type   : white black
/// Pawn   : P     p
/// Rook   : R     r
/// Knight : N     n
/// Bishop : B     b
/// Queen  : Q     q
/// King   : K     k
///
/// # Arguments
/// 
/// * board - The board to print out
pub fn print_board(board : &Board)
{
    // Part 1: 10pts.
    // print out the board.
    // It should be formatted like the comment above.
    println!("   A B C D E F G H");
    println!("  +---------------+");

    for i in 0..board.len() {
        print!("{} |", i+1);
        for j in 0..board[i].len() {
            print!("{}|", piece_str(board[i][j]));
        }
        if i == board.len() - 1 { 
            println!("\n  +---------------+");
            continue;
        }
        println!("\n  |-+-+-+-+-+-+-+-|");
    }
}


/// helper function to get the representation of a piece.
fn piece_str(p : Square) -> String
{
    match p
    {
        Empty              => " ".to_string(),
        Full(White,Pawn)   => "P".to_string(),
        Full(White,Rook)   => "R".to_string(),
        Full(White,Knight) => "N".to_string(),
        Full(White,Bishop) => "B".to_string(),
        Full(White,Queen)  => "Q".to_string(),
        Full(White,King)   => "K".to_string(),
        Full(Black,Pawn)   => "p".to_string(),
        Full(Black,Rook)   => "r".to_string(),
        Full(Black,Knight) => "n".to_string(),
        Full(Black,Bishop) => "b".to_string(),
        Full(Black,Queen)  => "q".to_string(),
        Full(Black,King)   => "k".to_string()
    }
}

/// creates a new board for to start the game.
///
/// print(start_board()) should print out
///    A B C D E F G H
///   +---------------+
/// 8 |r|n|b|q|k|b|n|r|
///   |-+-+-+-+-+-+-+-|
/// 7 |p|p|p|p|p|p|p|p|
///   |-+-+-+-+-+-+-+-|
/// 6 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 5 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 4 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 3 | | | | | | | | |
///   |-+-+-+-+-+-+-+-|
/// 2 |P|P|P|P|P|P|P|P|
///   |-+-+-+-+-+-+-+-|
/// 1 |R|N|B|Q|K|B|N|R|
///   +---------------+
pub fn start_board() -> Board
{
    let e = Empty;
    let wp = Full(White,Pawn);
    let wr = Full(White,Rook);
    let wn = Full(White,Knight);
    let wb = Full(White,Bishop);
    let wq = Full(White,Queen);
    let wk = Full(White,King);
    let bp = Full(Black,Pawn);
    let br = Full(Black,Rook);
    let bn = Full(Black,Knight);
    let bb = Full(Black,Bishop);
    let bq = Full(Black,Queen);
    let bk = Full(Black,King);
    vec![vec![br, bn, bb, bq, bk, bb, bn, br],
         vec![bp, bp, bp, bp, bp, bp, bp, bp],
         vec![ e,  e,  e,  e,  e,  e,  e,  e],
         vec![ e,  e,  e,  e,  e,  e,  e,  e],
         vec![ e,  e,  e,  e,  e,  e,  e,  e],
         vec![ e,  e,  e,  e,  e,  e,  e,  e],
         vec![wp, wp, wp, wp, wp, wp, wp, wp],
         vec![wr, wn, wb, wq, wk, wb, wn, wr]]
}

/////////////////////////////////////////////
//
// Main game logic
//
/////////////////////////////////////////////


/// Runs the Chess game, 
/// and returns the winning player.
/// Return a White(King) if White wins and a Black(King) if Black wins.
/// There are no draws.
///
/// A game is played with two players.
///  * For each turn we ask the player to take a turn, 
///    and update the board
///  * Next we check for a winner.
///  * If that player won, then we return their piece.
///  * If they didn't win, then play passes to the next player.
///
/// # Arguments
/// 
/// * board - the state of the board
///
/// Notes: see if you can do this without repeating your code
/// for White and Black.
pub fn run_game(board: &mut Board) -> Color {
    // Part 2: 10 pts
    // Write the code for running the game.
    // White should start,
    // then take a turn (which should check for a winner)
    // If there wasn't a winner, then moove to the next players turn.

    let mut winner: Option<Color>;
    let mut player_turn: Color = Color::White;  // white always moves first in chess

    loop {
        winner = take_turn(board, player_turn);

        // check for winner after each turn
        match winner {
            None => { player_turn = other(player_turn); }
            Some(White) => { return White; }
            Some(Black) => { return Black; }
        }
    }
}

/// Try to make a move,
/// If the user fails to enter a valid move,
/// then print the error and ask the user again.
///
/// When the user enters a valid move,
/// then update the board,
/// and return if there was a winner.
fn take_turn(board: &mut Board, player: Color) -> Option<Color> {
    // Part 3: 10pts.
    // write code for a players turn.
    // For each turn 
    //  * print the board
    //  * get a move from the player.
    //      You can use the try_move function for that.
    //  * check that the move is actually valid.
    //  * if it is, update the board.
    //
    // If there is an error (either from try_move or validate_move)
    // then print the error to the screen and try again.
    
    print_board(board);

    let mut player_move: Result<Move, GameError>;

    loop {
        // prompt for move
        player_move = try_move();
        match player_move {
            Ok(_move) => {
                // validate move
                let check_move: Result<(), GameError> = validate_move(board, player, _move);
                match check_move {
                    Ok(_valid) => {
                        board[_move.to.0][_move.to.1] = board[_move.from.0][_move.from.1];
                        board[_move.from.0][_move.from.1] = Square::Empty;

                        let is_winner: Option<Color> = check_winner(board);
                        match is_winner {
                            None => { return None; }
                            Some(White) => { return Some(White); }
                            Some(Black) => { return Some(Black); }
                        }
                    }
                    Err(_errmsg) => {
                        println!("{}", _errmsg);
                    }
                }
            }
            Err(_errmsg) => {
                println!("{}", _errmsg);
            }
        }
    }
}

/// This function check if a move is valid on the current board.
pub fn validate_move(board: &Board, player: Color, m: Move) -> Result<(),GameError> {
    let x0: i32 = m.from.1 as i32;
    let y0: i32 = m.from.0 as i32;
    let x1: i32 = m.to.1 as i32;
    let y1: i32 = m.to.0 as i32;

    let start_loc = board[m.from.0][m.from.1];

    let piece_color: Option<Color> = color(start_loc);
    match piece_color {
        None => { return Err(GameError { msg : "There's no piece there!".to_string() }); }
        Some(Color::White) => {
            if player != Color::White { return Err(GameError { msg : "You can't move a piece that isn't yours!".to_string() }); }
        }
        Some(Color::Black) => {
            if player != Color::Black { return Err(GameError { msg : "You can't move a piece that isn't yours!".to_string() }); }
        }
    }

    let moving_piece: Option<Piece> = piece(start_loc);
    match moving_piece {
        None => {
            return Err(GameError { msg : "There's no piece there!".to_string() });
        }
        Some(Piece::Pawn) => {
            match piece_color {
                None => {
                    return Err(GameError { msg : "There's no piece there!".to_string() });
                }
                Some(Color::White) => {
                    if (y0 - 1) == y1 { return Ok(()); }
                    else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                }
                Some(Color::Black) => {
                    if (y0 + 1) == y1 { return Ok(()); }
                    else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                }
            }
        }
        Some(Piece::Rook) => {
            let mut blocking_piece: Square;
             if ((x0 - x1) == 0) && (y0 < y1) {
                for i in y0..y1 {
                    blocking_piece = board[i as usize][x0 as usize];
                    match blocking_piece {
                        Square::Empty => {
                            continue;
                        }
                        Square::Full(_,_) => {
                            if (i + 1) == x1 { return Ok(()); }
                            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                        }
                    }
                }
                return Ok(());
            } else if (y0 - y1) == 0 {
                for i in x0..x1 {
                    blocking_piece = board[i as usize][y0 as usize];
                    match blocking_piece {
                        Square::Empty => {
                            continue;
                        }
                        Square::Full(_,_) => {
                            if (i + 1) == y1 { return Ok(()); }
                            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                        }
                    }
                }
                return Ok(());
            } else {
                return Err(GameError { msg : "You can't move there!".to_string() });
            }
        }
        Some(Piece::Knight) => {
            if (y0 - 2 == y1) && (x0 - 1 == x1)         { return Ok(()); }
            else if (y0 - 2 == y1) && (x0 + 1 == x1)    { return Ok(()); }
            else if (y0 + 2 == y1) && (x0 - 1 == x1)    { return Ok(()); }
            else if (y0 + 2 == y1) && (x0 + 1 == x1)    { return Ok(()); }
            else if (y0 + 1 == y1) && (x0 - 2 == x1)    { return Ok(()); }
            else if (y0 + 1 == y1) && (x0 + 2 == x1)    { return Ok(()); }
            else if (y0 - 1 == y1) && (x0 - 2 == x1)    { return Ok(()); }
            else if (y0 - 1 == y1) && (x0 + 2 == x1)    { return Ok(()); }
            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
        }
        Some(Piece::Bishop) => {
            if (x1-x0 == y1-y0) || (x1-x0 == -(y1-y0)) {

                let mut x: i32;
                let mut y: i32;
                let distance: i32 = (x0 - x1).abs();

                for _i in 1..(distance + 1) {
                    if x0 > x1 { x = x0 - distance }
                    else { x = distance - x0 }
                    
                    if y0 > y1 { y = y0 - distance }
                    else { y = distance - y0 }
                    
                    let block: Square = board[x as usize][y as usize];
                    match block {
                        Empty => {
                            continue;
                        }
                        Full(_,_) => {
                            if (x == x1) && (y == y1) { return Ok(()); }
                            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                        }
                    }
                }
                return Ok(());
            } else {
                return Err(GameError { msg : "You can't move there!".to_string() });
            }
        }
        Some(Piece::Queen) => {
            let mut blocking_piece: Square;
            if (x0 - x1) == 0 {
                for i in y0..y1 {
                    blocking_piece = board[x0 as usize][i as usize];
                    match blocking_piece {
                        Square::Empty => {
                            continue;
                        }
                        Square::Full(_,_) => {
                            if (i + 1) == x1 { return Ok(()); }
                            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                        }
                    }
                }
                return Ok(());
            } else if (y0 - y1) == 0 {
                for i in x0..x1 {
                    blocking_piece = board[i as usize][y0 as usize];
                    match blocking_piece {
                        Square::Empty => {
                            continue;
                        }
                        Square::Full(_,_) => {
                            if (i + 1) == y1 { return Ok(()); }
                            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                        }
                    }
                }
                return Ok(());
            } else if (x1-x0 == y1-y0) || (x1-x0 == -(y1-y0)) {

                let mut x: i32;
                let mut y: i32;
                let distance: i32 = (x0 - x1).abs();

                for _i in 1..(distance + 1) {
                    if x0 > x1 { x = x0 - distance }
                    else { x = distance - x0 }
                    
                    if y0 > y1 { y = y0 - distance }
                    else { y = distance - y0 }
                    
                    let block: Square = board[x as usize][y as usize];
                    match block {
                        Empty => {
                            continue;
                        }
                        Full(_,_) => {
                            if (x == x1) && (y == y1) { return Ok(()); }
                            else { return Err(GameError { msg : "You can't move there!".to_string() }); }
                        }
                    }
                }
                return Ok(());
            } else {
                return Err(GameError { msg : "You can't move there!".to_string() });
            }
        }
        Some(Piece::King) => {
            if x1 < (x0 - 1) || x1 > (x0 + 1)           { return Err(GameError { msg : "Can't Move There".to_string() }); }
            else if y1 < (y0 - 1) || y1 > (y0 + 1)      { return Err(GameError { msg : "Can't Move There".to_string() }); }
            else { return Ok(()); }
        }
    }
}

// The range function is the one we saw in class,
// but I've added another case.
// If x1 and x2 are the same number,
// then we return an infinite iterator of x1.
// This seems useless, but there's a neat trick we can do.
//
// If I want to iterate over two things at the same time,
// I can use the zip method to pair up each of the elements of the iterator.
// for (x,y) in (1..4).zip(5..8)
// loops over the pairs (1,5), (2,6), (3,7).
//
// I've also modified this from the version we saw in class, so it doesn't
// include the lowest number.
// range(3, 8) iterates over 4, 5, 6, and 7.
//
// Don't worry about the return type.
// Box means that it's a pointer
// dyn means that it's a dynamic Trait 
// (It could be one of many types of iterators)
// Iterator is a generic iterator object
// and Item is the type of thing we're iterating over.
// All together this means that we can stick this in a for loop.
fn range(x1 : usize, x2 : usize) -> Box<dyn Iterator<Item = usize>>
{
    if x1 < x2      {return Box::new(x1+1..x2);}
    else if x2 < x1 {return Box::new(x2+1..x1);}
    else            {return Box::new(repeat(x1));}
}

/// check to see if there's a winner
/// If there is a winner, then return the player that won,
/// Otherwise return None.
///
/// # Arguments
///
/// * board - the state of the board
fn check_winner(board : &Board) -> Option<Color> {
    //Part 10: check to see if the board has a winner.
    //We aren't doing anything fancy like check mate,
    //so White wins if Black doesn't have a King on the board
    //and vice versa.

    let mut wking: i32 = 0;
    let mut bking: i32 = 0;

    for i in 0..8 {
        for j in 0..8 {
            let king: Square = board[i as usize][j as usize];
            match king {
                Empty => {
                    continue;
                }
                Full(White,King) => {
                    wking += 1;
                }
                Full(Black,King) => {
                    bking += 1;
                }
                Full(_,_) => {
                    continue;
                }
            }
        }
    }

    if wking > bking {
        return Some(White);
    } else if bking > wking {
        return Some(Black);
    } else {
        return None;
    }
}

/////////////////////////////////////////////
//
// Code to ask for, and parse, a player's move
//
/////////////////////////////////////////////

/// Ask the user to input a move,
/// and check that the move is valid.
/// Return the move if it's valid.
/// Return a GameError on faliure.
fn try_move() -> Result<Move, GameError>
{
    let line = ask_for_move()?;
    return parse_move(&line);
}

/// Ask the user for a move, retun the move is possible
fn ask_for_move() -> Result<String, GameError>
{
    print!("Make a move: ");
    stdout().flush().unwrap();
    let mut line = String::new();
    match stdin().read_line(&mut line)
    {
        Ok(_)  => Ok(line.trim().to_string()),
        Err(e) => Err(game_error(&e.to_string()))
    }
}

/// A move has the form
/// "A3 C5"
/// It means move piece at square A3 to square C5
/// This function only checks that the move has the right form
/// not that it's a valid move for the board.
///
/// # Arguments
///
/// * m - the string for the move
///
fn parse_move(m : &str) -> Result<Move, GameError>
{
    let c1 = m.as_bytes()[0] as char;
    let r1 = m.as_bytes()[1] as char;
    let c2 = m.as_bytes()[3] as char;
    let r2 = m.as_bytes()[4] as char;
    return Ok(Move{from : parse_square(r1,c1)?,
                   to   : parse_square(r2,c2)?});
}

fn parse_square(row : char, col : char) -> Result<Pos, GameError>
{
    let r = match row
            {
                '1' => Ok(0), 
                '2' => Ok(1), 
                '3' => Ok(2), 
                '4' => Ok(3), 
                '5' => Ok(4), 
                '6' => Ok(5), 
                '7' => Ok(6), 
                '8' => Ok(7), 
                _   => Err(game_error("row must be one of 1-8"))
            }?;
    let c = match col
            {
                'A' => Ok(0), 
                'B' => Ok(1), 
                'C' => Ok(2), 
                'D' => Ok(3), 
                'E' => Ok(4), 
                'F' => Ok(5), 
                'G' => Ok(6), 
                'H' => Ok(7), 
                _   => Err(game_error("column must be one of A-H"))
            }?;
    return Ok((r,c));
}


//////////////////////////////////////////////
//
// Game Error code: You don't need to worry about this.
//
// you can print a GameError (ge) with
// println!("{}", ge);
//
//////////////////////////////////////////////

/// A GameError is an error with Tic Tac Toe
#[derive(Debug, Clone)]
pub struct GameError { msg : String }

/// make a new game error
fn game_error(err : &str) -> GameError
{
    GameError{msg: err.to_string()}
}

impl Display for GameError
{
    fn fmt(&self, f: &mut Formatter) -> fmt::Result
    {
        write!(f,"Game Error: {}",self.msg)
    }
}

impl Error for GameError
{
    fn description(&self) -> &str
    {
        &self.msg
    }
}

