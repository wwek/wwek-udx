package com.iamle.bigdata.flink.job;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SsqlJob {
    public static void main(String[] args) throws Exception {

        //环境信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();




        // 执行
        env.execute("Flink ssql test");
    }
}
