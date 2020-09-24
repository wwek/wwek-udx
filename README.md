# wwek-udx

wwek的用户自定义函数
适用于flink


ip2region(ip,0)表示国家
ip2region(ip,1)表示省份
ip2region(ip,2)表示城市
ip2region(ip,3)表示运营商





flink-udf范例
https://github.com/zjffdu/flink-udf

## 开发

打包
mvn clean package -Dmaven.test.skip=true


### 新建一个flink项目

https://ci.apache.org/projects/flink/flink-docs-release-1.11/dev/project-configuration.html

mvn archetype:generate                               \
      -DarchetypeGroupId=org.apache.flink              \
      -DarchetypeArtifactId=flink-quickstart-java     \
      -DarchetypeVersion=1.11.0
      
      
curl https://flink.apache.org/q/quickstart.sh | bash -s 1.10.0
