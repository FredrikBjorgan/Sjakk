package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public class Knight extends Piece{

    public Knight(Colour colour){
        super(colour);
    }

    @Override 
    public boolean isValidMove(Position from, Position to, Board board){

        if (from.equals(to)) {
            return false;
        }

        int rowDiffrence = Math.abs(to.row() - from.row());
        int columnDiffrence = Math.abs(to.column() - from.column());

        boolean movesInLShape =
        (rowDiffrence == 2 && columnDiffrence == 1)
        || (rowDiffrence == 1 && columnDiffrence == 2);

        return movesInLShape && board.canCaptureOrMoveTo(to, getColour());

    }
}