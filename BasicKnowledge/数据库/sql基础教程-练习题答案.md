练习题
1.1　编写一条 CREATE TABLE 语句，用来创建一个包含表 1-A 中所列各项的表 Addressbook（地址簿），并为 regist_no（注册编号）列设置主键约束。

表1-A　表 Addressbook（地址簿）中的列

列的含义	列的名称		数据类型	约束	

注册编号	regist_no		整数型		不能为 NULL、主键	

姓名			name				可变长字符串型（长度为 128 类）	不能为 NULL

住址			address			可变长字符串类型（长度为 256）	不能为 NULL	

电话号码	tel_no				定长字符串类型（长度为 10）

邮箱地址	mail_address	定长字符串类型（长度为 20）

```sql
create table Addressbook(
	regist_no integer not null,
    name varchar(128) not null,
    address varchar(256) not null,
    tel_no char(10),
    mail_address char(20)    
)primary key regist_no;
```



 1.2　假设在创建练习 1.1 中的 Addressbook 表时忘记添加如下一列 postal_code（邮政编码）了，请把此列添加到　Addressbook 表中。

列名　　：postal_code

数据类型：定长字符串类型（长度为 8）

约束　　：不能为 NULL

```sql
alter table addressbook add (postal_code char(8) not null);
```



2.1 

```sql
select product_name, regist_date 
from product
where regist_date > "2009-04-28"
;
```

2.3

```sql
select product_name, sale_price, purchase_price
from product
where sale_price - purchase_price >= 500;	
```

2.4

```sql
select product_name, product_type
from product
where (product_type = "办公用品" or product_type = "厨房用具")
and sale_price * 0.9 - purchase_price > 100;
```

3.1

