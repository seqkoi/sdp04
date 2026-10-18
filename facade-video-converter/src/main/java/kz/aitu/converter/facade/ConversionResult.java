package kz.aitu.converter.facade;

public record ConversionResult(String outputFilename, long originalSizeBytes, long convertedSizeBytes) {

    public double compressionRatio() {
        return (double) convertedSizeBytes / originalSizeBytes;
    }
}
