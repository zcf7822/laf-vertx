package com.jd.laf.web.vertx;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 消息处理插件管理器
 */
public abstract class MessageHandlers {

    //类对应的绑定器
    protected static volatile Map<String, MessageHandler> plugins;

    /**
     * 获取绑定器
     *
     * @param name 类型
     * @return
     */
    public static MessageHandler getPlugin(final String name) {
        if (name == null) {
            return null;
        }
        if (plugins == null) {
            //加载插件
            synchronized (MessageHandlers.class) {
                if (plugins == null) {
                    Map<String, MessageHandler> result = new HashMap<String, MessageHandler>();
                    //加载插件
                    ServiceLoader<MessageHandler> loader = ServiceLoader.load(MessageHandler.class, MessageHandlers.class.getClassLoader());
                    for (MessageHandler handler : loader) {
                        result.put(handler.type(), handler);
                    }
                    plugins = result;
                }
            }
        }

        //获取适合的插件
        return plugins.get(name);
    }

}
