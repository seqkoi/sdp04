package kz.aitu.converter.subsystem;

import java.util.zip.Deflater;

public class XvidCodec extends DeflateCodec {

    public XvidCodec() {
        super("Xvid", Deflater.BEST_SPEED);
    }
}
