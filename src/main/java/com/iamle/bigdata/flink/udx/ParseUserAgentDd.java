/***
 *
 * 解析UserAgent
 *
 * 使用
 * https://github.com/mngsk/device-detector
 */
package com.iamle.bigdata.flink.udx;


import io.github.mngsk.devicedetector.Detection;
import io.github.mngsk.devicedetector.DeviceDetector;
import io.github.mngsk.devicedetector.DeviceDetector.DeviceDetectorBuilder;
import io.github.mngsk.devicedetector.client.Client;
import io.github.mngsk.devicedetector.device.Device;
import io.github.mngsk.devicedetector.operatingsystem.OperatingSystem;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.StringJoiner;


/**
 * @author wwek
 */
public class ParseUserAgentDd extends ScalarFunction {
    DeviceDetector ua = null;

    public String doParseUserAgent(String str, String field) {
        String resultStr = str;
        String unknown = "未知";
        Detection detection = ua.detect(str);

        switch (field) {
            case "device":
                // 设备
                resultStr = detection.getDevice().map(Device::toString).orElse(unknown);
                break;
            case "type":
                // 类型 bot, browser, feed reader...
                if (detection.getDevice().isPresent()) {
                    resultStr = detection.getDevice().get().getType();
                } else {
                    resultStr = unknown;
                }
                break;
            case "brand":
                // 品牌
                if (detection.getDevice().isPresent()) {
                    resultStr = detection.getDevice().get().getBrand().orElse(unknown);
                } else {
                    resultStr = unknown;
                }
                break;
            case "model":
                // 型号
                if (detection.getDevice().isPresent()) {
                    resultStr = detection.getDevice().get().getModel().orElse(unknown);
                } else {
                    resultStr = unknown;
                }
                break;
            case "os":
                // 操作系统
                resultStr = detection.getOperatingSystem().map(OperatingSystem::toString).orElse(unknown);
                break;
            case "client":
                // client
                resultStr = detection.getClient().map(Client::toString).orElse(unknown);
                break;
            default:
                StringJoiner sj = new StringJoiner(",");
                sj.add(detection.getDevice().map(Device::toString).orElse(unknown))
                        .add(detection.getOperatingSystem().map(OperatingSystem::toString).orElse(unknown))
                        .add(detection.getClient().map(Client::toString).orElse(unknown));
                resultStr = sj.toString();
        }
        return resultStr;
    }


    @Override
    public void open(FunctionContext context) throws Exception {
        ua = new DeviceDetectorBuilder().build();
        super.open(context);
    }

    public String eval(String str) {
        return doParseUserAgent(str, "");
    }

    public String eval(String str, String field) {
        return doParseUserAgent(str, field);
    }
}
