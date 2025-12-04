package slab;

import org.agrona.MutableDirectBuffer;

public interface Codec extends CodecKeyHashGenerator {

    short bufferSize();

    void wrap(final MutableDirectBuffer buffer, final int offset, final int length);

    MutableDirectBuffer buffer();

    default int keyHashCode() {
        return generateKeyHashCode(buffer(), 0);
    }

    int keyOffset();

    int keyLength();
}
