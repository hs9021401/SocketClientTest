package FCLStruct;

import java.nio.ByteOrder;

import javolution.io.Struct;

/**
 * Created by Alex on 2015/8/17.
 */
public class VendorCommand extends Struct {
    Unsigned32 VendorID;
    Unsigned8 OperationCode;
    Unsigned8 Res0;
    Unsigned8 ControlCode;
    Unsigned8 Res1;
    Unsigned16 DataType;
    Unsigned8 Res2;
    Unsigned32 DataLength;

    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
}
