package com.codeproject.changemaker;


import com.codeproject.changemaker.controller.ActionController;
import com.codeproject.changemaker.domain.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;



@WebMvcTest(ActionController.class)
public class ActionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Coin coin;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    ActionController actionController;


    @Test
    public void getCurrentCoinsTest() throws Exception {
//        given(actionController.getCurrentCoins()).willReturn(coin);
        coin = new Coin();
        //Mockito.when(actionController.getCurrentCoins()).thenReturn(coin);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/currentCoins"));
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$.quarters", is(0)))
//                .andExpect(jsonPath("$.dimes", is(0)))
//                .andExpect(jsonPath("$.nickels", is(0)))
//                .andExpect(jsonPath("$.pennies", is(0)));

    }

}

