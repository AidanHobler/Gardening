package gardening;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;


 

public class SoundPlayer {
	
	public static Mixer mixer;
	public static Clip clip;
	
	public static void playSound(String name) {
		
		String realName = "/Sounds/" + name;
		
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);
		
		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
		try {
			clip = (Clip)mixer.getLine(dataInfo);	
		}
		catch(Exception e) {}
		
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource(realName));
			clip.open(audioStream);
			
			
		}
		catch(Exception e) {}
		
		clip.start();
	}
}
