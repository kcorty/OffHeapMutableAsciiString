package unsafeSlab;

import utils.UnsafeBufferUtils;

public interface UnsafeCodec extends UnsafeCodecKeyHashGenerator {

    short bufferSize();

    long memOffset();

    void wrap(final long memOffset);

    default int keyHashCode() {
        return generateKeyHashCode(memOffset());
    }

    int keyOffset();

    int keyLength();
}
