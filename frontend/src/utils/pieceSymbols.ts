export function getPieceSymbol(
    piece: string | null,
    colour: "WHITE" | "BLACK" | null
) {
    if (piece === null || colour === null) {
        return "";
    }

    const symbols: Record<string, string> = {
        "WHITE-King": "♔",
        "WHITE-Queen": "♕",
        "WHITE-Rook": "♖",
        "WHITE-Bishop": "♗",
        "WHITE-Knight": "♘",
        "WHITE-Pawn": "♙",

        "BLACK-King": "♚",
        "BLACK-Queen": "♛",
        "BLACK-Rook": "♜",
        "BLACK-Bishop": "♝",
        "BLACK-Knight": "♞",
        "BLACK-Pawn": "♟",
    };

    return symbols[`${colour}-${piece}`] ?? "";
}