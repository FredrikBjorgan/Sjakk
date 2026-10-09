export type CapturedPiece = {
    piece: string;
    colour: "WHITE" | "BLACK";
    value: number;
};

export type CapturedPiecesResponse = {
    capturedByWhite: CapturedPiece[];
    capturedByBlack: CapturedPiece[];
    whiteMaterialDifference: number;
    blackMaterialDifference: number;
};
