package chess;

import chess.pieces.Piece;

public class Board {

    private Piece[][] Square;

    public Board () {

        Square = new Piece[8][8];
}
    public Piece getPiece(Position position) {

        return Square[position.row()][position.column()];
    }

    public void setPiece(Position position, Piece piece) {

        Square[position.row()][position.column()] = piece;
    }

public boolean isPathClear(Position from, Position to) {

    int rowStep = Integer.compare(to.row(), from.row());
    int columnStep = Integer.compare(to.column(), from.column());

    int currentRow = from.row() + rowStep;
    int currentColumn = from.column() + columnStep;

    while (currentRow != to.row() || currentColumn != to.column()) {

        if (Square[currentRow][currentColumn] != null) {
            return false;
        }

        currentRow += rowStep;
        currentColumn += columnStep;
     }

return true; };

public boolean canCaptureOrMoveTo(Position position, Colour colour) {
    Piece targetPiece = getPiece(position);

    return targetPiece == null
            || targetPiece.getColour() != colour;
}

    }

