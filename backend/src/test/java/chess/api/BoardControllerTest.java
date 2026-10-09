package chess.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BoardControllerTest {
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new BoardController()).build();
    }

    @Test
    void capturedEndpointReturnsDtosAndResetsMaterial() throws Exception {
        mvc.perform(get("/api/board/captured"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"capturedByWhite":[],"capturedByBlack":[],
                         "whiteMaterialDifference":0,"blackMaterialDifference":0}
                        """, JsonCompareMode.STRICT));
        move(1, 4, 3, 4);
        move(6, 3, 4, 3);
        move(3, 4, 4, 3);
        mvc.perform(get("/api/board/captured"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"capturedByWhite":[{"piece":"Pawn","colour":"BLACK","value":1}],
                         "capturedByBlack":[],"whiteMaterialDifference":1,"blackMaterialDifference":-1}
                        """, JsonCompareMode.STRICT));
        move(7, 3, 4, 3);
        mvc.perform(get("/api/board/captured"))
                .andExpect(jsonPath("$.capturedByBlack[0].colour").value("WHITE"))
                .andExpect(jsonPath("$.whiteMaterialDifference").value(0))
                .andExpect(jsonPath("$.blackMaterialDifference").value(0));
        mvc.perform(post("/api/board/reset")).andExpect(status().isOk());
        mvc.perform(get("/api/board/captured"))
                .andExpect(content().json("""
                        {"capturedByWhite":[],"capturedByBlack":[],
                         "whiteMaterialDifference":0,"blackMaterialDifference":0}
                        """, JsonCompareMode.STRICT));
    }

    private void move(int fromRow, int fromColumn, int toRow, int toColumn) throws Exception {
        mvc.perform(post("/api/board/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"fromRow":%d,"fromColumn":%d,"toRow":%d,"toColumn":%d}
                        """.formatted(fromRow, fromColumn, toRow, toColumn)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void lowercaseEndpointsSupportMoveAndReset() throws Exception {
        mvc.perform(get("/api/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(64));
        mvc.perform(get("/api/board/turn"))
                .andExpect(status().isOk())
                .andExpect(content().json("\"WHITE\""));
        mvc.perform(get("/api/board/status"))
                .andExpect(status().isOk())
                .andExpect(content().json("\"ACTIVE\""));
        mvc.perform(post("/api/board/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fromRow\":1,\"fromColumn\":4,\"toRow\":3,\"toColumn\":4}"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        mvc.perform(get("/api/board/turn"))
                .andExpect(content().json("\"BLACK\""));
        mvc.perform(post("/api/board/reset"))
                .andExpect(status().isOk());
        mvc.perform(get("/api/board/turn"))
                .andExpect(content().json("\"WHITE\""));
        mvc.perform(get("/api/board/status"))
                .andExpect(content().json("\"ACTIVE\""));
        mvc.perform(get("/api/board"))
                .andExpect(jsonPath("$[12].piece").value("Pawn"))
                .andExpect(jsonPath("$[28].piece").isEmpty());
        mvc.perform(get("/api/board/Turn")).andExpect(status().isNotFound());
        mvc.perform(get("/api/board/Status")).andExpect(status().isNotFound());
    }
}
