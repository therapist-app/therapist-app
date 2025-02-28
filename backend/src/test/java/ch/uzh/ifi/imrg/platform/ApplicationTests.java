package ch.uzh.ifi.imrg.platform;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }
}
