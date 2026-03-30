package org.example.hw_21_03;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
class HelloSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloSpringApplication.class, args);
    }
}

@Aspect
@Component
class LoggingAspect {

    // время начала выполнения (для каждого потока)
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    // счетчик вызовов методов
    private Map<String, Integer> methodCounts = new ConcurrentHashMap<>();

    @Before("execution(* org.example.hw_21_03.HelloController.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();

        // сохраняем время старта
        startTime.set(System.currentTimeMillis());

        // увеличиваем счетчик
        methodCounts.merge(methodName, 1, Integer::sum);

        System.out.println(">>> Заходимо в метод: " + methodName);
        System.out.println("Кількість викликів: " + methodCounts.get(methodName));
    }

    @After("execution(* org.example.hw_21_03.HelloController.*(..))")
    public void logAfterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();

        long executionTime = System.currentTimeMillis() - startTime.get();

        System.out.println("<<< Виходимо з методу: " + methodName);
        System.out.println("Час виконання: " + executionTime + " мс");
        System.out.println("-----------------------------------");
    }
}

@RestController
class HelloController {
    @GetMapping("/")
    public String sayHello() {
        return "Hello Spring with AOP!";
    }
}

// автоматичне відкриття браузера після запуску додатку
@Component
class BrowserLauncher {
    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() {
        System.setProperty("java.awt.headless", "false");
        var desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI("http://localhost:8080"));
        } catch (IOException | URISyntaxException e) {
        }
    }
}