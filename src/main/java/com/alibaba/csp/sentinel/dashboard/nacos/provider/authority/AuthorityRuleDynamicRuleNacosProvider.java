package com.alibaba.csp.sentinel.dashboard.nacos.provider.authority;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.nacos.config.NacosConfigCenter;
import com.alibaba.csp.sentinel.dashboard.nacos.provider.AbstractDynamicRuleNacosProvider;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author immort-liuzyj(zyliu)
 * @since 2022/7/21  16:55
 */
@Component
public class AuthorityRuleDynamicRuleNacosProvider extends AbstractDynamicRuleNacosProvider<List<AuthorityRuleEntity>> {

    /**
     * @param app  app name
     * @param ip   machine client IP
     * @param port machine client port
     * @throws Exception if some error occurs
     */
    @Override
    public List<AuthorityRuleEntity> getRules(String app, String ip, Integer port) throws Exception {

        // 获取文件名称
        String dataId = app + nacosConfigProperties.getAuthorityRuleConfigSuffix();

        // 从nacos中获取对应配置文件的内容
        String content = NacosConfigCenter.getConfig(dataId);


        if (StringUtil.isEmpty(content)) {
            return new ArrayList<>(0);
        }

        // json 反序列化
        List<AuthorityRule> ruleList = JSON.parseArray(content, AuthorityRule.class);

        // 转换成 dashboard 所使用的entity
        return ruleList.stream()
                .map(rule -> AuthorityRuleEntity.fromAuthorityRule(app, ip, port, rule))
                .collect(Collectors.toList());
    }
}
