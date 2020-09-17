//package org.apache.zeppelin.flink.udf
//
//import org.apache.flink.table.functions.TableFunction
//
//// The generic type "(String, Int)" determines the schema of the returned table as (String, Integer).
//class ScalaSplit extends TableFunction[(String, Int)] {
//  def eval(str: String): Unit = {
//    // use collect(...) to emit a row.
//    str.split("#").foreach(x => collect((x, x.length)))
//  }
//}
