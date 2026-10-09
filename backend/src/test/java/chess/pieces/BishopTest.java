package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BishopTest {

    @Test 
    void bishopCanMoveDiagonally(){

        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(3,3);

        assertTrue(bishop.isValidMove(from, to, board));
    }

    @Test 
    void bishopCanMoveDiagonallyInOppositWay(){

        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);

        Position from = new Position(7,7);
        Position to = new Position(3,3);

        assertTrue(bishop.isValidMove(from, to, board));
    }

    @Test
    void bishopCantMoveHorizontaly(){

        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(0,3);

        assertFalse(bishop.isValidMove(from, to, board));

    }

    @Test
    void bishopCantMoveVertically(){

        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(3,0);

        assertFalse(bishop.isValidMove(from, to, board));

    }

    @Test 
    void bishopCantMoveToSameSquare(){

        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);

        Position from = new Position(0,0);
        Position to = new Position(0,0);

        assertFalse(bishop.isValidMove(from, to, board));

    }

    @Test 
    void bishopCantCaptureOverPiece(){
        
        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);
        Bishop ownPiece = new Bishop(Colour.WHITE);
        Bishop oponent = new Bishop(Colour.BLACK);

        Position from = new Position(0,0);
        Position friendly = new Position(3,3);
        Position target = new Position(6,6);

        board.setPiece(from, bishop);
        board.setPiece(friendly, ownPiece);
        board.setPiece(target, oponent);

        assertFalse(bishop.isValidMove(from, target, board));
    }

     @Test 
    void bishopCantJumpOverPiece(){
        
        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);
        Bishop ownPiece = new Bishop(Colour.WHITE);

        Position from = new Position(0,0);
        Position friendly = new Position(3,3);
        Position target = new Position(7,7);

        board.setPiece(from, bishop);
        board.setPiece(friendly, ownPiece);

        assertFalse(bishop.isValidMove(from, target, board));
    }

    @Test 
    void bishopCannotCaptureOwnPiece(){

        Board board = new Board();

        Bishop bishop = new Bishop(Colour.WHITE);
        Bishop ownPiece = new Bishop(Colour.WHITE);

        Position from = new Position(0, 0);
        Position target = new Position(3, 3);

        board.setPiece(target, ownPiece);

        assertFalse(bishop.isValidMove(from, target, board));
    }

    @Test 
    void bishopCanCaptureOpponentPiece() {

        Board board = new Board();

        Bishop biship = new Bishop(Colour.WHITE);
        Bishop opponentPiece = new Bishop(Colour.BLACK);

        Position from = new Position(0, 0);
        Position target = new Position(3, 3);

        board.setPiece(target, opponentPiece);

        assertTrue(biship.isValidMove(from, target, board));
    }
}
