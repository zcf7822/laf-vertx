package com.jd.laf.vertx.spring.events;

import io.vertx.core.Vertx;
import org.springframework.context.ApplicationEvent;


/**
 * 抽象的Vertx事件
 */
public abstract class AbstractVertxEvent extends ApplicationEvent {

    protected AbstractVertxEvent(Vertx vertx) {
        super(vertx);
    }

    public final Vertx getVertx() {
        return (Vertx) getSource();
    }
}
