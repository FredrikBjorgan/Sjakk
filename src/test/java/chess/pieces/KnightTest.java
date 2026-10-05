package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class KnightTest {
    
    @Test 
    void knightCanMoveTwoRowsOneColumn(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(2,1);

         assertTrue(knight.isValidMove(from, to, board));
    }

     @Test 
    void knightCanMoveOneRowTwoColumns(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(1,2);

         assertTrue(knight.isValidMove(from, to, board));
    }

    @Test 
    void knightCannotMoveStraight(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(2,0);

         assertFalse(knight.isValidMove(from, to, board));
    }
    
    @Test 
    void knightCannotMoveDiagonally(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(2,2);

         assertFalse(knight.isValidMove(from, to, board));
    }

    @Test 
    void knightCannotStayOnSameSquare(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(0,0);

         assertFalse(knight.isValidMove(from, to, board));
    }

    @Test 
    void knightCanJumpOverPieces(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);
         Knight knight1 = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(2,1);
         Position friendly = new Position(1,0);

         board.setPiece(friendly, knight1);

         assertTrue(knight.isValidMove(from, to, board));
    }

    @Test 
    void knightCanCaptureOpponentPiece(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);
         Knight knight1 = new Knight(Colour.BLACK);

         Position from = new Position(0,0);
         Position to = new Position(2,1);
         Position opponent = new Position(2,1);

         board.setPiece(opponent, knight1);

         assertTrue(knight.isValidMove(from, to, board));
    }

    @Test 
    void knightCanCaptureOwnPiece(){

         Board board = new Board();
         Knight knight = new Knight(Colour.WHITE);
         Knight knight1 = new Knight(Colour.WHITE);

         Position from = new Position(0,0);
         Position to = new Position(2,1);
         Position friendly = new Position(2,1);

         board.setPiece(friendly, knight1);

         assertFalse(knight.isValidMove(from, to, board));
    }
}
