/*import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Music
{
    AudioClip clip = null;

    public AudioClip getAudioClip()
    {
        return this.clip;
    }

    public void setAudioClip(AudioClip clip)
    {
        this.clip = clip;
    }

    public void play()
    {
        if(getAudioClip() != null)
        {
            getAudioClip().play();
        }
    }

    public void loop()
    {
        if(getAudioClip() != null)
        {
            getAudioClip().loop();
        }
    }

    public void stop()
    {
        if(getAudioClip() != null)
        {
            getAudioClip().stop();
        }
    }

    public static void main(String[] args)
    {
        Music mac = new Music();
        try
        {
            mac.setAudioClip(Applet.newAudioClip((new File("D:\\language\\java\\final\\baby-shark.wav")).toURL()));
        }
        catch(MalformedURLException e)
        {
            System.out.println("error");
        }
        mac.loop();
    }
}*/
/*import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
public class Music
{
    static void playMusic(String musicName) {// �I����?���� 
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(musicName));    //??��?
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value�i�H��??�m���q�A?0-2.0
            double value = 1;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
public class Music
{
    static SourceDataLine sdl;
    static void playMusic(String musicName) {// �I����?���� 
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(musicName));    //??��?
            AudioFormat aif = ais.getFormat();
            
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value�i�H��??�m���q�A?0-2.0
            double value = 1;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void stopMusic()
    {
        sdl.stop();
        //sdl = null;
    }
}