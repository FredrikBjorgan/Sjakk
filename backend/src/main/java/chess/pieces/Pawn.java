package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public class Pawn extends Piece{

    public Pawn(Colour colour){
        super(colour, 1);
    }

    

    @Override 
    public boolean isValidMove(Position from, Position to, Board board){
        
        if (from.equals(to)){
            return false;
        }

        int directions = getColour() == Colour.WHITE ? 1 : -1;
        int startRow = getColour() == Colour.WHITE ? 1: 6;

        int rowDifference = to.row() - from.row();
        int columnDiffrence = Math.abs(to.column() - from.column());

        Piece targetPiece = board.getPiece(to);


        //Vanlig trekk rett fram
        if (columnDiffrence == 0 && rowDifference == directions && targetPiece == null) {
            return true;
        }

        // Starttrekk 2 ruter fram
        if (columnDiffrence == 0 
            && rowDifference == 2 * directions
            && from.row() == startRow
            && targetPiece == null ) {
            
                return board.isPathClear(from, to);
        }

        //Slår motstander diagonalt
        if (columnDiffrence == 1
            && rowDifference == directions
            && targetPiece != null 
            && targetPiece.getColour() != getColour()
        ) {
            
            return true;
        }
        return false;

    
    }
}