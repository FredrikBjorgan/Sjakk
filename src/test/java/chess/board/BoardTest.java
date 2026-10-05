package chess.board;

import chess.Board;
import chess.Colour;
import chess.Position;
import chess.pieces.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    
    @Test
    void startPositionContains32Pieces(){

        Board board = new Board();
        board.setStartPosition();

        int pieceCount = 0;

        for(int row = 0; row < 8; row++){
            for(int coloum = 0; coloum < 8; coloum++){

                if(board.getPiece(

                    new Position( row, coloum)) != null){
                        pieceCount++;
                    }
            }

        }

        assertEquals(32, pieceCount);
    }

    @Test
void startPositionPlacesWhitePiecesCorrectly() {

    Board board = new Board();
    board.setStartPosition();

    assertInstanceOf(Rook.class,
            board.getPiece(new Position(0, 0)));

    assertInstanceOf(Knight.class,
            board.getPiece(new Position(0, 1)));

    assertInstanceOf(Bishop.class,
            board.getPiece(new Position(0, 2)));

    assertInstanceOf(Queen.class,
            board.getPiece(new Position(0, 3)));

    assertInstanceOf(King.class,
            board.getPiece(new Position(0, 4)));
}
    @Test
void whiteKingHasCorrectColour() {

    Board board = new Board();
    board.setStartPosition();

    Piece piece = board.getPiece(new Position(0, 4));

    assertEquals(Colour.WHITE, piece.getColour());
}

@Test
void startPositionPlacesWhitePawnsCorrectly() {

    Board board = new Board();
    board.setStartPosition();

    for (int column = 0; column < 8; column++) {

        Piece piece =
                board.getPiece(new Position(1, column));

        assertInstanceOf(Pawn.class, piece);
        assertEquals(Colour.WHITE, piece.getColour());
    }
}
@Test
void middleOfBoardIsEmptyAtStart() {

    Board board = new Board();
    board.setStartPosition();

    for (int row = 2; row <= 5; row++) {
        for (int column = 0; column < 8; column++) {

            assertNull(
                board.getPiece(new Position(row, column))
            );
        }
    }
}



}
