package com.alibaba.csp.sentinel.dashboard.nacos.publisher;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigCenter;
import com.alibaba.csp.sentinel.dashboard.nacos.config.property.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.exception.NacosException;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/17  18:10
 */
public abstract class AbstractDynamicRuleNacosPublisher<T> implements DynamicRuleNacosPublisher<T> {

    @Resource
    protected NacosConfigProperties nacosConfigProperties;

    /**
     * 告诉父类 规则文件的文件名
     *
     * @param appName 应用的名称
     */
    protected abstract String loadDataId(String appName);

    /**
     * Publish rules to remote rule configuration center for given application name.
     *
     * @param app   app name
     * @param rules list of rules to push
     * @throws Exception if some error occurs
     */
    @Override
    public void publish(String app, T rules) throws Exception {
        String dataId = this.loadDataId(app);
        // 加载配置文件
        // 实体类的转换
        // 进行合并。

        doPublish(dataId, (List<? extends RuleEntity>) rules);
    }

    private void doPublish(String dataId, List<? extends RuleEntity> rules) throws NacosException {
        String content = JSON.toJSONString(
                rules.stream()
                        // 注意实体的转换
                        .map(RuleEntity::toRule)
                        .collect(Collectors.toList()));
        NacosConfigCenter.publishConfig(dataId, content);
    }
}
