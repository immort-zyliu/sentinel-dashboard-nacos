package com.alibaba.csp.sentinel.dashboard.nacos.provider;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  17:02
 * @since 1.8
 * 基于 nacos 的规则提供者
 */
public interface DynamicRuleNacosProvider<T> {


    /**
     * @param app  app name
     * @param ip   machine client IP（client 端 接收dashboard请求的那个ip）
     * @param port machine client port（client 端 接收dashboard请求的那个port）
     * @throws Exception if some error occurs
     */
    T getRules(String app, String ip, Integer port) throws Exception;
}
