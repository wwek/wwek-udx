package com.iamle.bigdata.flink.udx;

import org.apache.flink.table.functions.ScalarFunction;

public class JavaDateTime extends ScalarFunction {
    public String eval(String str) {
        return new org.joda.time.DateTime().toString();
    }
}
