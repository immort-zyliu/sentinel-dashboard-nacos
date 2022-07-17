package com.alibaba.csp.sentinel.dashboard.nacos.publisher;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  17:07
 * 基于 nacos 的 规则推送者
 */
public interface DynamicRuleNacosPublisher<T> {

    /**
     * Publish rules to remote rule configuration center for given application name.
     *
     * @param app   app name
     * @param rules list of rules to push
     * @throws Exception if some error occurs
     */
    void publish(String app, T rules) throws Exception;
}