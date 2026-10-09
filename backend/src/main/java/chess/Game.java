package chess;

import chess.pieces.Piece;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.rules.MoveValidator;

public class Game{

private final Board board;
private Colour currentTurn;
private final MoveValidator moveValidator;

public Game (){

    board = new Board();
    board.setStartPosition();

    moveValidator = new MoveValidator();

    currentTurn = Colour.WHITE;
}

public Board getBoard() {
    return board;
}

public Colour getCurrentTurn() {
    return currentTurn;
}

public boolean movePiece(Position from, Position to) {

    Piece piece = board.getPiece(from);

    // Ingen brikke på startruten
    if (piece == null) {
        return false;
    }

    // Feil spiller prøver å flytte
    if (piece.getColour() != currentTurn) {
        return false;
    }

    Piece targetPiece = board.getPiece(to);

    // Kongen skal aldri kunne "slås"
    if (targetPiece instanceof King) {
        return false;
    }

    // Kontroller vanlig bevegelsesregel
    if (!piece.isValidMove(from, to, board)) {
        return false;
    }

    // Kontroller at eget trekk ikke etterlater kongen i sjakk
    if (
        moveValidator.wouldMoveLeaveKingInCheck(
            board,
            from,
            to,
            currentTurn
        )
    ) {
        return false;
    }

    boolean moveWasValid = board.movePiece(from, to);

    if (!moveWasValid) {
        return false;
    }

    // Bytt spiller
    if (currentTurn == Colour.WHITE) {
        currentTurn = Colour.BLACK;
    } else {
        currentTurn = Colour.WHITE;
    }

    return true;
}
public GameStatus getGameStatus() {
        boolean kingInCheck =
                moveValidator.isKingInCheck(
                    board,
                    currentTurn
                );

        boolean hasLegalMove =
                moveValidator.hasAnyLegalMove(
                    board,
                    currentTurn
                );

        if (!hasLegalMove && kingInCheck) {
            return GameStatus.CHECKMATE;
        }

        if (!hasLegalMove) {
            return GameStatus.STALEMATE;
        }

        if (kingInCheck) {
            return GameStatus.CHECK;
        }

        return GameStatus.ACTIVE;
    }
    public void resetGame() {

    board.clearBoard();
    board.setStartPosition();

    currentTurn = Colour.WHITE;
}

}
