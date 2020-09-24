package com.iamle.bigdata.flink.udf;

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

    public String phone2Region(String str, int c) {
        int cLimit = 5;
        if (str.isEmpty()) {
            return str;
        }
        if (!isPhoneNumber(str)) {
            return str;
        }
        if (c < 0 || c > cLimit) {
            // 默认1为城市
            c = 1;
        }
        String region = "未识别";

        PhoneNumberInfo found = phoneNumberLookup.lookup(str)
                .orElseThrow(RuntimeException::new);
        switch (c) {
            case 0:
                // 贵州
                region = found.getAttribution().getProvince();
                break;
            case 1:
                // 贵阳
                region = found.getAttribution().getCity();
                break;
            case 2:
                // ISP.CHINA_MOBILE
                region = found.getIsp().toString();

                // 中国移动
                region = found.getIsp().getCnName();
                break;
            case 3:
                // 550000
                region = found.getAttribution().getZipCode();
                break;
            case 4:
                // 0851
                region = found.getAttribution().getAreaCode();
                break;
            default:
                // 18798896741
                region = found.getNumber();
        }

        return region;
    }

    public String eval(String str) {
        return phone2Region(str, 1);
    }

    public String eval(String str, int c) {
        return phone2Region(str, c);
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
