package kz.aitu.converter.subsystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ContainerReader {

    private static final int VIDEO_SHARE_PERCENT = 75;

    public RawMedia read(Path source) throws IOException {
        byte[] data = Files.readAllBytes(source);
        int splitIndex = (int) ((long) data.length * VIDEO_SHARE_PERCENT / 100);
        byte[] video = Arrays.copyOfRange(data, 0, splitIndex);
        byte[] audio = Arrays.copyOfRange(data, splitIndex, data.length);
        return new RawMedia(video, audio);
    }
}
