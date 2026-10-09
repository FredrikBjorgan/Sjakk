package chess.game;

import chess.Board;
import chess.Colour;
import chess.Game;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CapturedPiecesTest {
    private Game game;
    private Board board;

    @BeforeEach
    void setUp() {
        game = new Game();
        board = game.getBoard();
        board.clearBoard();
        board.setPiece(new Position(0, 0), new King(Colour.WHITE));
        board.setPiece(new Position(7, 7), new King(Colour.BLACK));
        board.setPiece(new Position(2, 0), new Rook(Colour.WHITE));
        board.setPiece(new Position(2, 3), new Pawn(Colour.BLACK));
        board.setPiece(new Position(5, 7), new Rook(Colour.BLACK));
        board.setPiece(new Position(5, 4), new Pawn(Colour.WHITE));
    }

    private void whiteCapturesPawn() {
        assertTrue(game.movePiece(new Position(2, 0), new Position(2, 3)));
    }

    private void bothCapturePawn() {
        whiteCapturesPawn();
        assertTrue(game.movePiece(new Position(5, 7), new Position(5, 4)));
    }

    @Test
    void whiteCaptureRecordsBlackPawnAndRelativeMaterial() {
        whiteCapturesPawn();
        assertEquals(1, game.getCapturedByWhite().size());
        assertInstanceOf(Pawn.class, game.getCapturedByWhite().get(0));
        assertEquals(Colour.BLACK, game.getCapturedByWhite().get(0).getColour());
        assertTrue(game.getCapturedByBlack().isEmpty());
        assertEquals(1, game.getWhiteMaterialDifference());
        assertEquals(-1, game.getBlackMaterialDifference());
    }

    @Test
    void equalCapturesGiveZeroDifference() {
        bothCapturePawn();
        assertEquals(1, game.getCapturedByBlack().size());
        assertEquals(Colour.WHITE, game.getCapturedByBlack().get(0).getColour());
        assertEquals(0, game.getWhiteMaterialDifference());
        assertEquals(0, game.getBlackMaterialDifference());
    }

    @Test
    void additionalKnightCaptureUsesPieceValue() {
        board.setPiece(new Position(2, 5), new Knight(Colour.BLACK));
        bothCapturePawn();
        assertTrue(game.movePiece(new Position(2, 3), new Position(2, 5)));
        assertEquals(2, game.getCapturedByWhite().size());
        assertEquals(3, game.getWhiteMaterialDifference());
        assertEquals(-3, game.getBlackMaterialDifference());
    }

    @Test
    void blackMaterialAdvantageHasPositiveBlackDifference() {
        board.setPiece(new Position(5, 4), new Queen(Colour.WHITE));
        bothCapturePawn();
        assertEquals(-8, game.getWhiteMaterialDifference());
        assertEquals(8, game.getBlackMaterialDifference());
    }

    @Test
    void invalidCaptureDoesNotChangeHistory() {
        assertFalse(game.movePiece(new Position(2, 0), new Position(5, 4)));
        assertFalse(game.movePiece(new Position(5, 7), new Position(5, 4)));
        assertTrue(game.getCapturedByWhite().isEmpty());
        assertTrue(game.getCapturedByBlack().isEmpty());
        assertEquals(0, game.getWhiteMaterialDifference());
        assertEquals(0, game.getBlackMaterialDifference());
    }

    @Test
    void captureExposingKingDoesNotChangeHistory() {
        board.clearBoard();
        board.setPiece(new Position(0, 0), new King(Colour.WHITE));
        board.setPiece(new Position(7, 7), new King(Colour.BLACK));
        board.setPiece(new Position(1, 0), new Rook(Colour.WHITE));
        board.setPiece(new Position(7, 0), new Rook(Colour.BLACK));
        board.setPiece(new Position(1, 3), new Pawn(Colour.BLACK));
        assertFalse(game.movePiece(new Position(1, 0), new Position(1, 3)));
        assertTrue(game.getCapturedByWhite().isEmpty());
        assertTrue(game.getCapturedByBlack().isEmpty());
        assertEquals(0, game.getWhiteMaterialDifference());
    }

    @Test
    void resetClearsBothHistoriesAndMaterial() {
        bothCapturePawn();
        game.resetGame();
        assertTrue(game.getCapturedByWhite().isEmpty());
        assertTrue(game.getCapturedByBlack().isEmpty());
        assertEquals(0, game.getWhiteMaterialDifference());
        assertEquals(0, game.getBlackMaterialDifference());
        assertTrue(game.movePiece(new Position(1, 4), new Position(3, 4)));
        assertTrue(game.getCapturedByWhite().isEmpty());
    }

    @Test
    void statusQueriesAndQuietMovesDoNotRecordCaptures() {
        game.getGameStatus();
        assertTrue(game.movePiece(new Position(2, 0), new Position(3, 0)));
        game.getGameStatus();
        assertTrue(game.getCapturedByWhite().isEmpty());
        assertTrue(game.getCapturedByBlack().isEmpty());
    }
}
