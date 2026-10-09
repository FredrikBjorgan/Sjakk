package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PawnTest {
    
    @Test
    void pawnCanMoveOneSquareForwardWhite(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position from = new Position(1,0);
        Position to = new Position(2, 0);

        assertTrue(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCantMoveThreeSquareForwardWhite(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position from = new Position(1,0);
        Position to = new Position(4, 0);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCanMoveTwoSquareForwardFromStartWhite(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position from = new Position(1,0);
        Position to = new Position(3, 0);

        assertTrue(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCantMoveOneSquareBackwoardsWhite(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position from = new Position(4,0);
        Position to = new Position(3, 0);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCannotMoveTwoSquaresAfterLeavingStartingPositionWhite(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position from = new Position(2,0);
        Position to = new Position(4, 0);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCanMoveOneSquareForwardBlack(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.BLACK);

        Position from = new Position(7,0);
        Position to = new Position(6, 0);

        assertTrue(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCantMoveThreeSquareForwardBlack(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.BLACK);

        Position from = new Position(7,0);
        Position to = new Position(4, 0);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCanMoveTwoSquareForwardFromStartBlack(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.BLACK);

        Position from = new Position(6,0);
        Position to = new Position(4, 0);

        assertTrue(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCantMoveOneSquareBackwoardsBlack(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.BLACK);

        Position from = new Position(4,0);
        Position to = new Position(5, 0);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test
    void pawnCannotMoveTwoSquaresAfterLeavingStartingPositionBlack(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.BLACK);

        Position from = new Position(5,0);
        Position to = new Position(3, 0);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test 
    void pawnCannotMoveHorizontally(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position from = new Position(2,0);
        Position to = new Position(2, 1);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test 
    void pawnCannotMoveForwardIntoOccupiedSquare(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);
        Pawn pawn2 = new Pawn(Colour.WHITE);

        Position from = new Position(3,0);
        Position occupied = new Position(4,0);
        Position to = new Position(4, 0);

        board.setPiece(occupied, pawn2);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test 
    void pawnCanCaptureOpponentDiagonally(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);
        Pawn pawn2 = new Pawn(Colour.BLACK);

        Position from = new Position(3,0);
        Position to = new Position(4, 1);

        board.setPiece(to, pawn2);

        assertTrue(pawn.isValidMove(from, to, board));
    }

    @Test 
    void pawnCannotCaptureOwnPieceDiagonally(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);
        Pawn pawn2 = new Pawn(Colour.WHITE);

        Position from = new Position(3,0);
        Position to = new Position(4, 1);

        board.setPiece(to, pawn2);

        assertFalse(pawn.isValidMove(from, to, board));
    }

    @Test 
    void pawnCannotMoveDiagonallyToEmptySquare(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);

        Position form = new Position(3,0);
        Position to = new Position(4,1);

        assertFalse(pawn.isValidMove(form, to, board));
    }

    @Test 
    void pawnCannotJumpOverPieceOnTwoSquareMove(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.WHITE);
        Pawn pawn2 = new Pawn(Colour.WHITE);

        Position from = new Position(1,0);
        Position to = new Position(3, 0);
        Position occupoied = new Position(2,0);

        board.setPiece(occupoied, pawn2);

        assertFalse(pawn.isValidMove(from, to, board));

    }

    @Test 
    void pawnCannotStayOnSameSquare(){

        Board board = new Board();

        Pawn pawn = new Pawn(Colour.WHITE);

        Position form = new Position(2,0);
        Position to = new Position(2,0);

        assertFalse(pawn.isValidMove(form, to, board));

    }

    @Test 
    void pawnCanCaptureOpponentDiagonallyBlack(){

        Board board = new Board();
        Pawn pawn = new Pawn(Colour.BLACK);
        Pawn pawn2 = new Pawn(Colour.WHITE);

        Position from = new Position(4,0);
        Position to = new Position(3, 1);

        board.setPiece(to, pawn2);

        assertTrue(pawn.isValidMove(from, to, board));
    }

}
