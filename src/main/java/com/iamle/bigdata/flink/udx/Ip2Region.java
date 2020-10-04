/**
 * ip归属地
 * <p>
 * 数据库使用
 * https://gitee.com/lionsoul/ip2region
 * <p>
 * <p>
 * 把db文件打包到resources
 * https://github.com/lionsoul2014/ip2region/issues/148
 */

package com.iamle.bigdata.flink.udx;

import org.apache.commons.io.FileUtils;
import org.apache.flink.table.functions.FunctionContext;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.apache.flink.table.functions.ScalarFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.StringJoiner;

/**
 * @author wwek
 */
public class Ip2Region extends ScalarFunction {
    final Logger Log = LoggerFactory.getLogger(getClass());
    DbConfig config = null;
    DbSearcher searcher = null;

    @Override
    public void open(FunctionContext context) throws Exception {
        try {
            // 因为jar无法读取文件,复制创建临时文件
            String tmpDir = System.getProperty("user.dir") + File.separator + "temp";
            String dbPath = tmpDir + File.separator + "ip2region.db";
            System.out.println(dbPath);
            Log.info("init ip region db path [{}]", dbPath);
            File file = new File(dbPath);

            //从资源文件中拿db文件
            InputStream dbF = Ip2Region.class.getClassLoader().getResourceAsStream("data/ip2region.db");

            // 操作复制
            FileUtils.copyInputStreamToFile(dbF, file);
            config = new DbConfig();
            searcher = new DbSearcher(config, dbPath);
            Log.info("bean [{}]", config);
            Log.info("bean [{}]", searcher);
        } catch (Exception e) {
            Log.error("init ip region error:{}", e);
        }
        super.open(context);
    }

    public String doIp2Region(String str, String field) {
        String region = "未识别";

        // check is ip
        if (!Util.isIpAddress(str)) {
            return region;
        }

        try {
            //db
            if (searcher == null) {
                Log.error("DbSearcher is null");
                return null;
            }
            long startTime = System.currentTimeMillis();
            //查询算法
            int algorithm = DbSearcher.MEMORY_ALGORITYM;
            //B-tree
            //DbSearcher.BINARY_ALGORITHM //Binary
            //DbSearcher.MEMORY_ALGORITYM //Memory
            //define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
            }

            DataBlock dataBlock = null;
            if (!Util.isIpAddress(str)) {
                Log.warn("warning: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, str);
            String result = dataBlock.getRegion();
            long endTime = System.currentTimeMillis();
            Log.debug("region use time[{}] result[{}]", endTime - startTime, result);

            // 国家|区域|省份|城市|ISP
            // 中国|0|江苏省|苏州市|联通
            String[] resultArray = result.split("\\|");

            switch (field) {
                case "country":
                    // 国家-中国
                    region = resultArray[0];
                    break;
                case "area":
                    // 区域-0
                    region = resultArray[1];
                    break;
                case "province":
                    // 省份-江苏省
                    region = resultArray[2];
                    break;
                case "city":
                    // 城市-苏州市
                    region = resultArray[3];
                    break;
                case "isp":
                    // ISP运营商-联通
                    region = resultArray[4];
                    break;
                default:
                    // 国家,省份,城市,运营商,区域
                    StringJoiner sj = new StringJoiner(",");
                    sj.add(resultArray[0]).add(resultArray[2]).add(resultArray[3])
                            .add(resultArray[4]).add(resultArray[1]);
                    region = sj.toString();
            }

        } catch (Exception e) {
            Log.error("error:{}", e);
        }

        return region;
    }

    public String eval(String str) {
        return doIp2Region(str, "all");
    }

    public String eval(String str, String field) {
        return doIp2Region(str, field);
    }
}
