package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public class King extends Piece{

        public King(Colour colour){
            super(colour);
        }

        @Override 
        public boolean isValidMove(Position from, Position to, Board board){

            if (from.equals(to)) {
                return false;
            }

            int rowDiffrence = Math.abs(to.row() - from.row());
            int columnDiffrence = Math.abs(to.column() - from.column());

            //Vanlig trekk 
            boolean movesOneSquare = 
                rowDiffrence <= 1
                && columnDiffrence <=1;

            return movesOneSquare
                && board.canCaptureOrMoveTo(to, getColour());
        }
}