
package org.apache.fineract.infrastructure.reportmailingjob.helper;
import com.google.common.base.Splitter;
import java.net.InetAddress;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class IPv4Helper {
    private IPv4Helper() {
    }
    private static final Logger LOG = LoggerFactory.getLogger(IPv4Helper.class);
    public static long ipAddressToLong(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new IllegalArgumentException("ip address cannot be null or empty");
        }
        List<String> octets = Splitter.onPattern(java.util.regex.Pattern.quote(".")).splitToList(ipAddress);
        if (octets.size() != 4) {
            throw new IllegalArgumentException("invalid ip address");
        }
        long ip = 0;
        for (int i = 3; i >= 0; i--) {
            long octet = Long.parseLong(octets.get(3 - i));
            if (octet > 255 || octet < 0) {
                throw new IllegalArgumentException("invalid ip address");
            }
            ip |= octet << (i * 8);
        }
        return ip;
    }
    public static String longToIpAddress(long ip) {
        if (ip > 4294967295L || ip < 0) {
            throw new IllegalArgumentException("invalid ip");
        }
        StringBuilder ipAddress = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int shift = i * 8;
            ipAddress.append((ip & (0xff << shift)) >> shift);
            if (i > 0) {
                ipAddress.append(".");
            }
        }
        return ipAddress.toString();
    }
    public static boolean ipAddressIsInRange(final String ipAddress, final String startOfRange, final String endOfRange) {
        final long ipAddressToLong = ipAddressToLong(ipAddress);
        final long startOfRangeToLong = ipAddressToLong(startOfRange);
        final long endOfRangeToLong = ipAddressToLong(endOfRange);
        long diff = ipAddressToLong - startOfRangeToLong;
        return (diff >= 0 && (diff <= (endOfRangeToLong - startOfRangeToLong)));
    }
    public static boolean applicationIsRunningOnLocalMachine() {
        boolean isRunningOnLocalMachine = false;
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            if (localHost != null) {
                final String hostAddress = localHost.getHostAddress();
                final String startOfIpAddressRange = "127.0.0.0";
                final String endOfIpAddressRange = "127.255.255.255";
                if (StringUtils.isNotEmpty(hostAddress)) {
                    isRunningOnLocalMachine = ipAddressIsInRange(hostAddress, startOfIpAddressRange, endOfIpAddressRange);
                }
            }
        }
        catch (Exception exception) {
            LOG.error("Problem occurred in applicationIsRunningOnLocalMachine function", exception);
        }
        return isRunningOnLocalMachine;
    }
    public static boolean applicationIsNotRunningOnLocalMachine() {
        return !applicationIsRunningOnLocalMachine();
    }
}
