package kz.aitu.converter.subsystem;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContainerWriter {

    private static final String SIGNATURE_PREFIX = "MEDIA/";

    public void write(Path target, MediaFormat format, EncodedMedia media) throws IOException {
        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(target))) {
            output.writeUTF(SIGNATURE_PREFIX + format.name());
            output.writeUTF(media.codecName());
            output.writeInt(media.bitrate().videoKbps());
            output.writeInt(media.bitrate().audioKbps());
            output.writeInt(media.video().length);
            output.write(media.video());
            output.writeInt(media.audio().length);
            output.write(media.audio());
        }
    }
}
