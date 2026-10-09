package chess.game;

import chess.*;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {
    
    @Test 
    void whiteStartsTheGame(){

        Game game = new Game(); 

        assertEquals(Colour.WHITE, game.getCurrentTurn());
    }

    @Test
void turnChangesAfterValidMove() {

    Game game = new Game();

    Position from = new Position(1, 4);
    Position to = new Position(3, 4);

    boolean moveWasValid = game.movePiece(from, to);

    assertTrue(moveWasValid);
    assertEquals(Colour.BLACK, game.getCurrentTurn());
}

@Test
void samePlayerCannotMoveTwiceInARow() {

    Game game = new Game();

    game.movePiece(
            new Position(1, 4),
            new Position(3, 4)
    );

    boolean secondMove = game.movePiece(
            new Position(1, 3),
            new Position(3, 3)
    );

    assertFalse(secondMove);
}

@Test
void blackCannotMoveFirst() {

    Game game = new Game();

    boolean moveWasValid = game.movePiece(
            new Position(6, 4),
            new Position(4, 4)
    );

    assertFalse(moveWasValid);
    assertEquals(Colour.WHITE, game.getCurrentTurn());
}

@Test
void invalidMoveDoesNotChangeTurn() {

    Game game = new Game();

    boolean moveWasValid = game.movePiece(
            new Position(1, 4),
            new Position(5, 4)
    );

    assertFalse(moveWasValid);
    assertEquals(Colour.WHITE, game.getCurrentTurn());
}

@Test
void whiteAndBlackCanMoveAlternately() {

    Game game = new Game();

    boolean whiteMove = game.movePiece(
            new Position(1, 4),
            new Position(3, 4)
    );

    boolean blackMove = game.movePiece(
            new Position(6, 4),
            new Position(4, 4)
    );

    assertTrue(whiteMove);
    assertTrue(blackMove);
    assertEquals(Colour.WHITE, game.getCurrentTurn());
}

@Test
void foolsMateResultsInCheckmate() {

    Game game = new Game();

    // 1. f3
    assertTrue(
        game.movePiece(
            new Position(1, 5),
            new Position(2, 5)
        )
    );

    // ... e5
    assertTrue(
        game.movePiece(
            new Position(6, 4),
            new Position(4, 4)
        )
    );

    // 2. g4
    assertTrue(
        game.movePiece(
            new Position(1, 6),
            new Position(3, 6)
        )
    );

    // ... Qh4#
    assertTrue(
        game.movePiece(
            new Position(7, 3),
            new Position(3, 7)
        )
    );

    assertEquals(
        GameStatus.CHECKMATE,
        game.getGameStatus()
    );

    Piece pawn = game.getBoard().getPiece(new Position(1, 0));
    assertFalse(game.movePiece(new Position(1, 0), new Position(2, 0)));
    assertSame(pawn, game.getBoard().getPiece(new Position(1, 0)));
    assertNull(game.getBoard().getPiece(new Position(2, 0)));
    assertEquals(Colour.WHITE, game.getCurrentTurn());
    assertEquals(GameStatus.CHECKMATE, game.getGameStatus());

    game.resetGame();
    assertStartingPosition(game);
    assertTrue(game.movePiece(new Position(1, 4), new Position(3, 4)));
}

    @Test
    void resetRestoresStartingPositionAndWhiteTurn() {
        Game game = new Game();
        assertTrue(game.movePiece(new Position(1, 4), new Position(3, 4)));
        assertEquals(Colour.BLACK, game.getCurrentTurn());
        game.resetGame();
        assertStartingPosition(game);
    }

    private void assertStartingPosition(Game game) {
        assertEquals(Colour.WHITE, game.getCurrentTurn());
        assertEquals(GameStatus.ACTIVE, game.getGameStatus());
        Board startingBoard = new Game().getBoard();
        int pieceCount = 0;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Position position = new Position(row, column);
                Piece expected = startingBoard.getPiece(position);
                Piece actual = game.getBoard().getPiece(position);
                if (expected == null) {
                    assertNull(actual);
                } else {
                    assertNotNull(actual);
                    assertEquals(expected.getClass(), actual.getClass());
                    assertEquals(expected.getColour(), actual.getColour());
                    pieceCount++;
                }
            }
        }
        assertEquals(32, pieceCount);
    }

    @Test
    void pawnPromotionCreatesQueenForBothColours() {
        Game game = new Game();
        Board board = game.getBoard();
        board.clearBoard();
        board.setPiece(new Position(0, 4), new King(Colour.WHITE));
        board.setPiece(new Position(7, 4), new King(Colour.BLACK));
        board.setPiece(new Position(6, 0), new Pawn(Colour.WHITE));
        board.setPiece(new Position(1, 7), new Pawn(Colour.BLACK));

        assertTrue(game.movePiece(new Position(6, 0), new Position(7, 0)));
        assertInstanceOf(Queen.class, board.getPiece(new Position(7, 0)));
        assertEquals(Colour.WHITE, board.getPiece(new Position(7, 0)).getColour());
        assertNull(board.getPiece(new Position(6, 0)));
        assertTrue(game.movePiece(new Position(7, 4), new Position(6, 4)));
        assertTrue(game.movePiece(new Position(0, 4), new Position(1, 4)));
        assertTrue(game.movePiece(new Position(1, 7), new Position(0, 7)));
        assertInstanceOf(Queen.class, board.getPiece(new Position(0, 7)));
        assertEquals(Colour.BLACK, board.getPiece(new Position(0, 7)).getColour());
        assertNull(board.getPiece(new Position(1, 7)));
    }

    @Test
    void moveExposingOwnKingIsRejected() {
        Game game = new Game();
        Board board = game.getBoard();
        board.clearBoard();
        board.setPiece(new Position(0, 4), new King(Colour.WHITE));
        Bishop bishop = new Bishop(Colour.WHITE);
        board.setPiece(new Position(1, 4), bishop);
        board.setPiece(new Position(7, 4), new Rook(Colour.BLACK));
        board.setPiece(new Position(7, 0), new King(Colour.BLACK));

        assertFalse(game.movePiece(new Position(1, 4), new Position(2, 5)));
        assertSame(bishop, board.getPiece(new Position(1, 4)));
        assertNull(board.getPiece(new Position(2, 5)));
        assertEquals(Colour.WHITE, game.getCurrentTurn());
    }

    @Test
    void kingCannotMoveOntoPawnAttack() {
        Game game = new Game();
        Board board = game.getBoard();
        board.clearBoard();
        board.setPiece(new Position(0, 4), new King(Colour.WHITE));
        board.setPiece(new Position(2, 4), new Pawn(Colour.BLACK));
        board.setPiece(new Position(7, 0), new King(Colour.BLACK));

        assertFalse(game.movePiece(new Position(0, 4), new Position(1, 5)));
        assertEquals(Colour.WHITE, game.getCurrentTurn());
    }

    @Test
    void kingCannotBeCapturedAndCheckIsDetected() {
        Game game = new Game();
        Board board = game.getBoard();
        board.clearBoard();
        board.setPiece(new Position(0, 0), new King(Colour.WHITE));
        board.setPiece(new Position(1, 7), new Rook(Colour.WHITE));
        King blackKing = new King(Colour.BLACK);
        board.setPiece(new Position(7, 7), blackKing);

        assertFalse(game.movePiece(new Position(1, 7), new Position(7, 7)));
        assertSame(blackKing, board.getPiece(new Position(7, 7)));
        assertTrue(game.movePiece(new Position(1, 7), new Position(6, 7)));
        assertEquals(GameStatus.CHECK, game.getGameStatus());
    }

    @Test
    void stalemateRejectsMovesUntilReset() {
        Game game = new Game();
        Board board = game.getBoard();
        board.clearBoard();
        King whiteKing = new King(Colour.WHITE);
        board.setPiece(new Position(0, 0), whiteKing);
        board.setPiece(new Position(2, 1), new King(Colour.BLACK));
        board.setPiece(new Position(1, 2), new Queen(Colour.BLACK));

        assertEquals(GameStatus.STALEMATE, game.getGameStatus());
        assertFalse(game.movePiece(new Position(0, 0), new Position(0, 1)));
        assertSame(whiteKing, board.getPiece(new Position(0, 0)));
        assertEquals(Colour.WHITE, game.getCurrentTurn());
        assertEquals(GameStatus.STALEMATE, game.getGameStatus());
        game.resetGame();
        assertStartingPosition(game);
        assertTrue(game.movePiece(new Position(1, 4), new Position(3, 4)));
    }
}
