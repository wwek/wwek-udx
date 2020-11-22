package com.iamle.bigdata.flink.udx;

/**
 * 电话号码归属地
 * <p>
 * 数据库使用
 * https://github.com/EeeMt/phone-number-geo
 */

import com.google.common.collect.ImmutableMap;
import com.iamle.parse.format.SpecifierParseFormat;
import me.ihxq.projects.pna.PhoneNumberInfo;
import me.ihxq.projects.pna.PhoneNumberLookup;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.Map;
import java.util.StringJoiner;

/**
 * @author wwek
 */
public class Phone2Region extends ScalarFunction {

    PhoneNumberLookup phoneNumberLookup = null;

    @Override
    public void open(FunctionContext context) throws Exception {
        phoneNumberLookup = new PhoneNumberLookup();
        super.open(context);
    }

    public String phone2Region(String str, String format) {
        String region = "未识别";
        if (str.isEmpty()) {
            return region;
        }
        if (!isPhoneNumber(str)) {
            return str;
        }

        PhoneNumberInfo found = phoneNumberLookup.lookup(str)
                .orElseThrow(RuntimeException::new);

        Map<String, String> resultMap = ImmutableMap.<String, String>builder()
                // province 省份-贵州
                .put("province", found.getAttribution().getProvince())
                // city 城市-贵阳
                .put("city", found.getAttribution().getCity())
                // isp ISP运营商-中国移动
                .put("isp", found.getIsp().getCnName())
                // zipcode 550000
                .put("zipcode", found.getAttribution().getZipCode())
                // areacode 0851
                .put("areacode", found.getAttribution().getAreaCode())
                .build();

        return new SpecifierParseFormat().ip2regionParseFormat(format, resultMap);
    }

    public String eval(String str) {
        return phone2Region(str, "%P %C %I");
    }

    public String eval(String str, String format) {
        return phone2Region(str, format);
    }

    public static boolean isPhoneNumber(String phoneNumber) {

        if (phoneNumber.length() == 3) {
            return (phoneNumber.charAt(0) == '1');
        } else if (phoneNumber.length() == 8) {
            return (phoneNumber.charAt(0) == '0');
        } else if (phoneNumber.length() == 11) {
            return (phoneNumber.charAt(0) == '0' || phoneNumber.charAt(0) == '1');
        } else if (phoneNumber.length() == 12) {
            return (phoneNumber.charAt(0) == '0');
        }
        return false;

    }
}
