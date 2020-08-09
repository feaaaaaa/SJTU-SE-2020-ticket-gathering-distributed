/**
 * @ClassName User
 * @Description User Controller Test
 * @Author ziliuziliu
 * @Date 2020/7/15
 */

package com.oligei.ticketgathering.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Rollback
    void login() throws Exception{
        System.out.println("Correct username, Correct password");
        MvcResult result = mockMvc.perform(get("/User/Login?username=ziliuziliu&password=ziliuziliu0427!").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        User user = om.readValue(resultContent, new TypeReference<User>() {});
        assertEquals(user.getPassword(),userService.login("ziliuziliu","ziliuziliu0427!").getPassword());
    }

    @Test
    @Rollback
    void register() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String user_string = "{\"username\":\"linda\",\"gender\":\"Female\",\"email\":\"linda@qq.com\",\"phone\":\"12345\",\"password\":\"linda\",\"type\":\"User\"}";
        User user = mapper.readValue(user_string, User.class);
        MvcResult result = mockMvc.perform(post("/User/Register").contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("true",resultContent);
    }

    @Test
    @Rollback
    void existsByUsername() throws Exception {
        System.out.println("Correct username");
        MvcResult result = mockMvc.perform(get("/User/ExistsByUsername?username=ziliuziliu").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("true",resultContent);
    }
}
