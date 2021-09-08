package com.iamle.bigdata.flink.udx;

import com.iamle.utils.DateUtils;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.scala.typeutils.Types;
import org.apache.flink.table.functions.ScalarFunction;

public class GetTimestamp extends ScalarFunction {

    public GetTimestamp() {

    }

    @Override
    public TypeInformation<?> getResultType(Class<?>[] signature) {
        return Types.SQL_TIMESTAMP();
    }

    public Long eval(String dateStr) {

        try {
            return DateUtils.getTimestamp(dateStr, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}