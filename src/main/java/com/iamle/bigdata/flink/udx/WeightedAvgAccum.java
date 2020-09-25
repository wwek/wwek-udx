package com.iamle.bigdata.flink.udx;

/**
 * Accumulator for WeightedAvg.
 */
public class WeightedAvgAccum {
  public long sum = 0;
  public int count = 0;
}
