package com.iamle.bigdata.flink.udx;

/**
 * 电话号码归属地
 * <p>
 * 数据库使用
 * https://github.com/EeeMt/phone-number-geo
 */

import me.ihxq.projects.pna.PhoneNumberInfo;
import me.ihxq.projects.pna.PhoneNumberLookup;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

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

    public String phone2Region(String str, String field) {
        String region = "未识别";
        if (str.isEmpty()) {
            return region;
        }
        if (!isPhoneNumber(str)) {
            return str;
        }

        PhoneNumberInfo found = phoneNumberLookup.lookup(str)
                .orElseThrow(RuntimeException::new);
        switch (field) {
            case "province":
                // 省份-贵州
                region = found.getAttribution().getProvince();
                break;
            case "city":
                // 省份-贵阳
                region = found.getAttribution().getCity();
                break;
            case "isp":
                // ISP.CHINA_MOBILE
                // region = found.getIsp().toString();

                // 中国移动
                region = found.getIsp().getCnName();
                break;
            case "zipcode":
                // 550000
                region = found.getAttribution().getZipCode();
                break;
            case "areacode":
                // 0851
                region = found.getAttribution().getAreaCode();
                break;
            default:
                // 省份,城市,运营商
                StringJoiner sj = new StringJoiner(",");
                sj.add(found.getAttribution().getProvince())
                        .add(found.getAttribution().getCity())
                        .add(found.getIsp().getCnName());
                region = sj.toString();
        }

        return region;
    }

    public String eval(String str) {
        return phone2Region(str, "all");
    }

    public String eval(String str, String field) {
        return phone2Region(str, field);
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
