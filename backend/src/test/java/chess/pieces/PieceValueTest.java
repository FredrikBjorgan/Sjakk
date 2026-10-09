package chess.pieces;

import chess.Colour;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PieceValueTest {
    

    @Test 
    void piecesHaveCorrectValues(){

        assertEquals(1, new Pawn(Colour.WHITE).getValue());
        assertEquals(3, new Knight(Colour.WHITE).getValue());
        assertEquals(3, new Bishop(Colour.WHITE).getValue());
        assertEquals(5, new Rook(Colour.WHITE).getValue());
        assertEquals(9, new Queen(Colour.WHITE).getValue());
        assertEquals(0, new King(Colour.WHITE).getValue());
    }
}
