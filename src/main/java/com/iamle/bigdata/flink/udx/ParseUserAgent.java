/***
 *
 * 解析UserAgent
 *
 * 使用
 * https://yauaa.basjes.nl/UDF-ApacheFlinkTable.html
 */
package com.iamle.bigdata.flink.udx;


import com.google.common.collect.ImmutableMap;
import com.iamle.parse.format.SpecifierParseFormat;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.List;
import java.util.Map;

/**
 * @author wwek
 */
public class ParseUserAgent extends ScalarFunction {
    private transient UserAgentAnalyzer userAgentAnalyzer;


//    public ParseUserAgentYauua(int cacheSize, List<String> extractedFields) {
//        this.cacheSize = cacheSize;
//        this.extractedFields = extractedFields;
//    }

    public String doParseUserAgent(String str, String format) {
        String resultStr = str;
        String unknown = "未知";

        UserAgent.ImmutableUserAgent immutableUserAgent = userAgentAnalyzer.parse(str);

        // 设备
        String DeviceClass = immutableUserAgent.get("DeviceClass").getValue();
        String DeviceName = immutableUserAgent.get("DeviceName").getValue();
        String DeviceBrand = immutableUserAgent.get("DeviceBrand").getValue();
        // 操作系统
        String OperatingSystemClass = immutableUserAgent.get("OperatingSystemClass").getValue();
        String OperatingSystemName = immutableUserAgent.get("OperatingSystemName").getValue();
        String OperatingSystemVersion = immutableUserAgent.get("OperatingSystemVersion").getValue();
        String OperatingSystemNameVersion = immutableUserAgent.get("OperatingSystemNameVersion").getValue();
        String OperatingSystemVersionBuild = immutableUserAgent.get("OperatingSystemVersionBuild").getValue();

        String LayoutEngineClass = immutableUserAgent.get("LayoutEngineClass").getValue();
        String LayoutEngineName = immutableUserAgent.get("LayoutEngineName").getValue();
        String LayoutEngineVersion = immutableUserAgent.get("LayoutEngineVersion").getValue();
        String LayoutEngineVersionMajor = immutableUserAgent.get("LayoutEngineVersionMajor").getValue();
        String LayoutEngineNameVersion = immutableUserAgent.get("LayoutEngineNameVersion").getValue();
        String LayoutEngineNameVersionMajor = immutableUserAgent.get("LayoutEngineNameVersionMajor").getValue();
        String AgentClass = immutableUserAgent.get("AgentClass").getValue();
        String AgentName = immutableUserAgent.get("AgentName").getValue();
        String AgentVersion = immutableUserAgent.get("AgentVersion").getValue();
        String AgentVersionMajor = immutableUserAgent.get("AgentVersionMajor").getValue();
        String AgentNameVersion = immutableUserAgent.get("AgentNameVersion").getValue();
        String AgentNameVersionMajor = immutableUserAgent.get("AgentNameVersionMajor").getValue();


        Map<String, String> resultMap = ImmutableMap.<String, String>builder()
                .put("DeviceClass", DeviceClass)
                .put("DeviceName", DeviceName)
                .put("DeviceBrand", DeviceBrand)
                .put("OperatingSystemClass", OperatingSystemClass)
                .put("OperatingSystemName", OperatingSystemName)
                .put("OperatingSystemVersion", OperatingSystemVersion)
                .put("OperatingSystemNameVersion", OperatingSystemNameVersion)
                .put("OperatingSystemVersionBuild", OperatingSystemVersionBuild)
                .put("LayoutEngineClass", LayoutEngineClass)
                .put("LayoutEngineName", LayoutEngineName)
                .put("LayoutEngineVersion", LayoutEngineVersion)
                .put("LayoutEngineVersionMajor", LayoutEngineVersionMajor)
                .put("LayoutEngineNameVersion", LayoutEngineNameVersion)
                .put("LayoutEngineNameVersionMajor", LayoutEngineNameVersionMajor)
                .put("AgentClass", AgentClass)
                .put("AgentName", AgentName)
                .put("AgentVersion", AgentVersion)
                .put("AgentVersionMajor", AgentVersionMajor)
                .put("AgentNameVersion", AgentNameVersion)
                .put("AgentNameVersionMajor", AgentNameVersionMajor)
                .build();
        return new SpecifierParseFormat().parseuseragentParseFormat(format, resultMap);
    }


    @Override
    public void open(FunctionContext context) {
        int cacheSize = 15000;

        userAgentAnalyzer = UserAgentAnalyzer
                .newBuilder()
                //.withFields(extractedFields)
                .withCache(cacheSize)
                .immediateInitialization()
                .build();

    }

    public String eval(String str) {
        return doParseUserAgent(str, "%DN %OSNV %LENV %ANV");
    }

    public String eval(String str, String format) {
        return doParseUserAgent(str, format);
    }
}
