package offHeapDataStructures;

import org.junit.jupiter.api.Test;
import slab.ConcreteTestCodec;
import slab.TestCodec;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BytesToIntOffHeapMapTests {

    @Test
    public void testInsertions() {
        final BytesToIntOffHeapMap<TestCodec> testCodecMap
                = new BytesToIntOffHeapMap<>(TestCodec::new);

        final TestCodec testCodec = new ConcreteTestCodec();
        testCodec.setId(4);
        testCodecMap.put(testCodec, 7);
        assertEquals(1, testCodecMap.size());
        final TestCodec testCodec2 = new ConcreteTestCodec();
        testCodec2.setId(4);
        final int testValue = testCodecMap.getInt(testCodec2);
        assertEquals(7, testValue);
    }
}
