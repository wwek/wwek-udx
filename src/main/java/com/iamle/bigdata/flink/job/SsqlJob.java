/**
 * https://ci.apache.org/projects/flink/flink-docs-release-1.11/zh/dev/table/common.html#%E5%88%9B%E5%BB%BA-tableenvironment
 */
package com.iamle.bigdata.flink.job;

import com.iamle.bigdata.flink.udx.ParseUserAgent;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class SsqlJob {

    // source table datagen
    // https://ci.apache.org/projects/flink/flink-docs-stable/zh/dev/table/connectors/datagen.html
    private static final String DATA_SOURCE_SQL = "CREATE TABLE datagen_source (\n" +
            " f_sequence INT,\n" +
            " f_random INT,\n" +
            " f_random_str STRING,\n" +
            " ts AS localtimestamp,\n" +
            " WATERMARK FOR ts AS ts\n" +
            ") WITH (\n" +
            " 'connector' = 'datagen',\n" +
            "\n" +
            " 'rows-per-second'='5',\n" +
            "\n" +
            " 'fields.f_sequence.kind'='sequence',\n" +
            " 'fields.f_sequence.start'='1',\n" +
            " 'fields.f_sequence.end'='1000',\n" +
            "\n" +
            " 'fields.f_random.min'='1',\n" +
            " 'fields.f_random.max'='1000',\n" +
            "\n" +
            " 'fields.f_random_str.length'='10'\n" +
            ")";

    // sink table print
    private static final String PRINT_SINK_SQL = "create table sink_print ( \n" +
            " f_sequence INT," +
            " f_random INT," +
            " f_random_str STRING, " +
            " ts TIMESTAMP, " +
            " ua STRING " +
            ") with ('connector' = 'print' )";

    public static void main(String[] args) throws Exception {

        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);


        // 注册函数
        tEnv.createTemporarySystemFunction("ParseUserAgent", ParseUserAgent.class);


        tEnv.executeSql(DATA_SOURCE_SQL);
        tEnv.executeSql(PRINT_SINK_SQL);

        String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36";

        TableResult tableResult1 =  tEnv.executeSql("INSERT INTO  sink_print " +
                "SELECT f_sequence,f_random,f_random_str,ts,ParseUserAgent('" +
                ua + "') as ua FROM datagen_source"
        );

        // 通过 TableResult 来获取作业状态
        System.out.println(tableResult1.getJobClient().get().getJobStatus());


    }
}
