package com.alibaba.csp.sentinel.dashboard.nacos.provider;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  17:02
 * @since 1.8
 * 基于 nacos 的规则提供者
 */
public interface DynamicRuleNacosProvider<T> {


    /**
     * @param app app name
     * @throws Exception if some error occurs
     */
    T getRules(String app, String ip, Integer port) throws Exception;
}
