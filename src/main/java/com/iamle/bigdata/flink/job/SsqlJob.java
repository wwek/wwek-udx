/**
 * https://ci.apache.org/projects/flink/flink-docs-release-1.11/zh/dev/table/common.html#%E5%88%9B%E5%BB%BA-tableenvironment
 */
package com.iamle.bigdata.flink.job;

import com.iamle.bigdata.flink.udx.ParseUserAgent;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class SsqlJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment bsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);


        // 注册函数
        bsTableEnv.createTemporarySystemFunction("ParseUserAgent", ParseUserAgent.class);
        Table revenue = bsTableEnv.sqlQuery(
                "SELECT ParseUserAgent('a')"
        );
        System.out.println(revenue.toString());
    }
}
