package org.unbrokendome.vertx.spring;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.spi.VertxMetricsFactory;
import io.vertx.core.spi.metrics.VertxMetrics;
import org.unbrokendome.vertx.spring.metrics.VertxMetricsAdapter;

import java.util.List;
import java.util.function.Consumer;

public class VertxListenerAwareMetricsFactory implements VertxMetricsFactory {

    public static final String VERTICLE_DEPLOYED = "verticleDeployed";
    public static final String VERTICLE_UNDEPLOYED = "verticleUndeployed";
    public static final String VERTX_STOPPED = "vertxStopped";
    public static final String VERTX_STARTED = "vertxStarted";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<? extends VertxListener> listeners;

    public VertxListenerAwareMetricsFactory(List<? extends VertxListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public VertxMetrics metrics(final Vertx vertx, final VertxOptions options) {
        dispatch(VERTX_STARTED, listener -> listener.vertxStarted(vertx, options));
        return new ListenerAwareVertxMetrics(vertx);
    }

    /**
     * 分发消息
     *
     * @param eventName
     * @param listenerAction
     */
    protected void dispatch(final String eventName, final Consumer<VertxListener> listenerAction) {
        for (VertxListener listener : listeners) {
            try {
                listenerAction.accept(listener);
            } catch (Throwable t) {
                logger.error(String.format("Error in VertxListener %s while handling %s event", listener, eventName), t);
            }
        }
    }

    protected class ListenerAwareVertxMetrics implements VertxMetricsAdapter {

        protected final Vertx vertx;

        public ListenerAwareVertxMetrics(Vertx vertx) {
            this.vertx = vertx;
        }

        @Override
        public void verticleDeployed(final Verticle verticle) {
            dispatch(VERTICLE_DEPLOYED, listener -> listener.verticleDeployed(verticle, Vertx.currentContext()));
        }

        @Override
        public void verticleUndeployed(final Verticle verticle) {
            dispatch(VERTICLE_UNDEPLOYED, listener -> listener.verticleUndeployed(verticle, Vertx.currentContext()));
        }

        @Override
        public void close() {
            dispatch(VERTX_STOPPED, listener -> listener.vertxStopped(vertx));
        }
    }
}
