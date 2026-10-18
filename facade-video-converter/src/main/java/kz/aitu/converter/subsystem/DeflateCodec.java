package kz.aitu.converter.subsystem;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

public abstract class DeflateCodec implements Codec {

    private static final int BUFFER_SIZE = 4096;

    private final String name;
    private final int compressionLevel;

    protected DeflateCodec(String name, int compressionLevel) {
        this.name = name;
        this.compressionLevel = compressionLevel;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public byte[] encode(byte[] rawVideo) {
        Deflater deflater = new Deflater(compressionLevel);
        try {
            deflater.setInput(rawVideo);
            deflater.finish();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            while (!deflater.finished()) {
                int written = deflater.deflate(buffer);
                output.write(buffer, 0, written);
            }
            return output.toByteArray();
        } finally {
            deflater.end();
        }
    }
}
