// Java program showing how to change the instrument type 
import javax.sound.midi.*; 

public class Test001 { 
   
   public static void main(String[] args) 
   { 
      try
      {
         Sequencer sequencer = MidiSystem.getSequencer(); 
         sequencer.open();
         
         Sequence seq = new Sequence(Sequence.PPQ, 4);
         Instrument instr = new Instrument(seq);
                  
         instr.addNote(new Note(48, 0, 2));
         instr.addNote(new Note(55, 0, 2));
         instr.addNote(new Note(64, 0, 5));
         instr.addNote(new Note(64, 5, 10));
         instr.addNote(new Note(62, 0, 4));
         
         instr.composeTrack();   
         
         sequencer.setSequence(seq); 
         sequencer.start();
         
         while (true)
         {
            if (!sequencer.isRunning())
            { 
               sequencer.close(); 
               break; 
            } 
         } 
         
      }
      catch (Exception e)
      { 
         System.out.println(e.getMessage());
         e.printStackTrace(); 
      }   
   }   
} 
