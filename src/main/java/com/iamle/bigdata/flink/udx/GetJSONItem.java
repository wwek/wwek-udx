package com.iamle.bigdata.flink.udx;


import com.alibaba.fastjson.JSONObject;
import com.iamle.utils.DateUtils;
import com.iamle.utils.JSONUtils;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.functions.ScalarFunction;

public class GetJSONItem extends ScalarFunction {

    public GetJSONItem() {
    }

    @Override
    public TypeInformation<?> getResultType(Class<?>[] signature) {
        return super.getResultType(signature);
    }

    public String eval(String json, String keyStr) {

        try {
            return JSONUtils.getJSONField(JSONUtils.getJSONObjectFromString(json), keyStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}