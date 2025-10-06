package slab;


import offHeapDataStructures.BytesToIntOffHeapMap;
import offHeapMutableAsciiString.UnsafeAsciiString;
import org.agrona.collections.Object2ObjectHashMap;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(3)
@Warmup(iterations = 2, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
public class DataStoreTests {

    private final ArrayDeque<ConcreteTestOrder> concreteTestOrdersPool = new ArrayDeque<>(2048);
    private final Object2ObjectHashMap<UnsafeAsciiString, ConcreteTestOrder> heapMap = new Object2ObjectHashMap<>(
            2048, 0.65f);

    private final ConcreteTestOrder concreteTestOrder = new ConcreteTestOrder();
    private final UnsafeAsciiString lookupKey = new UnsafeAsciiString(40);
    private Slab<ConcreteTestOrder> slab = null;
    private final BytesToIntOffHeapMap<UnsafeAsciiString> offHeapMap = new BytesToIntOffHeapMap<>(2048,
            () -> new UnsafeAsciiString(40));

    @Setup(Level.Iteration)
    public void setup() {
        lookupKey.set("ABC123");
        this.slab = new Slab<>((short) 256, 8, () -> this.concreteTestOrder);
        for (int i = 0; i < 2048; i++) {
            this.concreteTestOrdersPool.push(new ConcreteTestOrder());
        }
        System.gc();
    }


    @Benchmark
    public void testHeapBacked() {
        final var order = concreteTestOrdersPool.poll();
        order.getUnsafeAsciiString().set(lookupKey);
        heapMap.put(order.getUnsafeAsciiString(), order);

        final var resolvedOrder = heapMap.remove(lookupKey);
        concreteTestOrdersPool.push(resolvedOrder);
    }

    @Benchmark
    public void testOffHeapBacked() {
        final int index = slab.create(concreteTestOrder);
        concreteTestOrder.getUnsafeAsciiString().set(lookupKey);
        offHeapMap.put(concreteTestOrder.getUnsafeAsciiString(), index);
        final var resolvedIndex = offHeapMap.removeKey(lookupKey);
        slab.removeAt(resolvedIndex);
    }
}
