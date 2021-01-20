因为最近要学习SQL，所以写一份关于《SQL必知必会》的总结，**本文DBMS使用MySQL**，SQL语法中是不区分大小写的。  

*版权说明：[美]Ben Forta. SQL必知必会（第4版） (图灵程序设计丛书 80). 人民邮电出版社.*   
@[toc]
# 1. 检索数据
**1.1 检索单个列**
1. 结束语句以分号;结尾
2. 不区分大小写
3. 处理语句时所有空格都会被省略，写成一行或多行没有区别
```sql
SELECT prod_name
FROM Products;
```
**1.2 检索多个列**
1. 多个列之间用逗号隔开
```sql
SELECT prod_id, prod_name, prod_price
FROM Products;
```
**1.3 检索不同值**
1. 使用关键字**DISTINCT**
2. 关键字应用于所有列
```sql
SELECT vend_id, prod_price
FROM Products;
```
**1.4 限制结果**
各个数据库实现方式不同

**1.5 注释**
```sql
/* SELECT vend_id
FROM Products */ 多行注释
SELCET prod_name --这是一条注释
FROM Products; # 这也是注释
```

# 2. 排序检索数据
**2.1 排序单个列**
```sql
SELECT prod_name
FROM Products
ORDER BY prod_name;
```
1. ORDER BY必须在代码的最后一句
2. 可以通过 **非检索列的数据** 进行排序也是完全**合法**的

**2.2 多个列排序**
```sql
SELECT prod_id, prod_name, prod_price
FROM Products
ORDER BY prod_id, prod_name, prod_price;
```

**2.3 按列位置进行排序**
```sql
SELECT prod_id, prod_name, prod_price
FROM Products
ORDER BY 2, 3;
```
1. 使用相对列位置（起始位1）排序，好处在于不用输入列名，缺点在于容易出错
2. 列号不合法会报错
3. 可以使用实际列名和相对位置混合排序

**2.4 指定序列方向**
```sql
SELECT prod_id, prod_name, prod_price
FROM Products
ORDER BY prod_id DESC, prod_name, prod_price DESC;
```
1. 排序默认**升序**，关键字为**DESC**
2. 每个需要降序的列后都需要加关键字

# 3. 过滤数据
**3.1 使用WHERE语句**
```sql
SELECT prod_name, prod_price
FROM Products
WHERE prod_price = 3.49
```
条件操作符
| 操作符 | 说明   |
| ------ | ------ |
| =      | 等于   |
| < >    | 不等于 |
| !=     | 不等于 |
|!<|不小于
|BETWEEN|指定两个值之间|
|IS NULL|为NULL值|
1. != 和 <>通常可以互换，但应遵循使用的DBMS文档

**3.2 使用WHERE子句**
**操作符(Operator)**用来联结或改变 **WHERE** 子句中的子句的关键字，又称逻辑操作符
条件操作符
| 操作符 | 说明                                |
| ------ | ----------------------------------- |
| AND    | 检索满足所有指定条件的行            |
| OR     | 检索满足指定条件之一的行            |
| IN     | 工作性质和OR相同 (以下代码效果相同) |
| NOT    | 否定其后条件                        |
```sql
SELECT prod_name, prod_price
FROM Products
WHERE vend_id IN ('DLL01', 'BRS01')
ORDER BY prod_name;
```
```sql
SELECT prod_name, prod_price
FROM Products
WHERE vend_id = 'DLL01' OR vend_id = 'BRS01'
ORDER BY prod_name;
```
1. 使用圆括号明确分组操作符，不要依赖默认求值顺序
2. IN 比 OR 操作符执行更快，而且可以包含其他SELECT语句

# 4. 用通配符进行过滤
通配符较慢，若其他操作符能达到操作目的，则优先使用其他操作符。  

**4.1 LIKE操作符**  

