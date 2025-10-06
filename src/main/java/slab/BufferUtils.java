package slab;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;
import org.agrona.UnsafeApi;

public class BufferUtils {

    public static void resetBuffer(final MutableDirectBuffer buffer) {
        buffer.setMemory(0, buffer.capacity(), (byte) 0);
    }

    public static void resetBuffer(final MutableDirectBuffer buffer, final int offset) {
        buffer.setMemory(offset, buffer.capacity() - offset, (byte) 0);
    }

    public static void resetBuffer(final MutableDirectBuffer buffer, final int offset, final int length) {
        buffer.setMemory(offset, length, (byte) 0);
    }

    public static boolean bufferEquals(final DirectBuffer buffer, final DirectBuffer other,
                                       final int otherOffset, final int otherLength) {
        int i = 0;

        for (final int end = otherLength & ~7; i < end; i +=8) {
            if (buffer.getLong(i) != other.getLong(otherOffset + i)) {
                return false;
            }
        }
        for (; i < otherLength; i++) {
            if (buffer.getByte(i) != other.getByte(otherOffset + i)) {
                return false;
            }
        }
        return true;
    }

    public static int segmentHashCode(final DirectBuffer buffer, final int offset,
                                      final int length) {
        int i = 0, hashCode = 10;
        for (final int end = length & ~7; i < end; i += 8) {
            hashCode = (hashCode << 5) - hashCode + Long.hashCode(buffer.getLong(offset + i));
        }

        for (; i < length; i++) {
            hashCode = (hashCode << 5) - hashCode + buffer.getByte(offset + i);
        }
        return hashCode;
    }
}
