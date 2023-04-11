package com.example.concur;

import com.example.concur.Controller.ConcurControllerTest;
import com.example.concur.Repository.UserRepoTest;
import com.example.concur.Service.ConcurServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConcurControllerTest.class,
        ConcurServiceTest.class,
        UserRepoTest.class
})
class ConcurApplicationTests {

}
