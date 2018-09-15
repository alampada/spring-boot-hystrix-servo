package com.example.demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class DemoHystrixCommandService {

  @HystrixCommand(fallbackMethod = "fallback")
  public String doSomething() {
    int random = ThreadLocalRandom.current().nextInt(3);
    if (random == 0) {
      return "foo";
    }
    else if (random == 1) {
      throw new HystrixBadRequestException("bad request");
    }
    else {
      throw new RuntimeException("foo");
    }
  }

  public String fallback() {
    return "fallback";
  }
}
