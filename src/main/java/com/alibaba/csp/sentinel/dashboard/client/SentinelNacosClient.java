package com.alibaba.csp.sentinel.dashboard.client;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.provider.paramflow.ParamFlowRuleDynamicRuleNacosProvider;
import com.alibaba.csp.sentinel.dashboard.nacos.publisher.paramflow.ParamFlowDynamicRuleNacosPublisher;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.util.AsyncTaskUtils;
import com.alibaba.csp.sentinel.dashboard.util.AsyncUtils;
import com.alibaba.csp.sentinel.util.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/18  17:40
 */
@Component
public class SentinelNacosClient {
    private static final Logger logger = LoggerFactory.getLogger(SentinelNacosClient.class);

    @Resource
    private ParamFlowRuleDynamicRuleNacosProvider paramFlowRuleDynamicRuleNacosProvider;

    @Resource
    private ParamFlowDynamicRuleNacosPublisher paramFlowDynamicRuleNacosPublisher;

    @Resource
    private RuleRepository<ParamFlowRuleEntity, Long> paramFlowRepository;

    /**
     * Fetch all parameter flow rules from provided nacos.
     *
     * @param app  application name
     * @param ip   machine client IP
     * @param port machine client port
     * @return all retrieved parameter flow rules
     * @since 0.2.1
     */
    public CompletableFuture<List<ParamFlowRuleEntity>> fetchParamFlowRulesOfNacos(String app, String ip, int port) {
        try {
            AssertUtil.notEmpty(app, "Bad app name");
            AssertUtil.notEmpty(ip, "Bad machine IP");
            AssertUtil.isTrue(port > 0, "Bad machine port");

            return AsyncTaskUtils.supplyAsyncWithDefaultThreadPool(() -> {
                try {
                    return paramFlowRuleDynamicRuleNacosProvider.getRules(app, ip, port);
                } catch (Exception e) {
                    logger.error("fatch error", e);
                }
                return new ArrayList<>();
            });
        } catch (Exception e) {
            logger.error("Error when fetching parameter flow rules", e);
            return AsyncUtils.newFailedFuture(e);
        }
    }

    /**
     *
     * @param app  application name
     * @param ip   machine client IP
     * @param port machine client port
     */
    public CompletableFuture<Void> publishParamFlowRules(String app, String ip, Integer port) {
        // List<ParamFlowRuleEntity> rules = paramFlowRepository.findAllByMachine(MachineInfo.of(app, ip, port));
        List<ParamFlowRuleEntity> rules = paramFlowRepository.findAllByApp(app);
        return AsyncTaskUtils.supplyAsyncWithDefaultThreadPool(() -> {
            try {
                paramFlowDynamicRuleNacosPublisher.publish(app, rules);
            } catch (Exception e) {
                logger.error("publish param flow rules error", e);
            }
            return null;
        });
    }


}
