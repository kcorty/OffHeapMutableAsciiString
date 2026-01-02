package slab;

import offHeapTypes.DirectBufferUnsafeString;
import org.agrona.concurrent.UnsafeBuffer;

import java.nio.ByteBuffer;

public class ConcreteTestOrder extends TestOrder {

    private final DirectBufferUnsafeString directBufferUnsafeString;

    public ConcreteTestOrder() {
        this.buffer().wrap(new UnsafeBuffer(ByteBuffer.allocateDirect(this.bufferSize())));
        this.directBufferUnsafeString = new DirectBufferUnsafeString(this.buffer(), keyOffset(), keyLength());
    }

    public DirectBufferUnsafeString getUnsafeAsciiString() {
        return this.directBufferUnsafeString;
    }

}
