package kz.aitu.converter.subsystem;

import java.util.zip.Deflater;

public class H264Codec extends DeflateCodec {

    public H264Codec() {
        super("H.264", Deflater.BEST_COMPRESSION);
    }
}
