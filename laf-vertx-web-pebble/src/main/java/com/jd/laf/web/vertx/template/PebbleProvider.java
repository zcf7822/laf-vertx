package com.jd.laf.web.vertx.template;

import com.jd.laf.web.vertx.SystemContext;
import com.jd.laf.web.vertx.TemplateProvider;
import io.vertx.ext.web.templ.PebbleTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

import static com.jd.laf.web.vertx.SystemContext.TEMPLATE_CACHE_SIZE;
import static com.jd.laf.web.vertx.SystemContext.TEMPLATE_EXTENSION;
import static io.vertx.ext.web.templ.PebbleTemplateEngine.DEFAULT_MAX_CACHE_SIZE;
import static io.vertx.ext.web.templ.PebbleTemplateEngine.DEFAULT_TEMPLATE_EXTENSION;

/**
 * Pebble引擎
 */
public class PebbleProvider implements TemplateProvider {

    public static final String PEBBLE = "pebble";

    @Override
    public TemplateEngine create(final SystemContext context) {
        PebbleTemplateEngine engine = PebbleTemplateEngine.create(context.getVertx());
        engine.setExtension(context.getString(TEMPLATE_EXTENSION, DEFAULT_TEMPLATE_EXTENSION));
        engine.setMaxCacheSize(context.getPositive(TEMPLATE_CACHE_SIZE, DEFAULT_MAX_CACHE_SIZE));
        return engine;
    }

    @Override
    public String type() {
        return PEBBLE;
    }
}