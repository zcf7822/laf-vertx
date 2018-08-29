package com.jd.laf.web.vertx.handler;

import com.jd.laf.web.vertx.SystemContext;
import com.jd.laf.web.vertx.SystemAware;
import com.jd.laf.web.vertx.RoutingHandler;
import io.vertx.core.Handler;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.web.RoutingContext;

import static com.jd.laf.web.vertx.SystemContext.*;
import static io.vertx.ext.web.handler.FormLoginHandler.*;

/**
 * 表单登录处理器资源
 */
public class FormLoginHandler implements RoutingHandler, SystemAware {

    public static final String FORM_LOGIN = "formLogin";

    protected Handler<RoutingContext> handler;

    @Override
    public String type() {
        return FORM_LOGIN;
    }

    @Override
    public void setup(final SystemContext context) {
        AuthProvider authProvider = context.getObject(AUTH_PROVIDER, AuthProvider.class);
        String userParam = context.getString(AUTH_USER_PARAM, DEFAULT_USERNAME_PARAM);
        String passwordParam = context.getString(AUTH_PASSWORD_PARAM, DEFAULT_PASSWORD_PARAM);
        String returnUrlParam = context.getString(AUTH_RETURN_URL_PARAM, DEFAULT_RETURN_URL_PARAM);
        handler = io.vertx.ext.web.handler.FormLoginHandler.create(authProvider, userParam, passwordParam, returnUrlParam, null);
    }

    @Override
    public void handle(final RoutingContext context) {
        handler.handle(context);
    }
}