package kz.aitu.converter.client;

import kz.aitu.converter.facade.ConversionException;
import kz.aitu.converter.facade.ConversionResult;
import kz.aitu.converter.facade.MediaInspectionFacade;
import kz.aitu.converter.facade.VideoConversionFacade;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public final class Main {

    private static final int SAMPLE_SIZE_BYTES = 400_000;

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        Path sample = SampleFileGenerator.create(Path.of("samples"), "holiday.avi", SAMPLE_SIZE_BYTES);
        String filename = sample.toString();

        VideoConversionFacade converter = new VideoConversionFacade();
        MediaInspectionFacade inspector = new MediaInspectionFacade();

        printInspection(inspector, filename);
        for (String format : List.of("mp4", "webm", "mkv")) {
            printConversion(converter, filename, format);
        }
        printConversion(converter, filename, "flv");
        printConversion(converter, "samples/missing.avi", "mp4");
    }

    private static void printInspection(MediaInspectionFacade inspector, String filename) {
        try {
            System.out.println("Inspect  -> " + inspector.inspect(filename));
        } catch (ConversionException e) {
            System.out.println("Inspect  -> ERROR: " + e.getMessage());
        }
    }

    private static void printConversion(VideoConversionFacade converter, String filename, String format) {
        try {
            ConversionResult result = converter.convert(filename, format);
            System.out.printf("Convert  -> %s (%d -> %d bytes, ratio %.2f)%n",
                    result.outputFilename(), result.originalSizeBytes(),
                    result.convertedSizeBytes(), result.compressionRatio());
        } catch (ConversionException e) {
            System.out.println("Convert  -> ERROR: " + e.getMessage());
        }
    }
}
