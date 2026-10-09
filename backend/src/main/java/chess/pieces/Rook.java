package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public class Rook extends Piece{

    public Rook(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValidMove(Position from, Position to, Board board ){

        if (from.equals(to)){
            return false;
        }

        boolean moveStraight =
            from.row() == to.row()
            || from.column() == to.column();

        return moveStraight 
            && board.isPathClear(from, to)
            && board.canCaptureOrMoveTo(to, getColour());

    } 
}