package chess.rules;

import chess.Board;
import chess.Colour;
import chess.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveValidatorTest {

    @Test
    void rookAttacksSquare() {

        Board board = new Board();
        MoveValidator validator = new MoveValidator();

        Rook rook = new Rook(Colour.BLACK);

        Position rookPosition = new Position(7, 0);
        Position target = new Position(3, 0);

        board.setPiece(rookPosition, rook);

        assertTrue(
            validator.isSquareUnderAttack(
                board,
                target,
                Colour.BLACK
            )
        );
    }


    @Test
    void rookDoesNotAttackThroughPiece() {

        Board board = new Board();
        MoveValidator validator = new MoveValidator();

        Rook rook = new Rook(Colour.BLACK);
        Pawn blocker = new Pawn(Colour.BLACK);

        Position rookPosition = new Position(7, 0);
        Position blockerPosition = new Position(5, 0);
        Position target = new Position(3, 0);

        board.setPiece(rookPosition, rook);
        board.setPiece(blockerPosition, blocker);

        assertFalse(
            validator.isSquareUnderAttack(
                board,
                target,
                Colour.BLACK
            )
        );
    }


    @Test
    void knightAttacksSquare() {

        Board board = new Board();
        MoveValidator validator = new MoveValidator();

        Knight knight = new Knight(Colour.BLACK);

        Position knightPosition = new Position(4, 4);
        Position target = new Position(2, 3);

        board.setPiece(knightPosition, knight);

        assertTrue(
            validator.isSquareUnderAttack(
                board,
                target,
                Colour.BLACK
            )
        );
    }


    @Test
    void pawnAttacksDiagonalEmptySquare() {

        Board board = new Board();
        MoveValidator validator = new MoveValidator();

        Pawn pawn = new Pawn(Colour.WHITE);

        Position pawnPosition = new Position(3, 3);
        Position target = new Position(4, 4);

        board.setPiece(pawnPosition, pawn);

        assertNull(board.getPiece(target));

        assertTrue(
            validator.isSquareUnderAttack(
                board,
                target,
                Colour.WHITE
            )
        );
    }


    @Test
    void pawnDoesNotAttackSquareStraightAhead() {

        Board board = new Board();
        MoveValidator validator = new MoveValidator();

        Pawn pawn = new Pawn(Colour.WHITE);

        Position pawnPosition = new Position(3, 3);
        Position target = new Position(4, 3);

        board.setPiece(pawnPosition, pawn);

        assertFalse(
            validator.isSquareUnderAttack(
                board,
                target,
                Colour.WHITE
            )
        );
    }

    @Test
void movingPieceCannotExposeOwnKing() {

    Board board = new Board();
    MoveValidator validator = new MoveValidator();

    King whiteKing = new King(Colour.WHITE);
    Bishop whiteBishop = new Bishop(Colour.WHITE);
    Rook blackRook = new Rook(Colour.BLACK);

    Position kingPosition = new Position(0, 4);
    Position bishopPosition = new Position(1, 4);
    Position rookPosition = new Position(7, 4);

    Position bishopTarget = new Position(2, 5);

    board.setPiece(kingPosition, whiteKing);
    board.setPiece(bishopPosition, whiteBishop);
    board.setPiece(rookPosition, blackRook);

    assertTrue(
        validator.wouldMoveLeaveKingInCheck(
            board,
            bishopPosition,
            bishopTarget,
            Colour.WHITE
        )
    );
}

@Test
void movingPieceCanMoveIfKingRemainsSafe() {

    Board board = new Board();
    MoveValidator validator = new MoveValidator();

    King whiteKing = new King(Colour.WHITE);
    Bishop whiteBishop = new Bishop(Colour.WHITE);
    Knight whiteKnight = new Knight(Colour.WHITE);
    Rook blackRook = new Rook(Colour.BLACK);

    Position kingPosition = new Position(0, 4);
    Position bishopPosition = new Position(1, 4);
    Position knightPosition = new Position(2, 2);
    Position knightTarget = new Position(4, 3);
    Position rookPosition = new Position(7, 4);

    board.setPiece(kingPosition, whiteKing);
    board.setPiece(bishopPosition, whiteBishop);
    board.setPiece(knightPosition, whiteKnight);
    board.setPiece(rookPosition, blackRook);

    assertFalse(
        validator.wouldMoveLeaveKingInCheck(
            board,
            knightPosition,
            knightTarget,
            Colour.WHITE
        )
    );
}

@Test
void simulatedMoveRestoresBoardAfterCheck() {

    Board board = new Board();
    MoveValidator validator = new MoveValidator();

    King whiteKing = new King(Colour.WHITE);
    Bishop whiteBishop = new Bishop(Colour.WHITE);
    Rook blackRook = new Rook(Colour.BLACK);

    Position kingPosition = new Position(0, 4);
    Position bishopPosition = new Position(1, 4);
    Position bishopTarget = new Position(2, 5);
    Position rookPosition = new Position(7, 4);

    board.setPiece(kingPosition, whiteKing);
    board.setPiece(bishopPosition, whiteBishop);
    board.setPiece(rookPosition, blackRook);

    validator.wouldMoveLeaveKingInCheck(
        board,
        bishopPosition,
        bishopTarget,
        Colour.WHITE
    );

    assertSame(
        whiteBishop,
        board.getPiece(bishopPosition)
    );

    assertNull(
        board.getPiece(bishopTarget)
    );
}

     
}