package com.jd.laf.web.vertx.response;

import com.jd.laf.web.vertx.Plugin;

import java.util.List;

import static com.jd.laf.web.vertx.Plugin.THROWABLE;
import static com.jd.laf.web.vertx.response.Response.HTTP_INTERNAL_ERROR;

/**
 * 响应工具类
 */
public abstract class Responses {

    /**
     * 成功响应
     *
     * @return
     */
    public static Response success() {
        return new Response();
    }

    /**
     * 成功响应
     *
     * @param data
     * @return
     */
    public static Response success(Object data) {
        return new Response(data);
    }

    /**
     * 成功响应
     *
     * @param code
     * @param data
     * @return
     */
    public static Response success(int code, Object data) {
        return new Response(code, data);
    }

    /**
     * 成功响应
     *
     * @param code   业务代码
     * @param status http响应码
     * @param data
     * @return
     */
    public static Response success(int code, int status, Object data) {
        return new Response(code, status, data, null);
    }

    /**
     * 分页成功响应
     *
     * @param pagination 分页
     * @param data       数据
     * @return
     */
    public static Response success(final Object pagination, final List<?> data) {
        return new Response(data, pagination);
    }

    /**
     * 分页成功响应
     *
     * @param code       业务响应码
     * @param pagination 分页
     * @param data       数据
     * @return
     */
    public static Response success(final int code, final Object pagination, final List<?> data) {
        return new Response(code, data, pagination);
    }

    /**
     * 分页成功响应
     *
     * @param code       业务响应码
     * @param pagination 分页
     * @param data       数据
     * @return
     */
    public static Response success(final int code, int status, final Object pagination, final List<?> data) {
        return new Response(code, status, data, pagination);
    }

    /**
     * 异常响应
     *
     * @param code    代码
     * @param message 消息
     * @return
     */
    public static Response error(int code, String message) {
        return new Response(code, message);
    }

    /**
     * 异常响应
     *
     * @param code    代码
     * @param status  http响应码
     * @param message 消息
     * @return
     */
    public static Response error(int code, int status, String message) {
        return new Response(code, status, message);
    }

    /**
     * 异常响应
     *
     * @param throwable 异常
     * @return
     */
    public static Response error(Throwable throwable) {
        return error(throwable, null, HTTP_INTERNAL_ERROR, HTTP_INTERNAL_ERROR);
    }

    /**
     * 异常响应
     *
     * @param throwable 异常
     * @param supplier  异常转换
     * @return
     */
    public static Response error(Throwable throwable, ErrorSupplier supplier) {
        return error(throwable, supplier, HTTP_INTERNAL_ERROR, HTTP_INTERNAL_ERROR);
    }

    /**
     * 异常响应
     *
     * @param throwable
     * @param supplier      异常转换
     * @param defaultCode   默认业务响应代码
     * @param defaultStatus 默认HTTP响应代码
     * @return
     */
    public static Response error(Throwable throwable, ErrorSupplier supplier, int defaultCode, int defaultStatus) {
        if (throwable == null) {
            return error(defaultCode, defaultStatus, "unknown error.");
        } else if (supplier != null) {
            return supplier.error(throwable);
        } else {
            supplier = THROWABLE.select(throwable.getClass());
            if (supplier != null) {
                return supplier.error(throwable);
            } else {
                //not found supplier for throwable , should print the error stack
                return new ErrorResponse(defaultCode,defaultStatus,throwable.getMessage(),true);
            }
        }
    }


}
