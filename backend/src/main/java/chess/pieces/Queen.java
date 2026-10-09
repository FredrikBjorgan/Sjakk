package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public class Queen extends Piece{

    public Queen(Colour colour){
    super(colour, 9);
    }

    @Override 
    public boolean isValidMove(Position from, Position to, Board board){

        if (from.equals(to)){
            return false;
        }


    //Trekk rett horizontalt eller Verticalt

    if (from.row() == to.row() 
            || from.column() == to.column()
            ) {
        return true 
            && board.isPathClear(from, to)
            && board.canCaptureOrMoveTo(to, getColour());
    }

    //Trekk Diagonalt
    if (Math.abs(from.row() - to.row()) 
            == Math.abs(from.column() - to.column())
            ) {
        return true 
            && board.isPathClear(from, to)
            && board.canCaptureOrMoveTo(to, getColour());
    }

        return false;
    }


}