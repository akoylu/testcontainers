package akoylu.tc;

import akoylu.tc.api.domain.Book;
import akoylu.tc.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@Slf4j
class TcApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    void contextLoads() {
        var book = new Book();
        book.setName("testContainers");

        bookRepository.save(book);

        log.info("Context loaded!");
    }

}
