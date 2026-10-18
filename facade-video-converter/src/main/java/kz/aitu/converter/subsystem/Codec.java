package kz.aitu.converter.subsystem;

public interface Codec {

    String name();

    byte[] encode(byte[] rawVideo);
}
