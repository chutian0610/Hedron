# Hedron

Hedron 是一个针对BI 场景的查询优化器。

BI 查询场景的特征:

- 数据源可以是不同的数据库引擎，也可以是相同数据库引擎的不同集群。
- 用户提供某个数据源的sql/table 作为数据来源，我们称为 view。 
- 用户的 SQL基于一个或多个 view 进行查询，我们称为 query。query中的view可能来自相同或不同数据源。


Hedron 会根据 query 中的 view，以及 view 的 schema 信息，生成一个优化的执行计划。

- [ ] pushdown: 重写 query 中的 SQL，将一些计算操作下推到数据源执行，以减少数据传输量。
- [ ] 函数适配: 重写 query 中的函数调用，以适配数据源的函数实现。
