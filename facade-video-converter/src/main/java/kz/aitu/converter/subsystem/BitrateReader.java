package kz.aitu.converter.subsystem;

public class BitrateReader {

    private static final int ASSUMED_DURATION_SECONDS = 10;

    public Bitrate read(RawMedia media) {
        return new Bitrate(toKbps(media.video().length), toKbps(media.audio().length));
    }

    private int toKbps(int byteCount) {
        long kilobits = byteCount * 8L / 1000;
        return (int) Math.max(1, kilobits / ASSUMED_DURATION_SECONDS);
    }
}
