public class Bishop extends Piece{

    public Bishop(Colour colour){
        super(colour);
    }

    @Override
    public boolean isValdidMove(
        Position from,
        Position to,
        Board board
    ){
    
        return from.row() == to.row()
        || from.column() == to.column();

        Math.abs(from.row() - to.row())
        ==
        Math.abs(from.column() - to.column())
    }
}