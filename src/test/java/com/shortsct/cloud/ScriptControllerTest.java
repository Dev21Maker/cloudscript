package com.shortsct.cloud;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortsct.cloud.controller.ScriptContoller;
import com.shortsct.cloud.model.Script;
import com.shortsct.cloud.repository.ScriptsRepository;

@WebMvcTest(ScriptContoller.class)
public class ScriptControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ScriptsRepository scriptsRepository;

    @BeforeEach
    public void setUp() {
        // Initialize MockMvc if not using @WebMvcTest
        // this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllDrafts() throws Exception {
        // Mock the repository response
        when(scriptsRepository.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/drafts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testCreateDraft() throws Exception {
        Script script = new Script("Typical Script", "http://www.example.com", "lala@snail.com", "12345");
        when(scriptsRepository.save(script)).thenReturn(script);
        when(scriptsRepository.findById(script.getId())).thenReturn(java.util.Optional.of(script));
        mockMvc.perform(post("/api/v1/drafts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(script)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Typical Script"));
    }

    @Test
    public void testDeleteDraft() throws Exception {
        Script script = new Script("Typical Script", "http://www.example.com", "lala@snail.com", "12345");
        when(scriptsRepository.save(script)).thenReturn(script);
        scriptsRepository.save(script);
        mockMvc.perform(post("/api/v1/drafts" + 1))
                .andDo(handler -> {
                    System.out.println("Messge " + handler.getResponse().getContentAsString());
                })
                .andExpect(status().isNotFound());
    }
}
