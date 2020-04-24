package pl.com.muca.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserControllerTest {

  private MockMvc mockMvc;


  @BeforeMethod
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(new UserControllerTest())
        .build();
  }

  @Test
  public void test() throws Exception {
    ResultMatcher expectedResult = content()
        .string("I'm just a simple string test... :)");

    MockHttpServletRequestBuilder actualResult = get("/testString");

    this.mockMvc.perform(actualResult).andExpect(status().isOk())
        .andExpect(expectedResult);
  }
}
