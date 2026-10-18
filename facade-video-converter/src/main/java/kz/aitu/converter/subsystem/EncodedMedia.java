package kz.aitu.converter.subsystem;

public record EncodedMedia(String codecName, Bitrate bitrate, byte[] video, byte[] audio) {
}
