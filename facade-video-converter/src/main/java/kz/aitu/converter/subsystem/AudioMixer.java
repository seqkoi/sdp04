package kz.aitu.converter.subsystem;

public class AudioMixer {

    private static final int MAX_SAMPLE_VALUE = 127;

    public byte[] normalize(byte[] audio, int targetPeak) {
        if (targetPeak < 1 || targetPeak > MAX_SAMPLE_VALUE) {
            throw new IllegalArgumentException("Target peak must be between 1 and " + MAX_SAMPLE_VALUE);
        }
        int currentPeak = findPeak(audio);
        if (currentPeak == 0) {
            return audio.clone();
        }
        double gain = (double) targetPeak / currentPeak;
        byte[] normalized = new byte[audio.length];
        for (int i = 0; i < audio.length; i++) {
            normalized[i] = clamp(Math.round(audio[i] * gain));
        }
        return normalized;
    }

    private int findPeak(byte[] audio) {
        int peak = 0;
        for (byte sample : audio) {
            peak = Math.max(peak, Math.abs((int) sample));
        }
        return peak;
    }

    private byte clamp(long value) {
        return (byte) Math.max(-MAX_SAMPLE_VALUE, Math.min(MAX_SAMPLE_VALUE, value));
    }
}
