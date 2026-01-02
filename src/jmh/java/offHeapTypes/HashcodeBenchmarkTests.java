package offHeapTypes;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
public class HashcodeBenchmarkTests {
    final DirectBufferUnsafeString directBufferUnsafeString1 = new DirectBufferUnsafeString(40);

    final MemorySegmentAsciiString segment1 = new  MemorySegmentAsciiString(40);

    String string1 = null;
    private final String stringBase = "ABCDEFGHIJKLMNOPQRSTUVQXYZ";
    int i = 0;

    final DirectBufferByteString directBufferByteString1 = new DirectBufferByteString(40);

    @Setup(Level.Trial)
    public void setup() {
        i = 0;
        directBufferUnsafeString1.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567891");
        string1 = "ABCDEFGHIJKLMNOPQRSTUVQXYZ1";
        segment1.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ123456789");
        directBufferByteString1.set("ABCDEFGHIJKLMNOPQRSTUVQXYZ1234567891");
    }

    //Add the string1 setting so the String hash can't cache the hash result, giving a more even comparison
    @Benchmark
    public void testUnsafeHash(final Blackhole blackhole) {
        string1 = stringBase + i;
        blackhole.consume(directBufferUnsafeString1.hashCode());
    }

    @Benchmark
    public void testStringHash(final Blackhole blackhole) {
        string1 = stringBase + i;
        blackhole.consume(string1.hashCode());
    }

    @Benchmark
    public void testMemorySegmentHash(final Blackhole blackhole) {
        string1 = stringBase + i;
        blackhole.consume(segment1.hashCode());
    }

    @Benchmark
    public void testUnsafeByteHash(final Blackhole blackhole) {
        string1 = stringBase + i;
        blackhole.consume(directBufferByteString1.hashCode());
    }
}
