import type { CapturedPiece } from "../types/CapturedPiece";
import { getPieceSymbol } from "../utils/pieceSymbols";

type PlayerInfoProps = {
    colour: "WHITE" | "BLACK";
    capturedPieces: CapturedPiece[];
    materialDifference: number;
    isCurrentTurn: boolean;
};

export default function PlayerInfo({
    colour, capturedPieces, materialDifference, isCurrentTurn,
}: PlayerInfoProps) {
    const name = colour === "WHITE" ? "White" : "Black";
    const sortedPieces = [...capturedPieces]
        .filter((piece) => piece.piece !== "King")
        .sort((a, b) => b.value - a.value || a.piece.localeCompare(b.piece));

    return (
        <section
            className={`player-info${isCurrentTurn ? " active-player" : ""}`}
            aria-label={`${name}${isCurrentTurn ? " to move" : ""}`}
        >
            <div className="player-name">
                {name}
                {isCurrentTurn && <span className="turn-dot" title="To move" aria-hidden="true" />}
            </div>
            <div className="player-material">
                <div className="captured-pieces" aria-label={`Pieces captured by ${name}`}>
                    {sortedPieces.map((piece, index) => (
                        <span key={`${piece.piece}-${index}`} title={`${piece.colour === "WHITE" ? "White" : "Black"} ${piece.piece}`}>
                            {getPieceSymbol(piece.piece, piece.colour)}
                        </span>
                    ))}
                </div>
                <span className="material-difference" aria-label={`${name} material difference: ${materialDifference}`}>
                    {materialDifference > 0 ? `+${materialDifference}` : materialDifference}
                </span>
            </div>
        </section>
    );
}
