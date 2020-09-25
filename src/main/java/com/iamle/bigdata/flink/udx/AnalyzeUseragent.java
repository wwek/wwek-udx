//package com.iamle.bigdata.flink.udx;
//
//import nl.basjes.parse.useragent.flink.table.AnalyzeUseragentFunction;
//import org.apache.flink.table.functions.FunctionContext;
//import org.apache.flink.table.functions.ScalarFunction;
//
//import java.util.Map;
//
//
///**
// * @author wwek
// */
//public class AnalyzeUseragent extends ScalarFunction {
//    AnalyzeUseragentFunction analyzeUseragentFunc = null;
//
//    @Override
//    public void open(FunctionContext context) throws Exception {
//        analyzeUseragentFunc = new AnalyzeUseragentFunction();
//        super.open(context);
//    }
//
//    public Map<String, String> eval(String str) {
//        return analyzeUseragentFunc.eval(str);
//    }
//}
