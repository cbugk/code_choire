import java.util.*;
import javax.sound.midi.*;

public class Instrument
{
   //PROPERTIES
   int channel;
   int type;   
   int volume;
   ArrayList<Note> noteList;
   Track track;
   Sequence sequence;
   
   //CONSTRUCTOR
   public Instrument(Sequence sequence)
   {
      this(0, 23, sequence); //default is the Harmonica on channel 0
   }
   
   public Instrument(int channel, int type, Sequence sequence)
   {
      this(channel, type, sequence, 100, new ArrayList<Note>());
   }
   
   public Instrument(int channel, int type, Sequence sequence, int volume, ArrayList<Note> noteList)
   {
      this.channel = channel;
      this.type = type;
      this.volume = volume;
      this.noteList = noteList;
      this.sequence = sequence;
      track = this.sequence.createTrack();
   }
   
   //METHODS
   public void addNote(Note n)
   {
      noteList.add(n);
   }
   
   public void removeNote(Note n)
   {
      noteList.remove(n);
   }
   
   public void composeTrack() 
   { 
      Note n;
      ShortMessage shrtMsg; 
      
      try {
         sequence.deleteTrack(track);
         track = sequence.createTrack();
         
         track.add( new MidiEvent( new ShortMessage(192, channel, type, 0), 1) );
         
         for (int j = 0; j < noteList.size(); j++)
         {
            n = noteList.get(j);
            shrtMsg = new ShortMessage();
            
            //add to track MidiEvent: start
            shrtMsg.setMessage(144, channel, n.getKey(), n.getVelocity()); 
            track.add( new MidiEvent( shrtMsg, n.getStartTime()) );
            
            //add to track MidiEvent: finish
            shrtMsg.setMessage(128, channel, n.getKey(), n.getVelocity()); 
            track.add( new MidiEvent( shrtMsg, n.getFinishTime()) );
         }         
      } 
      catch (Exception e) {
         e.printStackTrace(); 
      } 
   }
   
   //GETTERS
   public Sequence getSequence()
   {
      return sequence;
   }
   
   public int getChannel()
   {
      return channel;
   }
   
   public int getType()
   {
      return type;
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
   public void setSequence(Sequence seq)
   {
      sequence =  seq;
   }
   
   public void setChannel(int c)
   {
      channel = c;
   }
   
    public void setType(int t)
   {
      type = t;
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