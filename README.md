# wwek-udx

Flink适用的自定义函数(UDX)

flink ip转归属地信息 UDF
flink 电话号码归属地信息 UDF

## 使用
在zeppelin中使用
```
%flink.conf
flink.udf.jars /Users/wwek/Projects/bigdata/wwek-udx/target/wwek-udx-1.0-SNAPSHOT.jar

%flink.ssql(type=update)
show functions;

%flink.ssql(type=update)
select ip2region('114.114.114.114',0),phone2region('18798896741', 1);
```

### 标量函数（UDF）

ip2region(ip,0)表示国家
ip2region(ip,1)表示省份
ip2region(ip,2)表示城市
ip2region(ip,3)表示运营商

### 聚合函数（UDAF）
暂无

### 表值函数（UDTF）
暂无


flink-udf范例
https://github.com/zjffdu/flink-udf

## 开发

编译打包
```
mvn clean package -Dmaven.test.skip=true
```
