import java.util.*;
import java.io.*;
import javax.sound.midi.*;

public class MidiFileHandler
{
   //PROPERTIES
   Sequence fileSequence;
   Sequence sequence;
   
   //CONSTRUCTORS
   public MidiFileHandler(Sequence sequence, File file) throws InvalidMidiDataException, IOException
   {
      fileSequence = MidiSystem.getSequence(file);
      this.sequence = sequence;
   }
   
   //METHODS
   public ArrayList<InstrumentTrack> extractInstrumentTracks()
   {
      Track track;
      InstrumentTrack insTrack;
      ArrayList<InstrumentTrack> arrInsTrack;
      MidiEvent event;
      MidiMessage message;
      ShortMessage sm;
      MetaMessage mm;
      int command;
      Note n;
      
      arrInsTrack = new ArrayList<InstrumentTrack>();
      
      for (int trackNum = 0; trackNum < fileSequence.getTracks().length; trackNum++) 
      {
         track = fileSequence.getTracks()[trackNum];
         insTrack = new InstrumentTrack(sequence);
         
         for (int i=0; i < track.size(); i++) 
         { 
            event = track.get(i);
            message = event.getMessage();
            
            if (message instanceof ShortMessage) 
            {
               sm = (ShortMessage) message;
               command = sm.getCommand();
               
               if (command == 144)
               {
                  n = new Note(sm.getData1(), (int) event.getTick(), -1, sm.getData2());
                  
                  int j = i + 1;
                  while (j < track.size() && n.getDuration() < 0)
                  {
                     event = track.get(j);
                     message = event.getMessage();
                     if (message instanceof ShortMessage && ( (ShortMessage) message).getCommand() == 128 && ( (ShortMessage) message).getData1() == n.getKey())
                     {
                        n.setDuration((int) event.getTick() - n.getStartTick());
                     }
                     j++;
                  }
                  insTrack.addNote(n);
               }
               else if (command == 176)
               {
                  
               }
               else if (command == 192)
               {
                  insTrack.setChannel(sm.getChannel());
               }
                              
            }
            else if (message instanceof MetaMessage)
            {
               //mm = (MetaMessage) message;
               
            }
            else
            {
               System.out.print("@" + event.getTick() + " ");
               System.out.println("Other message: " + message.getClass());
            }
            
         }
         arrInsTrack.add(insTrack);
      }
      
      return arrInsTrack;
   }
}