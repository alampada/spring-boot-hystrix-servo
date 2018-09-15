package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  private DemoHystrixCommandService demoHystrixCommandService;

  public DemoController(DemoHystrixCommandService demoHystrixCommandService) {
    this.demoHystrixCommandService = demoHystrixCommandService;
  }

  @GetMapping("/demo")
  public String demo() {
    return demoHystrixCommandService.doSomething();
  }
}
