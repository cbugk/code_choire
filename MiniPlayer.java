// Java program showing how to change the instrument type 
import javax.sound.midi.*; 
import java.util.*; 

public class MiniPlayer { 
   
   public static void main(String[] args) 
   { 
      
      MiniPlayer player = new MiniPlayer(); 
      Note note; 
      
      player.setUpPlayer(100, 60);
   } 
   
   public void setUpPlayer(int instrument, int key) 
   { 
      ArrayList<Note> pit = new ArrayList<>(); // ArrayList of Notes
      
      // Add some Notes to pit
      pit.add(new Note(1, 60, 100, 0, 5));
      pit.add(new Note(1, 48, 100, 0, 20));
      pit.add(new Note(1, 55, 100, 0, 20));
      pit.add(new Note(1, 64, 100, 0, 5));
      pit.add(new Note(1, 64, 100, 5, 10));
      pit.add(new Note(1, 65, 100, 10, 15));
      pit.add(new Note(1, 67, 100, 15, 20));
      pit.add(new Note(1, 67, 100, 20, 25));
      pit.add(new Note(1, 55, 100, 20, 30));
      pit.add(new Note(1, 65, 100, 25, 30));
      pit.add(new Note(1, 64, 100, 30, 35));
      pit.add(new Note(1, 55, 100, 30, 40));
      pit.add(new Note(1, 62, 100, 35, 40));
      
      try { 
         
         Sequencer sequencer = MidiSystem.getSequencer(); 
         sequencer.open(); 
         Sequence sequence = new Sequence(Sequence.PPQ, 4); 
         Track track = sequence.createTrack(); 
         
         // Add Notes which are stored in the pit to the track
         for(int i = 0; i< pit.size();i++ ) {
	         track.add(pit.get(i).makeEvent("on"));
	         track.add(pit.get(i).makeEvent("off"));
         }
         
         // Set the instrument type 
       // track.add(makeEvent(192, 2, instrument, 0, 0)); 
       // track.add(makeEvent(192, 1, 1, 0, 0)); 
         
         sequencer.setSequence(sequence); 
         sequencer.start(); 
         
         while (true) { 
            
            if (!sequencer.isRunning()) { 
               sequencer.close(); 
               System.exit(1); 
            } 
         } 
      } 
      catch (Exception ex) { 
         
         ex.printStackTrace(); 
      } 
   } 
} 
