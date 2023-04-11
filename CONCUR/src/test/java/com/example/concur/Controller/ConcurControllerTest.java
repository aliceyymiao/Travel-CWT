package com.example.concur.Controller;

import com.example.concur.Config.SpringUnitTestBase;
import com.example.concur.Serializer.Decode;
import com.example.concur.Service.ConcurService;
import com.example.concur.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
public class ConcurControllerTest extends SpringUnitTestBase {

    @Autowired
    ConcurController concurController;

    @MockBean
    ConcurService concurService;

    @Mock
    Decode decode;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        User user = new User();
        user.setEmail("Sincere@april.biz");
        user.setName("Leanne Graham");
        when(concurService.getUserByEmail(anyString()))
                .thenReturn(user);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUpUser() throws Exception {
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");

        when(concurService.signUpUser(any(User.class))).thenReturn("You have signed up successfully!");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        mockMvc = MockMvcBuilders.standaloneSetup(concurController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("You have signed up successfully!"));
    }

    @Test
    public void getUserByEmail() {
        String email = "Sincere@april.biz";
        User user = concurController.getUserByEmail(email);
        assertThat(user.getName()).isEqualTo("Leanne Graham");
    }

    @Test
    public void testUpdateUserById() throws Exception {
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");
        Optional<String> name = null;

        String result = concurController.updateUserById("john@example.com", name,null,null,null,
                null,null,null,null, null);

        assertEquals(result, "You have updated your information successfully!");
        mockMvc = MockMvcBuilders.standaloneSetup(concurController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/user/update/john@example.com")
                        .param("name", "John")
                        .param("city", "New York"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("You have updated your information successfully!"));
    }

    @Test
    public void testDeleteUserByEmail() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(concurController).build();
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/john@example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("The user has been deleted successfully!"));
    }

    @Test
    public void getUserInfo_withValidToken_shouldReturnSuccessMessage() {
        // Arrange
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("email", "test@example.com")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(8l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();

        // Act
        String result = concurController.getUserInfo(jwtToken);

        // Assert
        assertEquals("The token is valid! Fetching user information and sending back to Customer System. Check Concur console for any exceptions.", result);
        verify(concurService, times(1)).getUserInfo("test@example.com");
    }

}
