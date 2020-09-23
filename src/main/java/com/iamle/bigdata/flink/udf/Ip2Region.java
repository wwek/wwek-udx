package com.iamle.bigdata.flink.udf;

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

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.apache.flink.table.functions.ScalarFunction;

import javax.annotation.PostConstruct;
import java.io.File;

public class Ip2Region extends ScalarFunction {
    DbConfig config = null;
    DbSearcher searcher = null;

    /**
     * 初始化IP库
     */
    @PostConstruct
    public void init() {
        try {
            // 因为jar无法读取文件,复制创建临时文件
            String tmpDir = System.getProperty("user.dir") + File.separator + "temp";
            String dbPath = tmpDir + File.separator + "ip2region.db";
            log.info("init ip region db path [{}]", dbPath);
            File file = new File(dbPath);
            FileUtils.copyInputStreamToFile(IpRegionService.class.getClassLoader().getResourceAsStream("data/ip2region.db"), file);
            config = new DbConfig();
            searcher = new DbSearcher(config, dbPath);
            log.info("bean [{}]", config);
            log.info("bean [{}]", searcher);
        } catch (Exception e) {
            log.error("init ip region error:{}", e);
        }
    }

    public String ip2Region(String str, int c) {
        int cLimit = 5;
        if (str.isEmpty()) {
            return str;
        }
        // check is ip
        if (!Util.isIpAddress(str)) {
            return str;
        }
        if (c < 0 || c > cLimit) {
            c = 0;
        }
        String region = "";


        try {
            //db
            if (searcher == null) {
                log.error("DbSearcher is null");
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
            }

            DataBlock dataBlock = null;
            if (Util.isIpAddress(ip) == false) {
                log.warn("warning: Invalid ip address");
            }
            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String result = dataBlock.getRegion();
            long endTime = System.currentTimeMillis();
            log.debug("region use time[{}] result[{}]", endTime - startTime, result);
            return result;

        } catch (Exception e) {
            log.error("error:{}", e);
        }
        return null;


        switch (c) {
            case 0:
                // 贵州
                region = found.getAttribution().getProvince();
                break;
            case 1:
                // 贵阳
                region = found.getAttribution().getCity();
                break;
            case 2:
                // ISP.CHINA_MOBILE
                region = found.getIsp().toString();

                // 中国移动
                region = found.getIsp().getCnName();
                break;
            case 3:
                // 550000
                region = found.getAttribution().getZipCode();
                break;
            case 4:
                // 0851
                region = found.getAttribution().getAreaCode();
                break;
            default:
                // 18798896741
                region = found.getNumber();
        }

        return region;
    }

    public String eval(String str) {
        return ip2Region(str, 1);
    }

    public String eval(String str, int c) {
        return ip2Region(str, c);
    }
}
