package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueenTest {

    @Test 
    void queenCanMoveHorizontally(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(0,4);

        assertTrue(queen.isValidMove(from, to, board));
    }

    @Test 
    void queenCanMoveVertically(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(4,0);

        assertTrue(queen.isValidMove(from, to, board));
    }

    @Test 
    void queenCanMoveDiagonally(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(4,4);

        assertTrue(queen.isValidMove(from, to, board));
    }

    @Test 
    void queenCantJumpOverPiece(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);
        Queen queen2 = new Queen(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(0,4);
        Position friendly = new Position(0,2);

        board.setPiece(friendly, queen2);

        assertFalse(queen.isValidMove(from, to, board));
    }

     @Test 
    void queenCanCapturePiece(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);
        Queen queen2 = new Queen(Colour.BLACK);

        Position from = new Position(0,0);
        Position to = new Position(0,4);

        board.setPiece(to, queen2);

        assertTrue(queen.isValidMove(from, to, board));
    }

    @Test 
    void queenCantStayOnSameSquare(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(0,0);

        assertFalse(queen.isValidMove(from, to, board));
    }

    @Test 
    void queenCantJumpOverOppontentPiece(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);
        Queen queen2 = new Queen(Colour.BLACK);

        Position from = new Position(0,0);
        Position to = new Position(0,4);
        Position friendly = new Position(0,2);

        board.setPiece(friendly, queen2);

        assertFalse(queen.isValidMove(from, to, board));
    }
    
    @Test 
    void queenCantCaptureOwnPiece(){

        Board board = new Board();
        Queen queen = new Queen(Colour.WHITE);
        Queen queen2 = new Queen(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(0,4);

        board.setPiece(to, queen2);

        assertFalse(queen.isValidMove(from, to, board));
    }
    
}
