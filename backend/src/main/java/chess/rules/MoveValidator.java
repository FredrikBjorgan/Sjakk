package chess.rules;

import chess.*;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;

public class MoveValidator {

   public boolean isSquareUnderAttack(
        Board board,
        Position target,
        Colour attackingColour
) {

    for (int row = 0; row < 8; row++) {
        for (int column = 0; column < 8; column++) {

            Position from = new Position(row, column);
            Piece piece = board.getPiece(from);

            if (piece == null) {
                continue;
            }

            if (piece.getColour() != attackingColour) {
                continue;
            }

            if (piece instanceof Pawn) {

                int direction =
                        piece.getColour() == Colour.WHITE ? 1 : -1;

                int rowDifference =
                        target.row() - from.row();

                int columnDifference =
                        Math.abs(target.column() - from.column());

                if (
                    rowDifference == direction
                    && columnDifference == 1
                ) {
                    return true;
                }

                continue;
            }

            if (piece.isValidMove(from, target, board)) {
                return true;
            }
        }
    }

    return false;
}
public boolean isKingInCheck(
        Board board,
        Colour kingColour
) {

    Position kingPosition = null;

    for (int row = 0; row < 8; row++) {
        for (int column = 0; column < 8; column++) {

            Position position = new Position(row, column);
            Piece piece = board.getPiece(position);

            if (
                piece instanceof King &&
                piece.getColour() == kingColour
            ) {
                kingPosition = position;
                break;
            }
        }

        if (kingPosition != null) {
            break;
        }
    }

    if (kingPosition == null) {
        return false;
    }

    Colour attackingColour =
            kingColour == Colour.WHITE
                    ? Colour.BLACK
                    : Colour.WHITE;

    return isSquareUnderAttack(
            board,
            kingPosition,
            attackingColour
    );
}

public boolean wouldMoveLeaveKingInCheck(
        Board board,
        Position from,
        Position to,
        Colour movingColour
) {

    Piece movingPiece = board.getPiece(from);
    Piece capturedPiece = board.getPiece(to);

    if (movingPiece == null) {
        return true;
    }

    // Utfør trekket midlertidig
    board.setPiece(to, movingPiece);
    board.setPiece(from, null);

    boolean kingInCheck =
            isKingInCheck(board, movingColour);

    // Sett brettet tilbake
    board.setPiece(from, movingPiece);
    board.setPiece(to, capturedPiece);

    return kingInCheck;
}

public boolean hasAnyLegalMove(
        Board board,
        Colour colour
) {

    for (int fromRow = 0; fromRow < 8; fromRow++) {
        for (int fromColumn = 0; fromColumn < 8; fromColumn++) {

            Position from =
                    new Position(fromRow, fromColumn);

            Piece piece = board.getPiece(from);

            if (piece == null) {
                continue;
            }

            if (piece.getColour() != colour) {
                continue;
            }

            for (int toRow = 0; toRow < 8; toRow++) {
                for (int toColumn = 0; toColumn < 8; toColumn++) {

                    Position to =
                            new Position(toRow, toColumn);

                    if (from.equals(to)) {
                        continue;
                    }

                    Piece targetPiece = board.getPiece(to);

                    if (targetPiece instanceof King) {
                        continue;
                    }

                    if (!piece.isValidMove(from, to, board)) {
                        continue;
                    }

                    if (
                        !wouldMoveLeaveKingInCheck(
                            board,
                            from,
                            to,
                            colour
                        )
                    ) {
                        return true;
                    }
                }
            }
        }
    }

    return false;
}


}