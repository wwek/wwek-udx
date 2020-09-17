package org.apache.zeppelin.flink.udf;

/**
 * Accumulator for WeightedAvg.
 */
public class WeightedAvgAccum {
  public long sum = 0;
  public int count = 0;
}
