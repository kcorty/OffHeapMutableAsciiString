package utils;

import org.agrona.UnsafeApi;

public class UnsafeBufferUtils {

    public static void resetBuffer(final long memOffset, final int length) {
        UnsafeApi.setMemory(memOffset, memOffset + length, (byte) 0);
    }

    public static void resetBuffer(final long memOffset, final int readOffset, final int length) {
        UnsafeApi.setMemory(memOffset + readOffset, memOffset + length, (byte) 0);
    }

    public static boolean bufferEquals(final long memOffset, final long otherMemOffset, final int length) {
        int i = 0;

        for (final int end = length & ~7; i < end; i += 8) {
            if (UnsafeApi.getLong(memOffset + i) != UnsafeApi.getLong(otherMemOffset + i)) {
                return false;
            }
        }

        for (; i < length; i++) {
            if (UnsafeApi.getByte(memOffset + i) != UnsafeApi.getByte(otherMemOffset + i)) {
                return false;
            }
        }
        return true;
    }

    public static int unsafeHashCode(final long memOffset, final int length) {
        int i = 0, hashCode = 19;
        for (final int end = length & ~7; i < end; i += 8) {
            hashCode = hashCode * 31 + Long.hashCode(UnsafeApi.getLong(memOffset + i));
        }
        for (; i < length; i++) {
            hashCode = hashCode * 31 + UnsafeApi.getByte(memOffset + i);
        }
        return hashCode;
    }

    public static int unsafeHashCodeShortCircuiting(final long memOffset, final int length) {
        int i = 0, hashCode = 19;
        for (final int end = length & ~7; i < end; i += 8) {
            final long value = UnsafeApi.getLong(memOffset + i);
            if (value == 0) {
                return hashCode;
            }
            hashCode = hashCode * 31 + Long.hashCode(value);
        }
        for (; i < length; i++) {
            hashCode = hashCode * 31 + UnsafeApi.getByte(memOffset + i);
        }
        return hashCode;
   }
}
