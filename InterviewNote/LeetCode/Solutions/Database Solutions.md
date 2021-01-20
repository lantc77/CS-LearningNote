# 175. Combine Two Tables (easy)
https://leetcode.com/problems/combine-two-tables/
address中可能没有person对应的匹配项，所以不能用 where = 来判断，left join可以输出没有匹配项的列
```sql
# Write your MySQL query statement below
select firstname, lastname, City, State
from person
left join address
on person.personid = address.personid
```
# 176. Second Highest Salary (easy)
https://leetcode.com/problems/second-highest-salary/
1. 嵌套子查询，先得到最大值的数据，再得到第二大的数据
```sql
# Write your MySQL query statement below
select max(salary) as SecondHighestSalary
from employee
where salary < (
                select max(salary) 
                from employee
                );
```
2. 通过降序排序，limit限制输出为1，offset设置数据偏离位置1（从第二行开始）。第一个select得到的是列 Salary （不满足题目要求），题目需要输出一个新的子段SecondHighestSalary，所以用第二个select得到一个新的字段
```sql
select(
		select distinct Salary 
		from Employee 
		order by Salary Desc
		limit 1 offset 1
	  )as SecondHighestSalary
```

# 177. Nth Highest Salary (med)
https://leetcode.com/problems/nth-highest-salary/
注意使用 **distinct** ，否则会输出重复数据，在查询前定义好变量
```sql
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
declare m int;
set m = n - 1;
  RETURN (
      # Write your MySQL query statement below.
      select(
            select distinct salary 
            from employee
            order by salary desc 
            limit 1 offset m
      ) as second
  );
END
```
# Rank Scores (med)
https://leetcode.com/problems/rank-scores/
选择两列数据，一列是Score，另一列是Rank，它的内容是count不小于当前s.Score的Score数量，在括号里的查询语句，表示对应于每一个s.Score，都有一个对应的count(distinct Score)值与之对应
```sql
# Write your MySQL query statement below
SELECT
      Score,
      (SELECT count(distinct Score) 
       FROM Scores 
       WHERE Score >= s.Score) Rank
FROM Scores s
ORDER BY Score desc
```
trick，用dense_rank()函数，直接解决
```sql
select score, dense_rank() over (order by Score desc) [Rank] 
from scores
```
# Consecutive Numbers
https://leetcode.com/problems/consecutive-numbers/
链接三次表，将不同的id行链接起来，比较各自的num
```sql
# Write your MySQL query statement below
select distinct l1.num as ConsecutiveNums  
from logs l1, logs l2, logs l3
where l1.id = l2.id+1 
  and l2.id = l3.id + 1
  and l1.num = l2.num
  and l2.num = l3.num
```
---------------------
在跟朋友交流过以后，他说sql计算效率比较低，在公司里主要会用select就可以，导入到内存里，主要用java和python分析处理数据，他认为leetcode中的题目都是奇技淫巧- -，所以决定过一遍简单题就好了。。。

# 596. Classes More Than 5 Students
```sql
select class
from (  select class, count(distinct student) as num
        from courses
        group by class
     ) classNum
where num >= 5;
```
# 620. Not Boring Movies
```sql
select *
from cinema c
where Lower(c.description) not like '%boring%'
and (c.id % 2) != 0
order by c.rating desc
```
# 181. Employees Earning More Than Their Managers
https://leetcode.com/problems/employees-earning-more-than-their-managers/
```sql
select e1.name as Employee
from employee e1,  employee e2
where e1.managerid = e2.id and e1.salary > e2.salary
```
# 183. Customers Who Never Order
https://leetcode.com/problems/customers-who-never-order/
```sql
SELECT A.Name from Customers A
WHERE NOT EXISTS (SELECT 1 FROM Orders B WHERE A.Id = B.CustomerId)

SELECT A.Name from Customers A
LEFT JOIN Orders B on  a.Id = B.CustomerId
WHERE b.CustomerId is NULL

SELECT A.Name from Customers A
WHERE A.Id NOT IN (SELECT B.CustomerId from Orders B)
```