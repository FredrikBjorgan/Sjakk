package chess.api;

import chess.pieces.Piece;
import chess.*;

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

    private final Game game;

    public BoardController() {
        game = new Game();
    }

    @GetMapping("/test")
    public String testBoardApi() {
        return "Board API works!";
    }

    @GetMapping("/turn")
    public Colour getCurrentTurn() {
    return game.getCurrentTurn();
    }

    @GetMapping("/status")
    public GameStatus getGameStatus(){
        return game.getGameStatus();
    }


    @GetMapping("/captured")
    public CapturedPiecesResponse getCapturedPieces() {
        return new CapturedPiecesResponse(
                toCapturedResponses(game.getCapturedByWhite()),
                toCapturedResponses(game.getCapturedByBlack()),
                game.getWhiteMaterialDifference(),
                game.getBlackMaterialDifference()
        );
    }

    private List<CapturedPieceResponse> toCapturedResponses(List<Piece> pieces) {
        return pieces.stream()
                .map(piece -> new CapturedPieceResponse(
                        piece.getClass().getSimpleName(),
                        piece.getColour(),
                        piece.getValue()))
                .toList();
    }

    @GetMapping
    public List<BoardSquareResponse> getBoard() {

        List<BoardSquareResponse> squares = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {

                Position position = new Position(row, column);
                Piece piece = game.getBoard().getPiece(position);

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

    @PostMapping("/reset")
    public void resetGame() {
        game.resetGame();
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

        return game.movePiece(from, to);
    }
}
