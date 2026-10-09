package chess.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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
