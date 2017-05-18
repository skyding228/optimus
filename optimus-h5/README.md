# t_dict

type为CONFIG的就是配置信息；
其他类型的是用于数据存储的;

# 定时任务
admin、h5的定时任务放在cron包下面

# 对账
充值提现订单双方不一致的情况

## 设计
按照原始订单号确定为同一条订单
将数据按照键值对的形式放在t_dict表中
type为CHECKING
异常类型:
- 某条订单只存在一方
- 订单数据不一致，金额、日期、状态

对方订单数据存哪里？放在description里面(截取前100)
出异常了也要发送邮件

对账算法:
定义两个Map,key是原始订单编号，value是订单对象;
双方对账文件依次交替取出一条记录，判断是否在对方Map中已经存在，如果存在就比较，相同就移除，不相同就放入自己的Map
	如果不存在就放进自己的Map
对账结束时，如果双方Map为空就是双方一致，否则就是有异常;

## 查询对账异常结果

```
--查询对账结果
SELECT IFNULL(CONCAT(DATE_FORMAT( o.order_time,'%Y-%m-%d %T'),'|',o.order_id,'|',o.outer_order_id,'|',
		o.chan_user_id,'|',o.order_type,'|',o.amount,'|',o.order_status,'|',o.memo),'A方不存在对应的记录') AS 'A方记录',d.`description` AS 'B方记录' FROM optimus.`t_dict` d LEFT JOIN optimus.`t_order` o ON d.`value` = o.`order_id` WHERE d.`type` = 'CHECKING' AND  SUBSTRING_INDEX(d.`id`,'.',2) = CONCAT('XINGYIFU2016','.',DATE_FORMAT(ADDDATE(NOW(),-1),'%Y-%m-%d'));
```

# 理财账户每日数据 
 日期    账户上日余额    转入通付（指从理财账户转入通付账户的）    转入理财（指从通付账户转入理财账户的）    产品购买支出    产品到期放款本金    产品到期放款利息    账户当日余额
 
## 设计
将数据按照键值对的形式放在t_dict表中
type为CHAN_OVERVIEW

## 查询账户每日数据

```
-- 查询渠道账户总览
SELECT SUBSTRING_INDEX(d.`id`,'.',-1) AS '键',d.`value` AS '值' FROM optimus.`t_dict` d WHERE d.`type` = 'CHAN_OVERVIEW' AND  SUBSTRING_INDEX(d.`id`,'.',2) = CONCAT('XINGYIFU2016','.',DATE_FORMAT(ADDDATE(NOW(),-1),'%Y-%m-%d')) ORDER BY d.`description`
```


