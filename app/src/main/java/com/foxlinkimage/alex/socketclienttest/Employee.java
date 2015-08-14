package com.foxlinkimage.alex.socketclienttest;


import java.nio.ByteOrder;

import javolution.io.Struct;

public class Employee extends Struct{
    public final UTF8String name   = new UTF8String(64);
    public final Unsigned16 year = new Unsigned16();
    public final Unsigned16 day = new Unsigned16();

    @Override
    public ByteOrder byteOrder() {
        return ByteOrder.LITTLE_ENDIAN;
    }
}
