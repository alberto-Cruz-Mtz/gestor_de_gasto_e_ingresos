package com.example.backend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void passing() {
    System.out.println("testing");
    assertTrue(true, "something is worng");
  }

  @Test
  void failing() {
    System.out.println("testing");
    assertTrue(false, "something is wrong");
  }
}
