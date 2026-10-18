package kz.aitu.converter.facade;

import kz.aitu.converter.subsystem.AudioMixer;
import kz.aitu.converter.subsystem.Bitrate;
import kz.aitu.converter.subsystem.BitrateReader;
import kz.aitu.converter.subsystem.Codec;
import kz.aitu.converter.subsystem.CodecFactory;
import kz.aitu.converter.subsystem.ContainerReader;
import kz.aitu.converter.subsystem.ContainerWriter;
import kz.aitu.converter.subsystem.EncodedMedia;
import kz.aitu.converter.subsystem.MediaException;
import kz.aitu.converter.subsystem.MediaFormat;
import kz.aitu.converter.subsystem.RawMedia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VideoConversionFacade {

    private static final int TARGET_AUDIO_PEAK = 100;
    private static final String OUTPUT_SUFFIX = "_converted";

    private final ContainerReader containerReader;
    private final BitrateReader bitrateReader;
    private final CodecFactory codecFactory;
    private final AudioMixer audioMixer;
    private final ContainerWriter containerWriter;

    public VideoConversionFacade() {
        this(new ContainerReader(), new BitrateReader(), new CodecFactory(),
                new AudioMixer(), new ContainerWriter());
    }

    public VideoConversionFacade(ContainerReader containerReader,
                                 BitrateReader bitrateReader,
                                 CodecFactory codecFactory,
                                 AudioMixer audioMixer,
                                 ContainerWriter containerWriter) {
        this.containerReader = containerReader;
        this.bitrateReader = bitrateReader;
        this.codecFactory = codecFactory;
        this.audioMixer = audioMixer;
        this.containerWriter = containerWriter;
    }

    public ConversionResult convert(String filename, String format) throws ConversionException {
        Path source = Path.of(filename);
        if (!Files.isRegularFile(source)) {
            throw new ConversionException("File not found: " + filename);
        }
        try {
            MediaFormat target = MediaFormat.fromExtension(format);
            RawMedia rawMedia = containerReader.read(source);
            Bitrate bitrate = bitrateReader.read(rawMedia);
            Codec codec = codecFactory.forFormat(target);
            byte[] encodedVideo = codec.encode(rawMedia.video());
            byte[] mixedAudio = audioMixer.normalize(rawMedia.audio(), TARGET_AUDIO_PEAK);
            Path output = resolveOutputPath(source, target);
            containerWriter.write(output, target,
                    new EncodedMedia(codec.name(), bitrate, encodedVideo, mixedAudio));
            return new ConversionResult(output.toString(), Files.size(source), Files.size(output));
        } catch (MediaException | IOException e) {
            throw new ConversionException("Conversion failed for " + filename + ": " + e.getMessage(), e);
        }
    }

    private Path resolveOutputPath(Path source, MediaFormat target) {
        String name = source.getFileName().toString();
        int dotIndex = name.lastIndexOf('.');
        String baseName = dotIndex > 0 ? name.substring(0, dotIndex) : name;
        Path directory = source.toAbsolutePath().getParent();
        return directory.resolve(baseName + OUTPUT_SUFFIX + "." + target.extension());
    }
}
