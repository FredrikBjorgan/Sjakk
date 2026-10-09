import { useEffect, useState } from "react";
import type { BoardSquare } from "../types/BoardSquare";

export function useChessGame() {
    const [squares, setSquares] = useState<BoardSquare[]>([]);
    const [selectedSquare, setSelectedSquare] =
        useState<BoardSquare | null>(null);

    const [message, setMessage] = useState<string>("");

    const [currentTurn, setCurrentTurn] =
        useState<"WHITE" | "BLACK">("WHITE");

    const [gameStatus, setGameStatus] =
    useState<"ACTIVE" | "CHECK" | "CHECKMATE" | "STALEMATE">(
        "ACTIVE"
    );    

    function fetchBoard() {
        fetch("/api/board")
            .then((response) => response.json())
            .then((data) => setSquares(data));
    }

    function fetchCurrentTurn() {
        fetch("/api/board/turn")
            .then((response) => response.json())
            .then((data) => setCurrentTurn(data));
    }

    function fetchGameStatus() {
    fetch("/api/board/status")
        .then((response) => response.json())
        .then((data) => setGameStatus(data));
}

    useEffect(() => {
        fetchBoard();
        fetchCurrentTurn();
        fetchGameStatus();
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
                    fetchCurrentTurn();
                    fetchGameStatus();
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

    function resetGame() {

    fetch("/api/board/reset", {
        method: "POST",
    }).then(() => {

        setSelectedSquare(null);
        setMessage("");

        fetchBoard();
        fetchCurrentTurn();
        fetchGameStatus();
    });
}

    return {
        displaySquares,
        selectedSquare,
        currentTurn,
        gameStatus,
        message,
        handleSquareClick,
        resetGame,
    };
}
