package offHeapTypes;

import org.agrona.UnsafeApi;

public class UnsafeString {
    private long memOffset;
    private final int length;

    public UnsafeString(final int length) {
        this.memOffset = 0;
        this.length = length;
    }

    public UnsafeString(final long memOffset, final int length) {
        this.memOffset = memOffset;
        this.length = length;
    }

    public void wrap(final long memOffset) {
        this.memOffset = memOffset;
    }

    public void set(final CharSequence charSequence) {
        if (charSequence.length() > length) {
            throw new IndexOutOfBoundsException("CharSequence too long!");
        }

        int i = 0;
        for (; i < charSequence.length(); i++) {
            UnsafeApi.putChar(memOffset + i, charSequence.charAt(i));
        }

        padRemainder(charSequence.length());
    }


    public void padRemainder(final int beginOffset) {
        int i = beginOffset;
        for (final int end = length & ~7; i < end; i += 8) {
            UnsafeApi.putLong(memOffset + i, 0);
        }
        for (; i < length; i++) {
            UnsafeApi.putByte(memOffset + i, (byte) 0);
        }
    }
}
