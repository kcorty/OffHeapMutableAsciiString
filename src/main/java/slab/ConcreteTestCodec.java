package slab;

import org.agrona.concurrent.UnsafeBuffer;

import java.nio.ByteBuffer;

//Instance of TestCodec with a pre-allocated buffer
public class ConcreteTestCodec extends TestCodec {

    public ConcreteTestCodec() {
        this.buffer().wrap(new UnsafeBuffer(ByteBuffer.allocateDirect(BUFFER_SIZE)));
    }
}
