package com.iamle.parse.format;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.Map;

/**
 * @author wwek
 */
public class SpecifierParseFormat {
    private static final String SpecifierJson = "%JSON";
    String strFormat;
    String strParseFormat;
    Map<String, String> specifierAndField;
    Map<String, String> fieldAndValue;

    public SpecifierParseFormat() {
    }

    public SpecifierParseFormat(String strFormat,
                                Map<String, String> specifierAndField,
                                Map<String, String> fieldAndValue
    ) {
        setStrFormat(strFormat);
        setSpecifierAndField(specifierAndField);
        setFieldAndValue(fieldAndValue);
    }

    /**
     * parseFormat
     *
     * @return String
     */
    public String parseFormat() {
        strParseFormat = strFormat;
        specifierAndField.forEach((key, value) -> {
            // System.out.println(key + "：" + value);
            String fieldValue = "";
            if (key.equals(SpecifierJson)) {
                Gson gson = new Gson();
                fieldValue = gson.toJson(fieldAndValue);
            } else {
                fieldValue = fieldAndValue.getOrDefault(value, "");
            }
            strParseFormat = strParseFormat.replace(key, fieldValue);
        });
        return strParseFormat;
    }

    /**
     * ip2regionParseFormat
     *
     * @param format        String
     * @param fieldAndValue Map<String, String>
     * @return String
     */
    public String ip2regionParseFormat(String format,
                                       Map<String, String> fieldAndValue) {
        Map<String, String> specifierAndField = ImmutableMap.<String, String>builder()
                // country 国家
                .put("%c", "country")
                // province 省份
                .put("%P", "province")
                // city 城市
                .put("%C", "city")
                // isp 运营商
                .put("%I", "isp")
                // area 区域
                .put("%A", "area")
                // %JSON 以json的方式结构化所有字段
                .put("%JSON", "json")
                .build();
        setStrFormat(format);
        setSpecifierAndField(specifierAndField);
        setFieldAndValue(fieldAndValue);
        return parseFormat();
    }

    /**
     * phone2regionParseFormat
     *
     * @param format        String
     * @param fieldAndValue Map<String, String>
     * @return String
     */
    public String phone2regionParseFormat(String format,
                                          Map<String, String> fieldAndValue) {
        Map<String, String> specifierAndField = ImmutableMap.<String, String>builder()
                // province 省份
                .put("%P", "province")
                // city 城市
                .put("%C", "city")
                // isp 运营商
                .put("%I", "isp")
                // zipcode
                .put("%zc", "zipcode")
                // areacode
                .put("%ac", "areacode")
                // %JSON 以json的方式结构化所有字段
                .put("%JSON", "json")
                .build();
        setStrFormat(format);
        setSpecifierAndField(specifierAndField);
        setFieldAndValue(fieldAndValue);
        return parseFormat();
    }

    /**
     * parseuseragentParseFormat
     *
     * @param format        String
     * @param fieldAndValue Map<String, String>
     * @return String
     */
    public String parseuseragentParseFormat(String format,
                                            Map<String, String> fieldAndValue) {
        Map<String, String> specifierAndField = ImmutableMap.<String, String>builder()
                // %DC DeviceClass Phone
                .put("%DC", "DeviceClass")
                // %DN DeviceName Google Nexus 6
                .put("%DN", "DeviceName")
                // %DB DeviceBrand Google
                .put("%DB", "DeviceBrand")

                // %OSNV OperatingSystemNameVersion Android 7.0
                .put("%OSNV", "OperatingSystemNameVersion")
                // %OSVB OperatingSystemVersionBuild NBD90Z
                .put("%OSVB", "OperatingSystemVersionBuild")
                // %OSC OperatingSystemClass Mobile
                .put("%OSC", "OperatingSystemClass")
                // %OSN OperatingSystemName Android
                .put("%OSN", "OperatingSystemName")
                // %OSV OperatingSystemVersion 7.0
                .put("%OSV", "OperatingSystemVersion")

                // %LENVM LayoutEngineNameVersionMajor Blink 53
                .put("%LENVM", "LayoutEngineNameVersionMajor")
                // %LEVM LayoutEngineVersionMajor 53
                .put("%LEVM", "LayoutEngineVersionMajor")
                // %LENV LayoutEngineNameVersion Blink 53.0
                .put("%LENV", "LayoutEngineNameVersion")
                // %LEC LayoutEngineClass Browser
                .put("%LEC", "LayoutEngineClass")
                // %LEN LayoutEngineName Blink
                .put("%LEN", "LayoutEngineName")
                // %LEV LayoutEngineVersion 53.0
                .put("%LEV", "LayoutEngineVersion")

                // %ANVM AgentNameVersionMajor Chrome 53
                .put("%ANVM", "AgentNameVersionMajor")
                // %AVM AgentVersionMajor 53
                .put("%AVM", "AgentVersionMajor")
                // %ANV AgentNameVersion Chrome 53.0.2785.124
                .put("%ANV", "AgentNameVersion")
                // %AC AgentClass Browser
                .put("%AC", "AgentClass")
                // %AN AgentName Chrome
                .put("%AN", "AgentName")
                // %AV AgentVersion 53.0.2785.124
                .put("%AV", "AgentVersion")

                // %JSON 以json的方式结构化所有字段
                .put("%JSON", "json")
                .build();
        setStrFormat(format);
        setSpecifierAndField(specifierAndField);
        setFieldAndValue(fieldAndValue);
        return parseFormat();
    }

    /**
     * splitByPer 按照分隔符%分割字符串为数组
     *
     * @param str String
     * @return String[]
     */
    public static String[] splitByPer(String str) {
        String delimiter = "%";
        return splitByDelimiter(str, delimiter);
    }

    /**
     * SplitByDelimiter 按照指定的分隔符分隔符分割字符串为数组
     * 如果字符为空则使用默认分隔符","
     *
     * @param str       String
     * @param delimiter String 分隔符
     * @return String[]
     */
    public static String[] splitByDelimiter(String str, String delimiter) {
        if (delimiter.isEmpty()) {
            delimiter = ",";
        }
        String[] strs = str.split(delimiter);
        return strs;
    }

    @Override
    public String toString() {
        parseFormat();
        return this.strParseFormat;
    }

    public void setStrFormat(String strFormat) {
        this.strFormat = strFormat;
    }

    public void setStrParseFormat(String strParseFormat) {
        this.strParseFormat = strParseFormat;
    }

    public void setSpecifierAndField(Map<String, String> specifierAndField) {
        this.specifierAndField = specifierAndField;
    }

    public void setFieldAndValue(Map<String, String> fieldAndValue) {
        this.fieldAndValue = fieldAndValue;
    }
}
