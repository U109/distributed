Atomikos通过数据库自带的X/OPEN协议，实现XA方案的分布式事务，基于2PC。

注意：MySQL 8以上需要开启相应权限 ：

```shell
GRANT XA_RECOVER_ADMIN ON *.* TO root@'%' ;
```
