package jmjumper.sortalgoviewer.Sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SinSynth {

    // bin mir nicht sicher, ob SinSynth oder PlaySound besser ist. Vermutlich sind die sowieso gleich.
    // TODO: Ton sollte relativ zur Arraygröße sein.

    public void playSound(int value) {
        makeSound mSound = new makeSound(value);
        mSound.start();
    }

    class makeSound extends Thread {
        protected static final int SAMPLE_RATE = 16 * 1024;
        int pitch;

        makeSound(int pitch) {
            this.pitch = pitch;
        }

        public static byte[] createSinWaveBuffer(double freq, int ms) {
            int samples = (ms * SAMPLE_RATE) / 1000;
            byte[] output = new byte[samples];
            double period = (double) SAMPLE_RATE / freq;
            for (int i = 0; i < output.length; i++) {
                double angle = 2.0 * Math.PI * i / period; // 2.0 * Math.PI * i / period --> SINUSWELLE
                output[i] = (byte) (Math.sin(angle) * 127f);
            }

            return output;
        }

        public void run() {
            try {
                final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8, 1, true, true);
                SourceDataLine line = AudioSystem.getSourceDataLine(af);
                line.open(af, SAMPLE_RATE);
                line.start();

                int frequency = (int) (Math.pow(pitch, 1.5f)) + 800; // pitch hoch x (hier 1.5f), damit sich die Töne mehr unterscheiden.
                // Je geringer der Exponent, desto tiefer der Ton.

                byte[] toneBuffer = createSinWaveBuffer(frequency, 10);
                line.write(toneBuffer, 0, toneBuffer.length);

                line.drain();
                line.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }


}
