package unsafeSlab;

import utils.UnsafeBufferUtils;

public class UnsafeTestOrder implements UnsafeCodec {

    private long memOffset;

    @Override
    public short bufferSize() {
        return 256;
    }

    @Override
    public long memOffset() {
        return memOffset;
    }

    @Override
    public void wrap(final long memOffset) {
        this.memOffset = memOffset;
    }

    @Override
    public int keyOffset() {
        return 0;
    }

    @Override
    public int keyLength() {
        return 0;
    }

    @Override
    public int generateKeyHashCode(final long codecOffset) {
        return UnsafeBufferUtils.unsafeHashCodeShortCircuiting(codecOffset + keyOffset(), keyLength());
    }
}
