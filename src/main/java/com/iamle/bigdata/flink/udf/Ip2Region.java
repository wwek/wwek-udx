package com.iamle.bigdata.flink.udf;

/**
 * ip归属地
 *
 * 数据库使用
 * https://gitee.com/lionsoul/ip2region
 */

import org.lionsoul.ip2region.Util;
import org.apache.flink.table.functions.ScalarFunction;

public class Ip2Region extends ScalarFunction {

    public String eval(String str) {
        // check is ip
        if (!Util.isIpAddress(str)) {
            return str;
        }

        return str.toUpperCase();
    }
}
