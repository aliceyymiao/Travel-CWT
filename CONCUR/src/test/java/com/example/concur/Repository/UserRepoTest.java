package com.example.concur.Repository;


import com.example.concur.Entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JPAUserRepo userRepo;

    @Before
    public void setup() {
        entityManager.clear();
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User user = new User();
        user.setEmail("aaa@gmail.com");
        user.setName("alex");
        User alex = new User();
        userRepo.save(user);

        // when
        Optional<User> result = userRepo.findById("aaa@gmail.com");
        if (result.isPresent()) {
            alex = result.get();
        }

        // then
        assertThat(alex.getName()).isEqualTo("alex");
    }

}
