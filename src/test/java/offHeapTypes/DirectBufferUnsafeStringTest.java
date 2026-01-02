package offHeapTypes;

import org.agrona.collections.Object2LongHashMap;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectBufferUnsafeStringTest {

    @Test
    public void testUnsafeEquals() throws InputMismatchException {
        assertEquals(new DirectBufferUnsafeString(40), new DirectBufferUnsafeString(40));
    }

    @Test
    public void mapTest() {
        final Object2LongHashMap<DirectBufferUnsafeString> map = new Object2LongHashMap<>(1000000, 0.8f, -1);

        for (int i = 0; i < 500000; i++) {
            final DirectBufferUnsafeString directBufferUnsafeString = new DirectBufferUnsafeString(40);
            directBufferUnsafeString.set(String.valueOf(i));
            map.put(directBufferUnsafeString, i);
        }

        final DirectBufferUnsafeString reusable = new DirectBufferUnsafeString(40);

        for (int i = 0; i < 500000; i++) {
            reusable.set(String.valueOf(i));
            final long index = map.getValue(reusable);
            assertEquals(i, index);
        }
    }


}
