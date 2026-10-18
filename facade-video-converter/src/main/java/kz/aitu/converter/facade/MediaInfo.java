package kz.aitu.converter.facade;

public record MediaInfo(String filename, long sizeBytes, int videoKbps, int audioKbps) {

    @Override
    public String toString() {
        return String.format("%s: %d bytes, video %d kbps, audio %d kbps",
                filename, sizeBytes, videoKbps, audioKbps);
    }
}
