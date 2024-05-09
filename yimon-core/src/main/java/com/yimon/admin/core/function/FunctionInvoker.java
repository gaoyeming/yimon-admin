package com.yimon.admin.core.function;

@FunctionalInterface
public interface FunctionInvoker<T, R> {

    R invoke(T t) throws Exception;

}