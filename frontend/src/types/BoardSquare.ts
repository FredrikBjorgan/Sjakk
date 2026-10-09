export type BoardSquare = {
    row: number;
    column: number;
    piece: string | null;
    colour: "WHITE" | "BLACK" | null;
};