**通配符(wildcard)**:用来匹配值得一部分的特殊字符
**搜索模式(search pattern)**:字面值，通配符或两者组合构成的搜索条件
**谓词(predicate)**:从技术上说，LIKE是谓词不是操作符，但是结果是相同的（没多大区别）
通配符搜索只能用于文本字段（串），搜索根据DBMS的设置，可以区分大小写
| 通配符 | 说明                                           | 代码 |
| ------ | ---------------------------------------------- | ---- |
| %      | 匹配任何字符出现任意次数（包括0次）            |      |
| _      | 一个下划线匹配一个字符                         |      |
| [ ]    | 指定一个字符集，必须匹配指定位置的**一个字符** |      |

**4.2 %通配符**  

1. 接受Fish之后任意字符
```sql
SELECT prod_name, prod_price
FROM Products
WHERE prod_name LIKE 'Fish%' 
```
2. bean bag前后可为任意字符
```sql
SELECT prod_name, prod_price
FROM Products
WHERE prod_name LIKE '%bean bag%' 
```
3. 仅匹配首尾，用来查找电子邮件挺有用
```sql
WHERE email LIKE 'b%@163.com' 
```
4. 注意空格也算字符，%不匹配NULL值

**4.3 下划线**  

**4.4 方括号 [ ]**  

匹配任何字符串首字符为J或者M，只能匹配一个字符。可以用\^来否定，例如本例改为[\^JM]，表示不匹配任何以J或M开头的人名
```sql
SELECT cust_contact
FROM customers
WHERE cust_contact LIKE '[JM]%'
```
# 5. 创建计算字段
**5.1 字段**(field)基本上与列的意思相同，经常互换使用。从客户端来看计算字段的数据和其他列的数据的返回方式相同。

**关键字 Concat**
**关键字 TRIM()** 去掉首尾空格，LTRIM()去掉左空格，RTRIM()去掉右空格
**关键字 AS** 计算列没有名字，它只是一个值，未命名的列客户端没有办法引用它，因此不能应用于客户端（应用程序）中，用关键字 **AS** 赋予计算列**别名(alias)**。如果仅仅是查看查询结果，则可以不用。
```sql
SELECT Concat(vend_name,'(',TRIM(vend_country),')')
	   AS Vend_title
FROM Vendors 
ORDER BY vend_name;
```
**5.2 执行算术计算**
```sql
SELECT prod_id,
	   quantity,
	   item_price,
	   quantity*item_price AS expanded_price
FROM OrderItems
WHERE order_num = 20008;
```
# 6. 使用函数
SQL函数往往不可移植
**6.1 文本处理函数**
| 函数              | 说明                             |
| ----------------- | -------------------------------- |
| LEFT() / RIGHT()  | 返回字符串左( 右 )边的字符       |
| LENGTH()          | 返回字符串的长度                 |
| LOWER() / UPPER() | 字符串转换成小 / 大写            |
| SOUNDEX()         | 返回文本转换成其语音表示的数字值 |
匹配所有发音类似于'Michael Green'的联系名
```sql
select cust_name, cust_contact
from customers
where soundex(cust_contact) = soundex('Michael Green');
```
**6.2 日期和时间处理函数**
从日期中自动提取年份
```sql
select order_num
from orders
where year(order_date) = 2012
```
**6.3 数值处理函数**
| 函数          | 说明       |
| ------------- | ---------- |
| abs()         | 返回绝对值 |
| cos() / sin() | 三角函数   |
| exp()         | 返回指数值 |
| pi()          | 返回圆周率 |
| sqrt()        | 返回平方根 |
# 7. 汇总数据
**7.1 聚集函数(aggregate function)** 对某些行运行的函数，计算并返回值
|函数| 说明 |备注|
|---------|--------|--------|
|  avg() | 求某列平均 |只能用于单个列
|  count() | 求某列的行数 |忽略指定列值为空的行，除非使用count(*)
|  max() / min() |  求某列的最大最小值 |用于文本时候，返回最后 / 最前一行
|  sum() | 求某列总和 |

