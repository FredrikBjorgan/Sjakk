package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RookTests {

    @Test
    void rookCanMoveVertically() {

        Board board = new Board();
        Rook rook = new Rook(Colour.WHITE);

        Position from = new Position(0, 0);
        Position to = new Position(5, 0);

        assertTrue(rook.isValidMove(from, to, board));
    
    }

    @Test
    void rookCanMoveHorizontally() {

        Board board = new Board();
        Rook rook = new Rook(Colour.WHITE);

        Position from = new Position(0, 0);
        Position to = new Position(3, 3);

        assertFalse(rook.isValidMove(from, to, board));
    }

    @Test
    void rookCannotJumpOverAnotherPiece(){

        Board board = new Board();

        Rook rook = new Rook(Colour.WHITE);
        Rook blockingPiece = new Rook(Colour.WHITE);

        Position from = new Position(0, 0);
        Position blocker = new Position(3, 0);
        Position to = new Position(5, 0);

        board.setPiece(blocker, blockingPiece);

        assertFalse(rook.isValidMove(from, to, board));
    }

    @Test
    void rookCannotStayInSamePosition() {

        Board board = new Board();
        Rook rook = new Rook(Colour.WHITE);

        Position from = new Position(0, 0);

        assertFalse(rook.isValidMove(from, from, board));

    }

    @Test 
    void rookCanCaptureOpponentPiece() {

        Board board = new Board();

        Rook rook = new Rook(Colour.WHITE);
        Rook opponentPiece = new Rook(Colour.BLACK);

        Position from = new Position(0, 0);
        Position target = new Position(5, 0);

        board.setPiece(target, opponentPiece);

        assertTrue(rook.isValidMove(from, target, board));
    }

    @Test 
    void rookCannotCaptureOwnPiece(){

        Board board = new Board();

        Rook rook = new Rook(Colour.WHITE);
        Rook ownPiece = new Rook(Colour.WHITE);

        Position from = new Position(0, 0);
        Position target = new Position(5, 0);

        board.setPiece(target, ownPiece);

        assertFalse(rook.isValidMove(from, target, board));
    }

    @Test 
    void rookCannotJumpOverPiece(){

        Board board = new Board();

        Rook rook = new Rook(Colour.WHITE);
        Rook ownPiece = new Rook(Colour.WHITE);
        Rook oponent = new Rook(Colour.BLACK);

        Position from = new Position(0,0);
        Position friendly = new Position(0,3);
        Position target = new Position(0,5);

        board.setPiece(from, rook);
        board.setPiece(friendly, ownPiece);
        board.setPiece(target, oponent);

        assertFalse(rook.isValidMove(from, target, board));

    }

}