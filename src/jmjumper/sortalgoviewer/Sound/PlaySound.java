package jmjumper.sortalgoviewer.Sound;

import javax.sound.sampled.*;

public class PlaySound extends Thread {

    public void playSound(int value) {

        int pitch = value + 600;

        makeSound mSound = new makeSound(pitch);
        mSound.start();

    }

    class makeSound extends Thread {
        int pitch;
        int length = 2000; // je höher, desto kürzer..

        makeSound(int pitch) {
            this.pitch = pitch;
        }

        public void run() {
            try {
                byte[] buf = new byte[1];
                float frequency = 44000f;
                AudioFormat af = new AudioFormat(frequency, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open();
                sdl.start();
                for (int i = 0; i < 50 * frequency / length; i++) {
                    double angle = i / (frequency / pitch) * 2.0 * Math.PI;
                    buf[0] = (byte) (Math.sin(angle) * 100);
                    sdl.write(buf, 0, 1);
                }
                sdl.drain();
                sdl.stop();
            } catch (LineUnavailableException exception) {
                exception.printStackTrace();
            }
        }
    }

}
