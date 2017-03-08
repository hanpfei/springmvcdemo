package com.hanpfei;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class GeneralUtils {
    private static final String DEFAULT_LOCAL_IP = "127.0.0.1";
    private static final String SEPERATOR = "_";

    private static final String[] BASE62_ELEMENTS = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z" };

    public static String dsProcess(String rawData) {
        // for datastream
        if (rawData.startsWith("\"")) {
            rawData = rawData.substring(1);
        }
        if (rawData.endsWith("\"")) {
            rawData = rawData.substring(0, rawData.length() - 1);
        }
        rawData = StringEscapeUtils.unescapeJava(rawData);

        return rawData;
    }

    public static String getString(String value, String defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generateId(byte[] input) {
        return UUID.nameUUIDFromBytes(input).toString().replaceAll("-", "");
    }

    public static String rowKey(String... args) {
        StringBuffer sb = new StringBuffer();
        int length = args.length;
        for (int i = 0; i < length; i++) {
            sb.append(args[i]);
            if (i != length - 1) {
                sb.append(SEPERATOR);
            }
        }
        return sb.toString();
    }

    public static boolean isHashKey(String key) {
        boolean b = false;
        String[] args = key.split(SEPERATOR);
        int len = args.length;
        if (len == 2 && args[0].length() == 32) {
            b = true;
        }
        return b;
    }

    public static String shortenString(String input) {
        long hashCode = hashCode(input.toCharArray());
        return convertToBase62String(hashCode);
    }

    private static long hashCode(final char[] value) {
        long h = 0;
        if (value.length > 0) {
            for (int i = 0; i < value.length; i++) {
                h = 31 * h + value[i];
            }
        }
        return h;
    }

    private static String convertToBase62String(long target) {
        List<Long> digitList = new ArrayList<Long>();
        long num = Math.abs(target);
        long remainder = 0;

        while (num > 0) {
            remainder = num % 62;
            digitList.add(remainder);
            num = num / 62;
        }

        Collections.reverse(digitList);
        StringBuffer sb = new StringBuffer();
        for (long digit : digitList) {
            sb.append(BASE62_ELEMENTS[(int) digit]);
        }

        return sb.toString();
    }

    /**
     * Get local ip address from network interface.
     *
     * @return the local ip address
     */
    public static String getLocalIp() {
        String localIp = null;
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
            if (!DEFAULT_LOCAL_IP.equals(localIp)) {
                // if ip is not 127.0.0.1, return it
                return localIp;
            }
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        }

        try {
            // get ip from network interfaces
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress ip = address.nextElement();
                    if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println(e.getMessage());
        }

        // local ip not found
        try {
            Socket socket = new Socket("www.w3c.org", 80);
            localIp = socket.getLocalAddress().getHostAddress();
            socket.close();

            return localIp;
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.err.println("local ip not found, use default");

        return DEFAULT_LOCAL_IP;
    }
}
