package chess;

import chess.pieces.Piece;

public class Board {

    private Piece[][] Square;

    public Board () {

        Square = new Square[8][8];
}
    public piece getPiece(Position position) {

        return Square[position.row()][position.column()];
    }

    public void setPiece(Position position, Piece piece) {

        Square[position.row()][position.column()] = piece;
    }


}