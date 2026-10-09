package chess.api;

import chess.Colour;

public record BoardSquareResponse(
        int row,
        int column,
        String piece,
        Colour colour
) {
}