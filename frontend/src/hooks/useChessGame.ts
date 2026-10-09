import { useEffect, useState } from "react";
import type { BoardSquare } from "../types/BoardSquare";
import type { CapturedPiece, CapturedPiecesResponse } from "../types/CapturedPiece";

export function useChessGame() {
    const [squares, setSquares] = useState<BoardSquare[]>([]);
    const [selectedSquare, setSelectedSquare] =
        useState<BoardSquare | null>(null);

    const [message, setMessage] = useState<string>("");
    const [capturedByWhite, setCapturedByWhite] = useState<CapturedPiece[]>([]);
    const [capturedByBlack, setCapturedByBlack] = useState<CapturedPiece[]>([]);
    const [whiteMaterialDifference, setWhiteMaterialDifference] = useState(0);
    const [blackMaterialDifference, setBlackMaterialDifference] = useState(0);

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

    function fetchCapturedPieces() {
        fetch("/api/board/captured")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Could not load captured pieces");
                }
                return response.json();
            })
            .then((data: CapturedPiecesResponse) => {
                setCapturedByWhite(data.capturedByWhite);
                setCapturedByBlack(data.capturedByBlack);
                setWhiteMaterialDifference(data.whiteMaterialDifference);
                setBlackMaterialDifference(data.blackMaterialDifference);
            })
            .catch(() => setMessage("Could not load captured pieces"));
    }

    useEffect(() => {
        fetchBoard();
        fetchCurrentTurn();
        fetchGameStatus();
        fetchCapturedPieces();
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
                    fetchCapturedPieces();
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
        fetchCapturedPieces();
    });
}

    return {
        displaySquares,
        selectedSquare,
        currentTurn,
        gameStatus,
        capturedByWhite,
        capturedByBlack,
        whiteMaterialDifference,
        blackMaterialDifference,
        message,
        handleSquareClick,
        resetGame,
    };
}
