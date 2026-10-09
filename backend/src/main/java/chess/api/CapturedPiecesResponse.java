package chess.api;

import java.util.List;

public record CapturedPiecesResponse(
        List<CapturedPieceResponse> capturedByWhite,
        List<CapturedPieceResponse> capturedByBlack,
        int whiteMaterialDifference,
        int blackMaterialDifference
) {}
