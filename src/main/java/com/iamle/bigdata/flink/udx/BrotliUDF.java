package com.iamle.bigdata.flink.udx;

import org.apache.flink.table.functions.ScalarFunction;

public class BrotliUDF extends ScalarFunction {
  public String eval(String str) {
    return org.brotli.dec.BrotliInputStream.class.getSimpleName();
  }
}
