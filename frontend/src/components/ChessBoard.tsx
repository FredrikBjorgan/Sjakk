import { useChessGame } from "../hooks/useChessGame";
import { getPieceSymbol } from "../utils/pieceSymbols";
import PlayerInfo from "./PlayerInfo";

export default function ChessBoard() {
    const {
        displaySquares, selectedSquare, currentTurn, gameStatus, message,
        capturedByWhite, capturedByBlack,
        whiteMaterialDifference, blackMaterialDifference,
        handleSquareClick, resetGame,
    } = useChessGame();
    const gameOver = gameStatus === "CHECKMATE" || gameStatus === "STALEMATE";

    return (
        <main className="chess-game" aria-label="Chess game">
            <PlayerInfo
                colour="BLACK"
                capturedPieces={capturedByBlack}
                materialDifference={blackMaterialDifference}
                isCurrentTurn={!gameOver && currentTurn === "BLACK"}
            />

            <div className="chess-board" aria-label="Chess board">
                {displaySquares.map((square) => (
                    <button
                        type="button"
                        key={`${square.row}-${square.column}`}
                        onClick={() => handleSquareClick(square)}
                        disabled={gameOver}
                        aria-label={`${String.fromCharCode(97 + square.column)}${square.row + 1}${square.piece ? ` ${square.colour} ${square.piece}` : " empty"}`}
                        aria-pressed={selectedSquare?.row === square.row && selectedSquare?.column === square.column}
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
                        {getPieceSymbol(square.piece, square.colour)}
                    </button>
                ))}
            </div>

            <PlayerInfo
                colour="WHITE"
                capturedPieces={capturedByWhite}
                materialDifference={whiteMaterialDifference}
                isCurrentTurn={!gameOver && currentTurn === "WHITE"}
            />

            <div className="game-controls">
                <div className="move-message" role="status" aria-live="polite">
                    {gameStatus === "CHECK" && "Check!"}
                    {gameStatus === "CHECKMATE" && (
                        currentTurn === "WHITE" ? "Checkmate! Black wins!" : "Checkmate! White wins!"
                    )}
                    {gameStatus === "STALEMATE" && "Stalemate - draw!"}
                    {message && <p>{message}</p>}
                </div>
                <button className="new-game" onClick={resetGame}>New Game</button>
            </div>
        </main>
    );
}
