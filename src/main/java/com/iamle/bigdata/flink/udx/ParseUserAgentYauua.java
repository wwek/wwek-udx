/***
 *
 * 解析UserAgent
 *
 * 使用
 * https://yauaa.basjes.nl/UDF-ApacheFlinkTable.html
 */
package com.iamle.bigdata.flink.udx;

import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.List;
import java.util.Map;

/**
 * @author wwek
 */
public class ParseUserAgentYauua extends ScalarFunction {
    private transient UserAgentAnalyzer userAgentAnalyzer;

    private int cacheSize = 1000;
    private final List<String> extractedFields;

    public ParseUserAgentYauua(int cacheSize, List<String> extractedFields) {
        this.cacheSize = cacheSize;
        this.extractedFields = extractedFields;
    }

    @Override
    public void open(FunctionContext context) {

        assert extractedFields != null;
        extractedFields.addAll(userAgentAnalyzer.getAllPossibleFieldNamesSorted());

        userAgentAnalyzer = UserAgentAnalyzer
                .newBuilder()
                .withFields(extractedFields)
                .withCache(cacheSize)
                .immediateInitialization()
                .build();

    }

    public Map<String, String> eval(String userAgentString) {
        return userAgentAnalyzer.parse(userAgentString).toMap(extractedFields);
    }
}
