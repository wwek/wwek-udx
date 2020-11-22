# wwek-udx
[![Build Status](https://travis-ci.com/wwek/wwek-udx.svg?branch=master)](https://travis-ci.com/wwek/wwek-udx)
![GitHub](https://img.shields.io/github/license/wwek/wwek-udx)


## 简介

wwek-udx 是一个 Flink 适用的自定义函数(UDX)集成包。
目前已包含了：
ip2region IP地址查询归属地信息 UDF
电话号码查询归属地信息 UDF
UserAgent结构化解析 UDF

flink版本要求：
flink >= 1.11.2

## 特性

- 多种函数可用包含ip查询、电话号码查询、UserAgent解析等
- 函数返回结果支持格式化（format）
- 本地化二进制数据库all in one jar包，无外部网络依赖

## 使用
在zeppelin中使用
```
%flink.conf
flink.udf.jars /你的目录/wwek-udx-1.0-SNAPSHOT.jar

%flink.ssql(type=update)
show functions;

%flink.ssql(type=update)
select ip2region('114.114.114.114',0),phone2region('18798896741', 1);
```

## 可用UDX函数
### 标量函数（UDF）

### **ip2region(ip,format)**

根据format字符串格式化ip归属地信息查询
默认format '%c %P %C %I'

**使用示例**

| 函数调用                                            | 结果                                                         |
| --------------------------------------------------- | ------------------------------------------------------------ |
| ip2region('101.105.35.57') （format='%c %P %C %I'） | 中国 浙江省 杭州市 阿里云                                    |
| ip2region('101.105.35.57','%c %P')                  | 中国 浙江省                                                  |
| ip2region('101.105.35.57','head%c %P\|%C')          | head中国 浙江省\|杭州市                                      |
| ip2region('101.105.35.57','%C')                     | 杭州市                                                       |
| ip2region('101.105.35.57','%JSON')                  | {"country":"中国","area":"0","province":"浙江省","city":"杭州市","isp":"阿里云"} |

**ip2region format参照表**

| Specifier | FieldName | ValueExample | Description                     |
| --------- | --------- | ------------ | ------------------------------- |
| %c        | country   | 中国         | 国家                            |
| %P        | province  | 浙江省       | 省份                            |
| %C        | city      | 杭州市       | 城市                            |
| %I        | isp       | 阿里云       | 运营商                          |
| %A        | area      | 华东         | 区域                            |
| %JSON     |           |              | json 以json的方式结构化所有字段 |



### **phone2region(phone,format)**

根据format字符串格式化电话归属地信息查询
默认format '%P %C %I'

**使用示例**

| 函数调用                                          | 结果                                                         |
| ------------------------------------------------- | ------------------------------------------------------------ |
| phone2region('13888888888') （format='%P %C %I'） | 云南 昆明 中国移动                                           |
| phone2region('13888888888','head%P %C')           | head云南 昆明                                                |
| phone2region('13888888888','%C')                  | 昆明                                                         |
| phone2region('13888888888','%JSON')               | {"province":"云南","city":"昆明","isp":"中国移动","zipcode":"650000","areacode":"0871"} |

**phone2region format参照表**

| Specifier | FieldName | ValueExample | Description                     |
| --------- | --------- | ------------ | ------------------------------- |
| %P        | province  | 云南         | 省份                            |
| %C        | city      | 昆明         | 城市                            |
| %I        | isp       | 中国移动     | 运营商                          |
| %zc       | zipcode   | 650000       | 邮编                            |
| %ac       | areacode  | 0871         | 区划码                          |
| %JSON     | 所有字段  |              | json 以json的方式结构化所有字段 |



### **parseuseragent(ua,format)**

根据format字符串格式化ua查询（使用yauua数据源）
默认format '%DN %OSNV %LENV %ANV'

**使用示例**

| 函数调用                                                     | 结果                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36') （format='%DN %OSNV %LENV %ANV'） | Apple Macintosh Mac OS X 10.15.7 Blink 85.0 Chrome 85.0.4183.121 |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%DN') | Apple Macintosh                                              |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%OSNV') | Mac OS X 10.15.7                                             |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%LENV') | Blink 85.0                                                   |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%ANV') | Chrome 85.0.4183.121                                         |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%JSON') | {"DeviceClass":"Desktop","DeviceName":"Apple Macintosh","DeviceBrand":"Apple","OperatingSystemClass":"Desktop","OperatingSystemName":"Mac OS X","OperatingSystemVersion":"10.15.7","OperatingSystemNameVersion":"Mac OS X 10.15.7","OperatingSystemVersionBuild":"??","LayoutEngineClass":"Browser","LayoutEngineName":"Blink","LayoutEngineVersion":"85.0","LayoutEngineVersionMajor":"85","LayoutEngineNameVersion":"Blink 85.0","LayoutEngineNameVersionMajor":"Blink 85","AgentClass":"Browser","AgentName":"Chrome","AgentVersion":"85.0.4183.121","AgentVersionMajor":"85","AgentNameVersion":"Chrome 85.0.4183.121","AgentNameVersionMajor":"Chrome 85"} |

**parseuseragent format参照表**

