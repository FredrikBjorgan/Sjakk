package chess.api;

import chess.Colour;

public record CapturedPieceResponse(String piece, Colour colour, int value) {}
