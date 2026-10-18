package kz.aitu.converter.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

final class SampleFileGenerator {

    private static final long SEED = 42L;
    private static final int SAMPLE_RANGE = 64;

    private SampleFileGenerator() {
    }

    static Path create(Path directory, String filename, int sizeBytes) throws IOException {
        Files.createDirectories(directory);
        byte[] data = new byte[sizeBytes];
        Random random = new Random(SEED);
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (random.nextInt(SAMPLE_RANGE) - SAMPLE_RANGE / 2);
        }
        Path file = directory.resolve(filename);
        Files.write(file, data);
        return file;
    }
}
