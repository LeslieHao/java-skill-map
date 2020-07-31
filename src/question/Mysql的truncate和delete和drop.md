#### 1.drop

```mssql
drop table table_name
```

* drop 删除表定义和所有数据，删除该表的所有触发器
* DDL 隐式提交 不可回滚
* 在系统层面会删除 xxx.ibd xxx.frm xxx.MYD xxx.MYI 等文件

#### 2.truncate

```mysql
truncate table table_name
=
drop table table_name
+
create table table_name
```

* truncate 会删除表并重新创建表，比delete 要快，有其对于大型表
* DDL 隐式提交 不可回滚
* 如果当前有活跃的表锁 无法执行truncate
* truncate 通常返回 ’0行受影响‘
* 对于 InnoDB/MyISAM 表的自增列（例如id）,truncate之后自增序列会重置

#### 3.delete

* DML 会记录日志，可以回滚，但是效率低
* 会加行锁
* 被删除的数据行只是被标记删除，不会减少占用空间
* delelte 后自增序列不会重置，除非重启数据库server