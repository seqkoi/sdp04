package kz.aitu.converter.subsystem;

public class UnsupportedFormatException extends MediaException {

    public UnsupportedFormatException(String requestedFormat) {
        super("Unsupported media format: " + requestedFormat);
    }
}
