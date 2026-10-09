package chess;

import chess.pieces.King;
import chess.pieces.Piece;
import chess.rules.MoveValidator;
import java.util.ArrayList;
import java.util.List;

public class Game{

private final Board board;
private Colour currentTurn;
private final MoveValidator moveValidator;
private final List<Piece> capturedByWhite = new ArrayList<>();
private final List<Piece> capturedByBlack = new ArrayList<>();

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

public List<Piece> getCapturedByWhite() {
    return List.copyOf(capturedByWhite);
}

public List<Piece> getCapturedByBlack() {
    return List.copyOf(capturedByBlack);
}

public int getWhiteMaterialDifference() {
    return capturedByWhite.stream().mapToInt(Piece::getValue).sum()
            - capturedByBlack.stream().mapToInt(Piece::getValue).sum();
}

public int getBlackMaterialDifference() {
    return -getWhiteMaterialDifference();
}

public boolean movePiece(Position from, Position to) {

    GameStatus status = getGameStatus();
    if (status == GameStatus.CHECKMATE || status == GameStatus.STALEMATE) {
        return false;
    }

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

    if (targetPiece != null) {
        if (piece.getColour() == Colour.WHITE) {
            capturedByWhite.add(targetPiece);
        } else {
            capturedByBlack.add(targetPiece);
        }
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
    capturedByWhite.clear();
    capturedByBlack.clear();

    currentTurn = Colour.WHITE;
}

}
