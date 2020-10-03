# wwek-udx
[![Build Status](https://travis-ci.com/wwek/wwek-udx.svg?branch=master)](https://travis-ci.com/wwek/wwek-udx)
![GitHub](https://img.shields.io/github/license/wwek/wwek-udx)


## 简介

wwek-udx 是一个 Flink 适用的自定义函数(UDX)集成包。
包含了：
IP地址查询归属地信息 UDF
电话号码查询归属地信息 UDF
等

flink版本要求：
flink >= 1.11.2

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

## 可用UDX函数
### 标量函数（UDF）
ip2region(ip,0)表示国家
ip2region(ip,1)表示省份
ip2region(ip,2)表示城市
ip2region(ip,3)表示运营商

### 聚合函数（UDAF）
暂无

### 表值函数（UDTF）
暂无


## 开发wwek-udx

编译打包
```
mvn clean package -Dmaven.test.skip=true
```

## Todo
*[ ] 对象实例化未使用单例
*[ ] Benchmark