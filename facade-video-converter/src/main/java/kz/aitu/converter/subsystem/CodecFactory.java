package kz.aitu.converter.subsystem;

public class CodecFactory {

    public Codec forFormat(MediaFormat format) {
        return switch (format) {
            case MP4, MKV -> new H264Codec();
            case WEBM -> new Vp9Codec();
            case AVI -> new XvidCodec();
        };
    }
}
