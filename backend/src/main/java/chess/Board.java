package chess;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

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

    public void setStartPosition(){
        

        //Hvite tårn
        setPiece(new Position(0,0), new Rook(Colour.WHITE));
        setPiece(new Position(0,7), new Rook(Colour.WHITE));

        //Hvite Hester
        setPiece(new Position(0,1), new Knight(Colour.WHITE));
        setPiece(new Position(0,6), new Knight(Colour.WHITE));

        //Hvite løpere
        setPiece(new Position(0,2), new Bishop(Colour.WHITE));
        setPiece(new Position(0,5), new Bishop(Colour.WHITE));

        //Hvite Konge og Dronning
        setPiece(new Position(0,3), new Queen(Colour.WHITE));
        setPiece(new Position(0,4), new King(Colour.WHITE));

        //Hvite bønner
        for(int column = 0; column<8; column++){
                setPiece(new Position(1,column), new Pawn(Colour.WHITE));
            }

         //Svarte tårn
        setPiece(new Position(7,0), new Rook(Colour.BLACK));
        setPiece(new Position(7,7), new Rook(Colour.BLACK));

        //Svarte Hester
        setPiece(new Position(7,1), new Knight(Colour.BLACK));
        setPiece(new Position(7,6), new Knight(Colour.BLACK));

        //Svarte løpere
        setPiece(new Position(7,2), new Bishop(Colour.BLACK));
        setPiece(new Position(7,5), new Bishop(Colour.BLACK));

        //Svarte Konge og Dronning
        setPiece(new Position(7,3), new Queen(Colour.BLACK));
        setPiece(new Position(7,4), new King(Colour.BLACK));

        //Svarte bønner
        for(int column = 0; column<8; column++){
                setPiece(new Position(6,column), new Pawn(Colour.BLACK));
            }
            }  

            public boolean movePiece(Position from, Position to) {

    Piece piece = getPiece(from);

    if (piece == null) {
        return false;
    }

    if (!piece.isValidMove(from, to, this)) {
        return false;
    }

    setPiece(to, piece);
    setPiece(from, null);

    return true;
    }
}

