package chess.api;

public record MoveRequest(
        int fromRow,
        int fromColumn,
        int toRow,
        int toColumn
) {
}