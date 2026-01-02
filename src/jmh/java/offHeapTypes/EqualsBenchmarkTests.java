package offHeapTypes;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
public class EqualsBenchmarkTests {
    final DirectBufferUnsafeString directBufferUnsafeString1 = new DirectBufferUnsafeString(40);

    final DirectBufferUnsafeString directBufferUnsafeString2 = new DirectBufferUnsafeString(40);

    final DirectBufferUnsafeString directBufferUnsafeString3 = new DirectBufferUnsafeString(48);

    final MemorySegmentAsciiString segment1 = new MemorySegmentAsciiString(40);
    final MemorySegmentAsciiString segment2 = new MemorySegmentAsciiString(40);

    String string1 = null;
    String string2 = null;

    @Setup(Level.Trial)
    public void setup() {
        directBufferUnsafeString1.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567891");
        directBufferUnsafeString2.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567892");
        directBufferUnsafeString3.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567893");
        string1 = "ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567894";
        string2 = "ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567895";
        segment1.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567896");
        segment2.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567897");
    }


    @Benchmark
    public void testUnsafeEquals(final Blackhole blackhole) {
        blackhole.consume(directBufferUnsafeString1.equals(directBufferUnsafeString2));
    }

    @Benchmark
    public void testFastEquals(final Blackhole blackhole) {
        blackhole.consume(directBufferUnsafeString1.equals(directBufferUnsafeString3));
    }

    @Benchmark
    public void testStringEquals(final Blackhole blackhole) {
        blackhole.consume(string1.equals(string2));
    }

    @Benchmark
    public void testMemorySegmentVectorEquals(final Blackhole blackhole) {
        blackhole.consume(MemorySegmentAsciiString.memorySegmentEquals(
                segment1.memorySegment, segment2.memorySegment));
    }
}
