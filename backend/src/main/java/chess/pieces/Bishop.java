package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public class Bishop extends Piece {

    public Bishop(Colour colour) {
        super(colour, 3);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Board board) {

        if (from.equals(to)) {
            return false;
        }

        boolean movesDiagonally =
                Math.abs(from.row() - to.row())
                == Math.abs(from.column() - to.column());

        return movesDiagonally && board.isPathClear(from, to) && board.canCaptureOrMoveTo(to, getColour());
    }
}