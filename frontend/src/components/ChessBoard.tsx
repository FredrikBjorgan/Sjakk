import { useEffect, useState } from "react";
import type { BoardSquare } from "../types/BoardSquare";

function getPieceSymbol(
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

export default function ChessBoard() {

    const [squares, setSquares] = useState<BoardSquare[]>([]);
    const [selectedSquare, setSelectedSquare] =
        useState<BoardSquare | null>(null);
    const [message, setMessage] = useState<string>("");

    function fetchBoard() {
        fetch("/api/board")
            .then((response) => response.json())
            .then((data) => setSquares(data));
    }

    useEffect(() => {
        fetchBoard();
    }, []);

    function handleSquareClick(square: BoardSquare) {

        // Første klikk
        if (selectedSquare === null) {

            if (square.piece !== null) {
                setSelectedSquare(square);
            }

            return;
        }

        // Klikk på samme rute igjen
        if (
            selectedSquare.row === square.row &&
            selectedSquare.column === square.column
        ) {
            setSelectedSquare(null);
            return;
        }

        // Andre klikk -> send trekket til backend
        fetch("/api/board/move", {
            method: "POST",

            headers: {
                "Content-Type": "application/json",
            },

            body: JSON.stringify({
                fromRow: selectedSquare.row,
                fromColumn: selectedSquare.column,
                toRow: square.row,
                toColumn: square.column,
            }),
        })
            .then((response) => response.json())
            .then((moveWasValid) => {

                if (moveWasValid) {
                    setMessage("");
                    fetchBoard();
                } else {
                    setMessage("Invalid move");
                }

                setSelectedSquare(null);
            });
    }

    const displaySquares = [...squares].sort((a, b) => {
        if (a.row !== b.row) {
            return b.row - a.row;
        }

        return a.column - b.column;
    });

    return (
        <div>
            <h2>Chess Board</h2>

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