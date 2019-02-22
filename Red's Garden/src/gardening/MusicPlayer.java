package gardening;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;

public class MusicPlayer {
	
	//Play reverb when below, no reverb when above
	
	private Mixer mixer;
	private Clip clip;
	private long length;
	private String realName;

	
	public void playMusic(String name, long microTime) {
		
		realName = "/Sounds/" + name + ".wav";
		
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip)mixer.getLine(dataInfo);	
		}
		catch(Exception e) {}
		
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(MusicPlayer.class.getResource(realName));
			clip.open(audioStream);

			
		}
		catch(Exception e) {}
		
		clip.setMicrosecondPosition(microTime);
		clip.start();
		clip.loop(clip.LOOP_CONTINUOUSLY);

	}
	
	public long getMicroTime() {
		return clip.getMicrosecondPosition();
	}
	
	public long getMicroLength() {
		return clip.getMicrosecondLength();
	}
	
	public void stopPlaying() {
		clip.stop();
	}

}
