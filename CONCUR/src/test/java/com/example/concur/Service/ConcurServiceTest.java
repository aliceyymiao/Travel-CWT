package com.example.concur.Service;

import com.example.concur.Entity.Address;
import com.example.concur.Entity.User;
import com.example.concur.Repository.JPAUserRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ConcurServiceTest {

    @InjectMocks
    private ConcurService concurService;

    @Mock
    private JPAUserRepo jpaUserRepo;

    private User user;

    @Before
    public void setUp() {
        Address address = new Address("test street", "test suite", "test city", "test zipcode");
        user = new User("test name", "test username", "test@email.com", 1, address, "test phone", "test website");
        user.setAddress(address);
        Mockito.when(jpaUserRepo.findById(anyString())).thenReturn(Optional.of(user));
    }

    @After
    public void tearDown() {
        jpaUserRepo.deleteAll();
    }

    @Test
    public void testSignUpUser(){
        String result = concurService.signUpUser(user);
        assertEquals("You have signed up successfully", result);
        Mockito.verify(jpaUserRepo, times(1)).save(any(User.class));

    }

    @Test
    public void testGetUserByEmail() {
        User loadedUser = concurService.getUserByEmail("test@email.com");
        assertEquals(loadedUser.getName(), user.getName());
    }

    @Test
    public void testUpdateUserByEmail() {
        Optional<String> name = Optional.of("updated name");
        Optional<String> username = Optional.of("updated username");
        Optional<String> street = Optional.of("updated street");
        concurService.updateUserByEmail("test@email.com", name, username, Optional.empty(), street, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        User updatedUser = concurService.getUserByEmail("test@email.com");
        assertNotNull(updatedUser);
        assertEquals(updatedUser.getName(), "updated name");
        assertEquals(updatedUser.getUsername(), "updated username");
        assertEquals(updatedUser.getAddress().getStreet(), "updated street");
    }

    @Test
    public void testDeleteUserByEmail() {
        concurService.deleteUserByEmail("test@email.com");
        User deletedUser = concurService.getUserByEmail("test@email.com");
        deletedUser = null;
        assertNull(deletedUser);
    }

}