# 8. 分组数据
**8.1 创建分组**
目前为止所有计算都是在表上的所有数据或者匹配特定的where子句的数据上进行，例如下面例子返回供应商DLL01提供的产品数目。
```sql
select count(*) as num_prods
from Products
where vend_id = 'DLL01';
```
如果要返回每个供应商提供的产品数目，该怎么办？或者返回只提供一项产品的供应商的产品，或者返回提供10个以上产品的供应商的产品，怎么办？这就是分组大显身手的时候了。使用分组可以将数据分为多个逻辑组，对每个组进行聚集计算。
```sql
select vend_id, count(*) as num_prods
from Products
group by vend_id;
```
```sql
vend_ id num_ prods 
-------  --------- 
BRS01 	 3 
DLL01 	 4 
FNG01    2 
```
group by指示DBMS按照vend_id进行排序并分组数据，这样一来就是根据vend_id而不是整个表计算num_prods  

**GROUP BY注意事项**
- GROUP BY子句可以包含任意数目的列，因而可以对分组进行嵌套，更细致地进行数据分组
- 如果在GROUP BY子句中嵌套了分组，数据将在最后指定的分组上进行汇总。换句话说，在建立分组时，指定的所有列都一起计算（所以不能从个别的列取回数据）
- GROUP BY子句中列出的每一列都必须是检索列或有效的表达式（但不能是聚集函数）。如果在SELECT中使用表达式，则必须在GROUP BY子句中指定相同的表达式。不能使用别名
- 大多数SQL实现不允许GROUP BY列带有长度可变的数据类型（如文本或备注型字段）
- **除聚集计算语句外，SELECT语句中的每一列都必须在GROUP BY子句中给出**
- 如果分组列中包含具有NULL值的行，则NULL将作为一个分组返回。如果列中有多行NULL值，它们将分为一组
- GROUP BY子句必须出现在WHERE子句之后，ORDER BY子句之前  

**8.2 过滤分组**
SQL还允许过滤分组，规定包括哪些分组，排除哪些分组。例如，你可能想要列出至少有两个订单的所有顾客。为此，必须基于完整的分组而不是个别的行进行过滤。
用到关键字**HAVING**，所有类型的WHERE子句都可以用HAVING代替，唯一的区别是WHERE过滤行，HAVING过滤分组。
```sql
select vend_id, count(*) as num_prods
from products
group by vend_id
having num_prods >= 2； -- 改成having count(*) >= 2也可以
```
```sql
vend_ id num_ prods 
-------- --------- 
BRS01 	 3 
DLL01	 4 
FNG01	 2
```
同时使用WHERE，HAVING，被WHERE过滤掉的数组不会出现在分组中
```sql
select vend_id, count(*) as num_prods
from products
where prod_price >= 4
group by vend_id
having num_prods >= 2; -- 改成having count(*) >= 2也可以
```
```sql
vend_ id num_ prods 
-------- --------- 
BRS01 	 3 
FNG01	 2
```
**order by 与 group by**
| order by                            | group by                                                 |
| ----------------------------------- | -------------------------------------------------------- |
| 对产生的输出排序                    | 对行分组，但输出可能不是分组的顺序                       |
| 任意列都可以使用（ 甚至非选择的列） | 只可能使用选择列或表达式列，而且必须使用每个选择列表达式 |
| 不一定需要                          | 如果和聚集函数一起使用列或表达式，则必须使用             |
一般在使用group by子句时，也是用order by，这是保证数据正确排序的唯一方法，不要仅仅依赖group by排序数据

**8.3 SELECT子句顺序**
|子句| 说明| 是否必须使用|
|---------|--------|--------|
| select | 要返回的列或表达式 | 是
| from | 从中检索数据的表 | 仅在从表选择数据时使用
| where | 行级过滤 | 否
| group by | 分组说明 | 仅在按组计算聚集时使用
| having | 组级过滤 | 否
| order by | 输出数据排序 | 否
# 9. 使用子查询
**注意：作为子查询的select语句只能查询单个列，不能查询多个列**
第一个查询
```sql
select order_num
from orderitems
where prod_id = 'RGAN01'
```
```sql
order_num
---------
20007
20008
```
第二个查询
```sql
select cust_id
from orders
where order_num in (20007, 20008)
```
合并两个查询，子查询总是从内向外处理
```sql
select cust_id
from orders
where order_num in (select order_num
					from orderitems
					where prod_id = 'RGAN01')
```

