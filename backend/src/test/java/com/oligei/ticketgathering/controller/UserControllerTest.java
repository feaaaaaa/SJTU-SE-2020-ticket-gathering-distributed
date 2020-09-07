/**
 * @ClassName User
 * @Description User Controller Test
 * @Author ziliuziliu
 * @Date 2020/7/15
 */

package com.oligei.ticketgathering.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.function.latebinding.JsonLateBindingValue;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.service.UserService;
import com.oligei.ticketgathering.util.msgutils.Msg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    private String username1 = "ziliuziliu";
    private String username2 = "zlzl";
    private String password1 = "1234567";
    private String password2 = "123456";
    private Integer userId1 = 1;
    private Integer userId2 = 2;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    JSONObject loginMock(String username, String password) throws Exception{
        MvcResult result = mockMvc.perform(get("/User/Login?username="+username+"&password="+password)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void login(){
        User tmp = new User(null,username1,null,null,null,encoder.encode(password1),
                username1,null);
        JSONObject msg = null;

        when(userService.login(username1,password1)).thenReturn(tmp);
        when(userService.login(anyString(),argThat(password -> Objects.equals(password,password2)))).thenReturn(null);
        when(userService.login(argThat(username -> Objects.equals(username,username2)),anyString())).thenReturn(null);

        System.out.println("Correct username, Correct password");
        try {
            msg = loginMock(username1, password1);
            assertEquals(200,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}

        System.out.println("Correct username, Wrong password");
        try {
            msg = loginMock(username1, password2);
            assertEquals(201,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}

        System.out.println("Wrong username, Correct password");
        try {
            msg = loginMock(username2, password1);
            assertEquals(201,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}

        System.out.println("Correct username, Correct password");
        try {
            msg = loginMock(username2, password2);
            assertEquals(201,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}
    }

    JSONObject registerMock(User user) throws Exception{
        MvcResult result = mockMvc.perform(post("/User/Register").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void register() {
        JSONObject msg = null;
        User tmp = new User(null,username1,"123","123","123",encoder.encode(password1),
                "success","123");

        when(userService.register(tmp)).thenReturn(true);
        when(userService.register(argThat(user -> !Objects.equals(user,tmp)))).thenReturn(false);

        System.out.println("User not null");
        try {
            msg = registerMock(tmp);
            assertEquals(200,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}
    }

    JSONObject existsByUsernameMock(String username) throws Exception{
        MvcResult result = mockMvc.perform(get("/User/ExistsByUsername?username="+username)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void existsByUsername() {
        JSONObject msg = null;

        when(userService.existsByUsername(username1)).thenReturn(true);
        when(userService.existsByUsername(argThat(username -> !Objects.equals(username,username1)))).thenReturn(false);

        System.out.println("Correct username");
        try {
            msg = existsByUsernameMock(username1);
            assertEquals(200,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}

        System.out.println("Wrong username");
        try {
            msg = existsByUsernameMock(username2);
            assertEquals(201,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}
    }

    JSONObject findUserByUserIdMock(Integer userId) throws Exception{
        MvcResult result = mockMvc.perform(get("/User/FindByUserId?userId="+userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void findUserByUserId() {
        JSONObject msg = null;
        User tmp = new User(null,username1,"123","123","123",encoder.encode(password1),
                "success","123");

        when(userService.findUserByUserId(userId1)).thenReturn(tmp);
        when(userService.findUserByUserId(argThat(userId -> !Objects.equals(userId,userId1)))).thenReturn(null);

        System.out.println("Correct userId");
        try {
            msg = findUserByUserIdMock(userId1);
            assertEquals(200,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}

        System.out.println("Wrong userId");
        try {
            msg = findUserByUserIdMock(userId2);
            assertEquals(201,msg.get("status"));
        }
        catch (Exception e) {e.printStackTrace();}
    }

    @Test
    @Rollback
    void rechargeOrDeduct() throws Exception {
        when(userService.rechargeOrDeduct(1,100)).thenReturn(600);
        MvcResult result = mockMvc.perform(
                post("/User/rechargeOrDeduct")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("userId","1")
                        .param("increment","100")
        )
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = om.readValue(resultContent, new TypeReference<JSONObject>() {});
        assertEquals(600,jsonObject.get("data"));
    }
}
