package com.iamle.bigdata.flink.udf;

public class Test {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() / 1000);

        // Ip2Region
        Ip2Region ip2Region = new Ip2Region();
        System.out.println(ip2Region.ip2Region("114.114.114.114", 0));
    }
}