| Specifier | FieldName                    | ValueExample         | Description                |
| --------- | ---------------------------- | -------------------- | -------------------------- |
| %DC       | DeviceClass                  | Phone                |                            |
| %DN       | DeviceName                   | Google Nexus 6       |                            |
| %DB       | DeviceBrand                  | Google               |                            |
| %OSC      | OperatingSystemClass         | Mobile               |                            |
| %OSN      | OperatingSystemName          | Android              |                            |
| %OSV      | OperatingSystemVersion       | 7.0                  |                            |
| %OSNV     | OperatingSystemNameVersion   | Android 7.0          |                            |
| %OSVB     | OperatingSystemVersionBuild  | NBD90Z               |                            |
| %LEC      | LayoutEngineClass            | Browser              |                            |
| %LEN      | LayoutEngineName             | Blink                |                            |
| %LEV      | LayoutEngineVersion          | 53.0                 |                            |
| %LEVM     | LayoutEngineVersionMajor     | 53                   |                            |
| %LENV     | LayoutEngineNameVersion      | Blink 53.0           |                            |
| %LENVM    | LayoutEngineNameVersionMajor | Blink 53             |                            |
| %AC       | AgentClass                   | Browser              |                            |
| %AN       | AgentName                    | Chrome               |                            |
| %AV       | AgentVersion                 | 53.0.2785.124        |                            |
| %AVM      | AgentVersionMajor            | 53                   |                            |
| %ANV      | AgentNameVersion             | Chrome 53.0.2785.124 |                            |
| %ANVM     | AgentNameVersionMajor        | Chrome 53            |                            |
| %JSON     | 所有字段                     |                      | 以json的方式结构化所有字段 |



### **parseuseragentdd(ua,format)**

根据format字符串格式化ua查询（使用mngsk/device-detector数据源）

默认format '%DN %OSNV %LENV %ANV'

**使用示例**

| 函数调用                                                     | 结果                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36') （format='%DN %OSNV %LENV %ANV'） | Apple Macintosh Mac OS X 10.15.7 Blink 85.0 Chrome 85.0.4183.121 |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%DN') | Apple Macintosh                                              |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%OSNV') | Mac OS X 10.15.7                                             |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%LENV') | Blink 85.0                                                   |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%ANV') | Chrome 85.0.4183.121                                         |
| parseuseragent('Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.124 Mobile Safari/537.36','%JSON') | {"DeviceClass":"Desktop","DeviceName":"Apple Macintosh","DeviceBrand":"Apple","OperatingSystemClass":"Desktop","OperatingSystemName":"Mac OS X","OperatingSystemVersion":"10.15.7","OperatingSystemNameVersion":"Mac OS X 10.15.7","OperatingSystemVersionBuild":"??","LayoutEngineClass":"Browser","LayoutEngineName":"Blink","LayoutEngineVersion":"85.0","LayoutEngineVersionMajor":"85","LayoutEngineNameVersion":"Blink 85.0","LayoutEngineNameVersionMajor":"Blink 85","AgentClass":"Browser","AgentName":"Chrome","AgentVersion":"85.0.4183.121","AgentVersionMajor":"85","AgentNameVersion":"Chrome 85.0.4183.121","AgentNameVersionMajor":"Chrome 85"} |

**parseuseragent format参照表**

| Specifier | FieldName                    | ValueExample         | Description                |
| --------- | ---------------------------- | -------------------- | -------------------------- |
| %DC       | DeviceClass                  | Phone                |                            |
| %DN       | DeviceName                   | Google Nexus 6       |                            |
| %DB       | DeviceBrand                  | Google               |                            |
| %OSC      | OperatingSystemClass         | Mobile               |                            |
| %OSN      | OperatingSystemName          | Android              |                            |
| %OSV      | OperatingSystemVersion       | 7.0                  |                            |
| %OSNV     | OperatingSystemNameVersion   | Android 7.0          |                            |
| %OSVB     | OperatingSystemVersionBuild  | NBD90Z               |                            |
| %LEC      | LayoutEngineClass            | Browser              |                            |
| %LEN      | LayoutEngineName             | Blink                |                            |
| %LEV      | LayoutEngineVersion          | 53.0                 |                            |
| %LEVM     | LayoutEngineVersionMajor     | 53                   |                            |
| %LENV     | LayoutEngineNameVersion      | Blink 53.0           |                            |
| %LENVM    | LayoutEngineNameVersionMajor | Blink 53             |                            |
| %AC       | AgentClass                   | Browser              |                            |
| %AN       | AgentName                    | Chrome               |                            |
| %AV       | AgentVersion                 | 53.0.2785.124        |                            |
| %AVM      | AgentVersionMajor            | 53                   |                            |
| %ANV      | AgentNameVersion             | Chrome 53.0.2785.124 |                            |
| %ANVM     | AgentNameVersionMajor        | Chrome 53            |                            |
| %JSON     | 所有字段                     |                      | 以json的方式结构化所有字段 |



### 聚合函数（UDAF）

暂无

### 表值函数（UDTF）
暂无


## 开发wwek-udx

编译打包
```
mvn clean package -Dmaven.test.skip=true
```

## 问题和建议

发issue || 发pr

## Todo

*[ ] 实现查询缓存提高性能
*[ ] 对象实例化未使用单例
*[ ] Benchmark