package com.jitterted.ebp.blackjack.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class WebTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getOfHomePageIsStatus200Ok() throws Exception {
        mockMvc.perform(get("/index.html"))
               .andExpect(status().isOk());
    }

    @Test
    void postToStartGameIs3xxRedirect() throws Exception {
        mockMvc.perform(post("/start-game"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/game"));
    }

    @Test
    void testGameEndpointIs200Ok() throws Exception {
        mockMvc.perform(get("/game"))
               .andExpect(status().isOk());
    }

    @Test
    void postToHitEndpointRedirectsToGameView() throws Exception {
        mockMvc.perform(post("/hit"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    void testThatDoneShowsRelevantInformation() throws Exception {
        mockMvc.perform(get("/done"))
               .andExpect(model().attributeExists("outcome"))
               .andExpect(model().attributeExists("gameView"))
               .andExpect(status().isOk());
    }

    @Test
    void testWhenWeStandWeRedirectBackToTheGame() throws Exception {
        mockMvc.perform(post("/stand"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/done"));
    }
}
