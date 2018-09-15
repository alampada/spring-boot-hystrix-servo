package com.example.demo;

import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Replace existing Micrometer Metrics Publisher coming from the actuator with servo.
 */
@Configuration
public class DemoApplicationConfiguration {

  @PostConstruct
  public void registerHystrixServo(){
    final HystrixPlugins hystrixPlugins = HystrixPlugins.getInstance();
    if (hystrixPlugins.getMetricsPublisher() == null) {
      hystrixPlugins.registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
    }
    else {
      final HystrixEventNotifier hystrixEventNotifier = hystrixPlugins.getEventNotifier();
      final HystrixConcurrencyStrategy hystrixConcurrencyStrategy = hystrixPlugins.getConcurrencyStrategy();
      final HystrixPropertiesStrategy hystrixPropertiesStrategy = hystrixPlugins.getPropertiesStrategy();
      final HystrixCommandExecutionHook hystrixCommandExecutionHook = hystrixPlugins.getCommandExecutionHook();
      HystrixPlugins.reset();
      hystrixPlugins.registerEventNotifier(hystrixEventNotifier);
      hystrixPlugins.registerConcurrencyStrategy(hystrixConcurrencyStrategy);
      hystrixPlugins.registerPropertiesStrategy(hystrixPropertiesStrategy);
      hystrixPlugins.registerCommandExecutionHook(hystrixCommandExecutionHook);
      hystrixPlugins.registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
    }
  }
}
