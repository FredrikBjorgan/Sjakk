package chess.api;

import chess.Board;
import chess.Position;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final Board board;

    public BoardController() {
        board = new Board();
        board.setStartPosition();
    }

    @GetMapping("/test")
    public String testBoardApi() {
        return "Board API works!";
    }

    @GetMapping
    public List<BoardSquareResponse> getBoard() {

        List<BoardSquareResponse> squares = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {

                Position position = new Position(row, column);
                Piece piece = board.getPiece(position);

                if (piece == null) {

                    squares.add(
                        new BoardSquareResponse(
                            row,
                            column,
                            null,
                            null
                        )
                    );

                } else {

                    squares.add(
                        new BoardSquareResponse(
                            row,
                            column,
                            piece.getClass().getSimpleName(),
                            piece.getColour()
                        )
                    );
                }
            }
        }

        return squares;
    }

    @PostMapping("/move")
    public boolean movePiece(@RequestBody MoveRequest moveRequest){

        Position from = new Position(
            moveRequest.fromRow(),
            moveRequest.fromColumn()
        );

        Position to = new Position(
            moveRequest.toRow(),
            moveRequest.toColumn()
        );

        return board.movePiece(from, to);
    }
}