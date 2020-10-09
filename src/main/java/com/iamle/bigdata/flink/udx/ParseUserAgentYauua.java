/***
 *
 * 解析UserAgent
 *
 * 使用
 * https://yauaa.basjes.nl/UDF-ApacheFlinkTable.html
 */
package com.iamle.bigdata.flink.udx;


import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author wwek
 */
public class ParseUserAgentYauua extends ScalarFunction {
    private transient UserAgentAnalyzer userAgentAnalyzer;


//    public ParseUserAgentYauua(int cacheSize, List<String> extractedFields) {
//        this.cacheSize = cacheSize;
//        this.extractedFields = extractedFields;
//    }

    public String doParseUserAgent(String str, String field) {
        String resultStr = str;
        String unknown = "未知";

        UserAgent.ImmutableUserAgent immutableUserAgent = userAgentAnalyzer.parse(str);

        switch (field) {
            case "device":
                // 设备
                resultStr = immutableUserAgent.get("device").toString();
                break;
            case "type":
                // 类型 bot, browser, feed reader...
                resultStr = immutableUserAgent.get("type").toString();
                break;
            case "brand":
                // 品牌
                resultStr = immutableUserAgent.get("brand").toString();
                break;
            case "model":
                // 型号
                resultStr = immutableUserAgent.get("model").toString();
                break;
            case "os":
                // 操作系统
                resultStr = immutableUserAgent.get("os").toString();
                break;
            case "client":
                // client
                resultStr = immutableUserAgent.get("client").toString();
                break;
            default:
                //
                StringJoiner sj = new StringJoiner(",");
                sj.add(immutableUserAgent.get("device").toString())
                        .add(immutableUserAgent.get("type").toString())
                        .add(immutableUserAgent.get("brand").toString())
                        .add(immutableUserAgent.get("model").toString())
                        .add(immutableUserAgent.get("os").toString())
                        .add(immutableUserAgent.get("client").toString());
                resultStr = sj.toString();
        }
        return resultStr;
    }


    @Override
    public void open(FunctionContext context) {
        int cacheSize = 1000;

        userAgentAnalyzer = UserAgentAnalyzer
                .newBuilder()
//                .withFields(extractedFields)
                .withCache(cacheSize)
                .immediateInitialization()
                .build();

    }

    public String eval(String str) {
        return doParseUserAgent(str, "");
    }

    public String eval(String str, String field) {
        return doParseUserAgent(str, field);
    }
}
