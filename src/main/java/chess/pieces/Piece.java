package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public abstract class Piece {


    private final Colour colour;

    public Piece(Colour colour){

        this.colour = colour;
    }

    public Colour getColour(){

        return colour;
    }

    public boolean isValdidMove(

        Position from,
        Position to,
        Board board
    )

}