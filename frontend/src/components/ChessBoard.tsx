import { useChessGame } from "../hooks/useChessGame";
import { getPieceSymbol } from "../utils/pieceSymbols";

export default function ChessBoard() {

    const {
        displaySquares,
        selectedSquare,
        currentTurn,
        gameStatus,
        message,
        handleSquareClick,
        resetGame,
    } = useChessGame();

    return (
        <div>
            <h2>Chess Board</h2>
            <button onClick={resetGame}>New Game</button>

            <p className="current-turn">
                {currentTurn === "WHITE"
                    ? "White to move"
                    : "Black to move"}
            </p>

                                {gameStatus === "CHECK" && (
                        <p className="move-message">
                            Check!
                        </p>
                    )}

                    {gameStatus === "CHECKMATE" && (
                        <p className="move-message">
                            Checkmate!{" "}
                            {currentTurn === "WHITE"
                                ? "Black wins!"
                                : "White wins!"}
                        </p>
                    )}

                    {gameStatus === "STALEMATE" && (
                        <p className="move-message">
                            Stalemate - draw!
                        </p>
                    )}

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