# 10*. 联结表
**10.1 内联结（等值联结）**
where子句作为过滤条件，只包含那些匹配给定条件的行
```sql
select vend_name, prod_name, prod_price
from vendors, products
where vendors.vend_id = products.vend_id;
```
**10.2 联结多个表**
用嵌套子查询
```sql
select cust_name, cust_contact
from customers
where cust_id in (select cust_id
				  from orders
				  where order_num in (select order_num
									  from orderitems
									  where prod_id = 'RGAN01'));
```
使用联结表
```sql
select cust_name, cust_contact
from customers, orders, orderitems
where customers.cust_id = orders.cust_id 
and orders.order_num = orderitems.order_num
and prod_id = 'RGAN01';
```
结果相同
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190702200500897.png)
**10.3 外联结**
先看一个内联结的例子，where的意思是完全匹配才输出
```sql
select p.FirstName, p.LastName, a.City, a. State
from Person as p, Address as a 
where p.PersonId = a.PersonId;
```
外联结除了包括两个表中匹配条件的行，还包括“**没有关联行**”的行。
*此外left join表示从左边的表选择所有行，right join同理是右边。*
```sql
SELECT FirstName, LastName, City, State
FROM Person
LEFT JOIN Address
ON Person.PersonId = Address.PersonId;
```
**10.4 带聚集函数的联结**
分组显示统计过的符合条件的列
```sql
select customers.cust_id,
	   count(orders.order_num) as num_ord
from customers inner join orders
where customers.cust_id = orders.cust_id 
group by customers.cust_id;
```
# 11. 组合查询
**关键字union**：将多条 select 语句组合成一个结果集
1. union 和使用 where+or 效果是一样的
2. union每个查询必须包含相同的列，表达式，聚集函数
3. 列数据类型必须兼容
4. 重复的行会被自动取消 （使用union all时不取消）
5. 只允许代码最后一行使用order by，不允许多条存在
```sql
select cust_name, cust_contact, cust_email
from customers
where cust_state in ('IL', 'IN', 'MI')
union
select cust_name, cust_contact, cust_email
from customers
where cust_name = 'Fun4A11';
```
# 12. 插入数据
# 13. 更新删除数据
# 14. 创建和操纵表
**14.1 创建表基础**
1. 不同SQL实现中， CREATE TABLE语法可能不同
2. 利用 **CREATE TABLE** 创建表必须给出如下信息：
	1. 新表名字，在关键字后给出
	2. 表列名字和定义，用逗号分隔（表最后一行没有逗号）
	3. 有的DBMS还要指定表的位置、
3. 创建新表时，指定表名必须不存在，否则出错，为了防止意外覆盖已有的表，SQL一般要求手工删除该表，再重新创建，而非简单覆盖

**使用NULL值**
1. 每个表列要么是NOT NULL列，要么是NULL列
2. 定义为NOT NULL的列会阻止没有值得列插入
3. NULL值不等同于空字符串''
4. 只有不允许NULL值得列可作为主键，允许NULL的列不能作为唯一标识符

**指定默认值**
关键字 **DEFAULT**
```sql
CREATE TABLE Products
{
	prod_id			CHAR(10)		NOT NULL	DEFAULT 1,
	vend_id 		CHAR(10)		NOT NULL,
	prod_name 		CHAR(256)		NOT NULL,
	prod_price 		DECIMAL(8, 2) 	NOT NULL,
	prod_desc 		VARCHAR(1000)	NULL.
	vend_address	CHAR(50)		,
	vend_city		CHAR(50)		,
	vend_country	CHAR(50)	
};
```
**14.2 更新表**
1. 加入列
```sql
ALTER TABLE Products
ADD Vend_phone CHAR(20);
```
2. 删除列
```sql
ALTER TABLE Products
DROP Vend_phone;
```
3. 删除表
```sql
DROP TABLE Products
```
# 15. 使用视图
# 16. 管理事务处理
# 17. 高级SQL特性