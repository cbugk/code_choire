import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

public class Note {
	int channel, key, velocity, tickInit, tickEnd;
	public Note(int channel, int key, int velocity, int tickInit, int tickEnd) {
		this.channel = channel;
		this.key = key;
		this.velocity = velocity;
		this.tickInit = tickInit;
		this.tickEnd = tickEnd;
	}
	public MidiEvent makeEvent(String command) 
		{ 
		
		MidiEvent event = null; 
		
		try { 
		if (command == "on")
		{
			ShortMessage a = new ShortMessage(); 
			a.setMessage(144, channel, key, velocity); 
			
			event = new MidiEvent(a, tickInit);
		}
		else if (command == "off")
		{
			ShortMessage a = new ShortMessage(); 
			a.setMessage(128, channel, key, velocity); 
			
			event = new MidiEvent(a, tickEnd);
		}
			
		} 
			catch (Exception ex) { 
		
		ex.printStackTrace(); 
		} 
			return event; 
		} 
}
