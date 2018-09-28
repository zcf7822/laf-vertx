package com.jd.laf.vertx.spring.events;

import io.vertx.core.Context;
import io.vertx.core.Verticle;
import org.springframework.context.ApplicationEvent;

/**
 * 抽象的执行器事件
 */
public abstract class AbstractVerticleEvent extends ApplicationEvent {

    private final Context context;

    protected AbstractVerticleEvent(Verticle verticle, Context context) {
        super(verticle);
        this.context = context;
    }

    public final Verticle getVerticle() {
        return (Verticle) getSource();
    }

    public final Context getContext() {
        return context;
    }
}
