package com.example.first_camel_case.tracker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class Graphics2DTrackerAspect {
    private static final ConcurrentHashMap<Integer, Graphics2D> activeGraphicsInstances = new ConcurrentHashMap<>();
    private static int counter = 0;

    @Around("call(java.awt.Graphics2D java.awt.image.BufferedImage.createGraphics())")
    public Object trackGraphics2D(ProceedingJoinPoint joinPoint) throws Throwable {
        Graphics2D g = (Graphics2D) joinPoint.proceed();
        int id = counter++;
        activeGraphicsInstances.put(id, g);
        System.out.println("[AOP] Graphics2D instance created: " + g + " | ID: " + id + " | Active: " + activeGraphicsInstances.size());
        return g;
    }

    @AfterReturning("call(void java.awt.Graphics.dispose()) && target(java.awt.Graphics2D)")
    public void trackDispose(Graphics2D g) {
        activeGraphicsInstances.values().removeIf(instance -> instance == g);
        System.out.println("[AOP] Graphics2D instance disposed: " + g + " | Remaining: " + activeGraphicsInstances.size());
    }

    public static int getActiveGraphicsCount() {
        return activeGraphicsInstances.size();
    }
}
