package chess;

import chess.pieces.Piece;

public class Game{

private final Board board;
private Colour currentTurn;

public Game (){

    board = new Board();
    board.setStartPosition();

    currentTurn = Colour.WHITE;
}

public Board getBoard() {
    return board;
}

public Colour getCurrentTurn() {
    return currentTurn;
}

public Boolean movePiece(Position from, Position to){

    Piece piece = board.getPiece(from);

    //Om det ikke er brikker på startruten
    if (piece == null) {
        return false;
    }

    //Feil farge prøver å flytte på en brikke
    if (piece.getColour() != currentTurn) {
        return false;
    }

    // Lar board kontrollere trekkreglene
    
    boolean moveWasValid = board.movePiece(from, to);

    if (!moveWasValid) {
        return false;
    }

    //gyldige trekk

    if (currentTurn == Colour.WHITE) {
        currentTurn = Colour.BLACK;
    } else{
        currentTurn = Colour.WHITE;

    }

    return true;


}

}
