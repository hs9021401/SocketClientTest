package FCLStruct;

import java.nio.ByteOrder;

import javolution.io.Struct;

/**
 * Created by Alex on 2015/8/17.
 */
public class BlockImageStatus extends Struct {

    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
}
