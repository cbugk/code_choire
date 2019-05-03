// Java program showing how to change the instrument type 
import javax.sound.midi.*;
import java.io.*;
import java.util.*;

public class Test001 { 
   
   public static void main(String[] args) 
   { 
      try
      {
         Sequencer sequencer = MidiSystem.getSequencer(); 
         sequencer.open();
         
         Sequence seq = new Sequence(Sequence.PPQ, 4);
         
         MidiFileHandler mfh = new MidiFileHandler(seq, new File("ask.mid"));
         
         ArrayList<InstrumentTrack> arrInsTrack = mfh.extractInstrumentTracks();
         arrInsTrack.get(5).composeTrack();  
         
         sequencer.setSequence(seq);
         
         sequencer.start();
         
         while (true)
         {
            System.out.println("/s");
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
