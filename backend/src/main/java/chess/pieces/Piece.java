package chess.pieces;

import chess.Board;
import chess.Colour;
import chess.Position;

public abstract class Piece {


    private final Colour colour;
    private final int value;


    public Piece(Colour colour, int value){

        this.colour = colour;
        this.value = value;
    }

    public Colour getColour(){

        return colour;
    }

    public abstract boolean isValidMove(

        Position from,
        Position to,
        Board board
    );

    public int getValue(){
        return value;
    }

}