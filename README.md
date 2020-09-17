# wwek-udx

wwek的用户自定义函数
适用于flink


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
