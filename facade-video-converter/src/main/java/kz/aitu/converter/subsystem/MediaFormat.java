package kz.aitu.converter.subsystem;

import java.util.Locale;

public enum MediaFormat {
    MP4("mp4"),
    WEBM("webm"),
    MKV("mkv"),
    AVI("avi");

    private final String extension;

    MediaFormat(String extension) {
        this.extension = extension;
    }

    public String extension() {
        return extension;
    }

    public static MediaFormat fromExtension(String value) throws UnsupportedFormatException {
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        for (MediaFormat format : values()) {
            if (format.extension.equals(normalized)) {
                return format;
            }
        }
        throw new UnsupportedFormatException(value);
    }
}
