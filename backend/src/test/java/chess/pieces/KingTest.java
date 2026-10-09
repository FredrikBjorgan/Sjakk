package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KingTest {
    
    @Test 
    void kingCanMoveOneSquareVertically(){

        Board board = new Board();
        King king = new King(Colour.WHITE);

        Position from = new Position(0,4);
        Position to = new Position(1,4);

        assertTrue(king.isValidMove(from, to, board));

    }

    @Test 
    void kingCanMoveOneSquareHorizontally(){

        Board board = new Board();
        King king = new King(Colour.WHITE);

        Position from = new Position(0,4);
        Position to = new Position(0,5);

        assertTrue(king.isValidMove(from, to, board));

    }
    @Test 
    void kingCanMoveOneSquareDiagonally(){

        Board board = new Board();
        King king = new King(Colour.WHITE);

        Position from = new Position(0,4);
        Position to = new Position(1,5);

        assertTrue(king.isValidMove(from, to, board));

    }

    @Test 
    void kingCantMoveTwoSquare(){

        Board board = new Board();
        King king = new King(Colour.WHITE);

        Position from = new Position(0,4);
        Position to = new Position(0,6);

        assertFalse(king.isValidMove(from, to, board));

    }

    @Test 
    void kingCantStayOnSameSquare(){

        Board board = new Board();
        King king = new King(Colour.WHITE);

        Position from = new Position(0,4);
        Position to = new Position(0,4);

        assertFalse(king.isValidMove(from, to, board));

    }

     @Test 
    void kingCanCaptureOpponentPiece(){

        Board board = new Board();
        King king = new King(Colour.WHITE);
        Bishop bishop = new Bishop(Colour.BLACK);

        Position from = new Position(0,4);
        Position to = new Position(0,5);

        board.setPiece(to, bishop);

        assertTrue(king.isValidMove(from, to, board));
    }

    @Test 
    void kingCanCaptureOwnPiece(){

        Board board = new Board();
        King king = new King(Colour.WHITE);
        Bishop bishop = new Bishop(Colour.WHITE);

        Position from = new Position(0,4);
        Position to = new Position(0,5);

        board.setPiece(to, bishop);

        assertFalse(king.isValidMove(from, to, board));
    }
}


