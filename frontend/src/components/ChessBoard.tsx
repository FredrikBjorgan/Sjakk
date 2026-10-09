import { useChessGame } from "../hooks/useChessGame";
import { getPieceSymbol } from "../utils/pieceSymbols";

export default function ChessBoard() {

    const {
        displaySquares,
        selectedSquare,
        currentTurn,
        message,
        handleSquareClick,
    } = useChessGame();

    return (
        <div>
            <h2>Chess Board</h2>

            <p className="current-turn">
                {currentTurn === "WHITE"
                    ? "White to move"
                    : "Black to move"}
            </p>

            <div className="chess-board">
                {displaySquares.map((square) => (
                    <div
                        key={`${square.row}-${square.column}`}
                        onClick={() => handleSquareClick(square)}
                        className={
                            `${(square.row + square.column) % 2 === 0
                                ? "square light"
                                : "square dark"
                            } ${
                                selectedSquare?.row === square.row &&
                                selectedSquare?.column === square.column
                                    ? "selected"
                                    : ""
                            }`
                        }
                    >
                        {getPieceSymbol(
                            square.piece,
                            square.colour
                        )}
                    </div>
                ))}
            </div>

            {message && (
                <p className="move-message">
                    {message}
                </p>
            )}
        </div>
    );
}