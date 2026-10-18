package kz.aitu.converter.facade;

import kz.aitu.converter.subsystem.Bitrate;
import kz.aitu.converter.subsystem.BitrateReader;
import kz.aitu.converter.subsystem.ContainerReader;
import kz.aitu.converter.subsystem.RawMedia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MediaInspectionFacade {

    private final ContainerReader containerReader;
    private final BitrateReader bitrateReader;

    public MediaInspectionFacade() {
        this(new ContainerReader(), new BitrateReader());
    }

    public MediaInspectionFacade(ContainerReader containerReader, BitrateReader bitrateReader) {
        this.containerReader = containerReader;
        this.bitrateReader = bitrateReader;
    }

    public MediaInfo inspect(String filename) throws ConversionException {
        Path source = Path.of(filename);
        if (!Files.isRegularFile(source)) {
            throw new ConversionException("File not found: " + filename);
        }
        try {
            RawMedia rawMedia = containerReader.read(source);
            Bitrate bitrate = bitrateReader.read(rawMedia);
            return new MediaInfo(source.getFileName().toString(), Files.size(source),
                    bitrate.videoKbps(), bitrate.audioKbps());
        } catch (IOException e) {
            throw new ConversionException("Inspection failed for " + filename + ": " + e.getMessage(), e);
        }
    }
}
