import java.util.*;
import javax.sound.midi.*;

public class InstrumentTrack
{
   //PROPERTIES
   Sequence sequence;
   Track track;
   int channel; //channel played for instrument
   int program; //instrument type
   int volume; //multiplier for velocity out of 100
   ArrayList<Note> noteList; //note collection (not necessarily chronological)
   ArrayList<MidiEvent> nonNoteList;
   ArrayList<MidiEvent> eventList; //collection of events to be put into track
   
   
   String trackName;
   String instrumentName;
   
   
   
   //CONSTRUCTOR
   public InstrumentTrack(Sequence sequence)
   {
      this(sequence, 0, 23); //default is the Harmonica on channel 0
   }
   
   public InstrumentTrack(Sequence sequence, int channel, int program)
   {
      this(sequence, channel, program, 100, new ArrayList<Note>());
   }
   
   public InstrumentTrack(Sequence sequence, int channel, int program, int volume, ArrayList<Note> noteList)
   {
      this.sequence = sequence;
      track = this.sequence.createTrack();
      this.channel = channel;
      this.program = program;
      this.volume = volume;
      this.noteList = noteList;
      
   }
   
   //METHODS
   public boolean addNote(Note n)
   {
      return noteList.add(n);
   }
   
   public boolean removeNote(Note n)
   {
      return noteList.remove(n);
   }
   
   public void emptyTrack()
   {
      sequence.deleteTrack(track);
      track = sequence.createTrack();
   }
   
   public void composeTrack() //assumes track is empty
   { 
      Note n;
      ShortMessage shrtMsg; //to create MidiEvent
      
      emptyTrack();
         
      try {
         track.add( new MidiEvent( new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 100), 0) );
         
         shrtMsg = new ShortMessage();
         for (int j = 0; j < noteList.size(); j++)
         {
            n = noteList.get(j);
            
            //add to track MidiEvent: start
            shrtMsg.setMessage(ShortMessage.NOTE_ON, channel, n.getKey(), n.getVelocity() * volume / 100); 
            track.add( new MidiEvent( shrtMsg, n.getStartTick()) );
            
            //add to track MidiEvent: finish
            shrtMsg.setMessage(ShortMessage.NOTE_OFF, channel, n.getKey(), n.getVelocity() * volume / 100); 
            track.add( new MidiEvent( shrtMsg, n.getStartTick() + n.getDuration()) );
         }         
      } 
      catch (Exception e) {
         e.printStackTrace(); 
      } 
   }
   
   //GETTERS
   public Track getTrack()
   {
      return track;
   }
   
   public int getChannel()
   {
      return channel;
   }
   
   public int getProgram()
   {
      return program;
   }
   
   public int getVolume()
   {
      return volume;
   }
   
   public ArrayList<Note> getNoteList()
   {
      return noteList;
   }
   
   //SETTERS
   public void setTrack(Track t)
   {
      track = t;
   }
   
   public void setChannel(int c)
   {
      channel = c;
   }
   
    public void setProgram(int t)
   {
      program = t;
   }
   
    public void setVolume(int v)
    {
      volume = v; 
    }
    
    public void setNoteList(ArrayList<Note> nL)
    {
       noteList = nL;
    }
}