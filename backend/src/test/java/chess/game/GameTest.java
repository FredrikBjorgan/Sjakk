package chess.game;

import chess.*;

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

}
