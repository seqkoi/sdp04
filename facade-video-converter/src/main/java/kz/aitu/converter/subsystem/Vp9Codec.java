package kz.aitu.converter.subsystem;

public class Vp9Codec extends DeflateCodec {

    private static final int BALANCED_LEVEL = 6;

    public Vp9Codec() {
        super("VP9", BALANCED_LEVEL);
    }
}
