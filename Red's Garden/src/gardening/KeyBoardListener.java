package gardening;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class KeyBoardListener implements KeyListener, FocusListener{

	public boolean[] keys = new boolean[120];
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}
	
	public void keyReleased(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode < keys.length) {
			keys[keyCode] = false;
		}
	}
	
	public void focusLost(FocusEvent event) {
		for(int i = 0; i < keys.length; i++){
			keys[i] = false;
		}
	}
	
	//This one is useless
	public void keyTyped(KeyEvent event) {}
	
	public void focusGained(FocusEvent event) {}
	
	public boolean left() {
		return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	}
	
	public boolean right() {
		return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	}
	
	public boolean space() {
		return keys[KeyEvent.VK_SPACE];
	}
	
}
