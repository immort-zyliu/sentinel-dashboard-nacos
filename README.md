# sentinel 控制台 推送到nacos版本。

## 0. 前言
> 如果使用这个的话，sentinel client端的各种rules 也需要从 nacos  中进行拉取（这部分不用实现，sentinel 官方已经实现，当然你也可以自己实现）

> 使用此种方式有一种缺点，同种服务、不同机器 的限流规则 不太容易细粒度控制。  
> 比如user服务 部署在机器a一份，机器b一份。此时由于服务都从nacos 配置中心 监听 user-flow-rules 这么一个流控配置，此时就导致这个user-flow-rules的配置在 两个机器中都生效，你无法精准控制 机器a中user服务的限流参数 机器b的user服务的限流参数。因为他们监听的配置文件相同。


> 有需要用的，测试出bug，需要更改的话，请大概阅读 一下 sentienl client端源码（大概架构即可） sentinel dashboard 的推送方式部分源码，即可自行更改（或者联系本人一起讨论）

> 从本项目的提交日志即可看到我一步一步都改了什么；因为我是从原来sentinel nacos的源代码开始进行更改的。



## 1. 规则推送方式


## 2. 使用方式

### 2.1 client 端的配置

### 2.2 dashboard 端的配置


## 最后，如何避免懒加载？