package codeChoire.midiCore;

import java.util.*;
import javax.sound.midi.*;

/**
 * Class to represent a single musical instrument in a song.
 * 
 * @author Celil Bugra Karacan (github.com/psykobug)
 * @version 0.1.0
 * @date 2019-05-10
 */
public class InsTrack
{
   // PROPERTIES
   final static double DEFAULT_VOLUME = 0.80;
   final static double DEFAULT_TEMPO = 4.4; // represents time signiture <beats per measure>.<beat duration>
   Track track;
   int channel; // channel played for instrument
   int program; // General MIDI instrument type - [ more info: www.midi.org/specifications/item/gm-level-1-sound-set ]
   double tempo; // double to represent time signiture
   double volume; // 0 <= volume <= 1 - multiplier for Notes' velocities
   int muteFactor;
   ArrayList<Note> noteList; // note collection (not necessarily chronological) to be composed into track.
   ArrayList<MidiEvent> eventList; // MidiEvent conversion from noteList
   ArrayList<MidiEvent> nonNoteList; // other MidiEvents
   String name; // name of the "Code_ChoireProject"
   String instrumentName;
   boolean isMuted;
   
   
   
   
   // CONSTRUCTOR
   
   public InsTrack(Track track, int channel, int program)
   {
      this(track, channel, program, DEFAULT_VOLUME, new ArrayList<Note>(), DEFAULT_TEMPO);
   }
   
   public InsTrack(Track track, int channel, int program, double volume, ArrayList<Note> noteList, double tempo)
   {
      this.track = track;
      this.channel = channel;
      this.program = program;
      this.volume = volume;
      this.noteList = noteList;
      this.tempo = tempo;
      muteFactor = 1;
   }
   
   // METHODS
   public boolean addNote(Note n)
   {
      return noteList.add(n);
   }
   
   public boolean removeNote(Note n)
   {
      return noteList.remove(n);
   }
   
   public boolean isMuted()
   {
      return muteFactor == 0;
   }
   
   public void mute( boolean state)
   {
      if (state == true)
      muteFactor = 0;
      else
         muteFactor = 1;
   }
   
   private void setTrackHeader()
   {
      MetaMessage mm;
      
      try
      {
         //set program (instrument type)
         track.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0), 0));
         
         //mm = new MetaMessage();
         //mm.setMessage(0x03, name.getBytes(), name.getBytes().length+1); //track name
         //track.add(new MidiEvent(mm, 0));
      }
      catch ( InvalidMidiDataException ex)
      {
         ex.printStackTrace();
      }
   }
   
   public Track composeTrack() // requires an empty track
   {
      Note note;
      ShortMessage onMsg; // to create MidiEvent
      ShortMessage offMsg;
      
      setTrackHeader();
      
      try
      {
         //shrtMsg = new ShortMessage();
         onMsg = new ShortMessage();
         offMsg = new ShortMessage();
         
         for (int j = 0; j < noteList.size(); j++)
         {
            note = noteList.get(j);
            
            // add to track MidiEvent: start
            // shrtMsg.setMessage(ShortMessage.NOTE_ON, channel, note.getKey(), (int)( note.getVelocity() * volume));
            onMsg = new ShortMessage(ShortMessage.NOTE_ON, channel, note.getKey(), (int)( note.getVelocity() * volume));
            //track.add(new MidiEvent(shrtMsg, note.getTick()));
            track.add(new MidiEvent(onMsg, note.getTick()));
            
            // add to track MidiEvent: finish
            // shrtMsg.setMessage(ShortMessage.NOTE_OFF, channel, note.getKey(), (int) ( note.getVelocity() * volume));
            offMsg.setMessage(ShortMessage.NOTE_OFF, channel, note.getKey(), (int)( note.getVelocity() * volume));
            //track.add(new MidiEvent(shrtMsg, note.getTick() + note.getDuration()));
            track.add(new MidiEvent(offMsg, note.getTick() + note.getDuration()));
         }
      } catch (Exception ex)
      {
         ex.printStackTrace();
      }
      
      return track;
   }
   
   //TODO
   public ArrayList<Note> extractNotes()
   {
      return null;
   }
   
   // GETTERS
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
      return (int) volume * 100;
   }
   
   public ArrayList<Note> getNoteList()
   {
      return noteList;
   }
   
   public double getTempo()
   {
      return tempo;
   }
   
   public String getName()
   {
      return name;
   }
   
   // SETTERS
   public void setTrack(Track t)
   {
      track = t;
   }
   
   public void setChannel(int c)
   {
      if (0 <= c && c < 16)
         channel = c;
   }
   
   public void setProgram(int t)
   {
      if (0 <= t && t < 128)
         program = t;
   }
   
   public void setVolume(int v)
   {
      if (0 <= v && v <= 100)
         volume = (double) v / 100;
   }
   
   public void setTempo(int t)
   {
      if ( false)
         tempo = 4.4;
   }
   
   public void setNoteList(ArrayList<Note> nL)
   {
      noteList = nL;
   }
   
   public void setName(String n)
   {
      name = n;
   }
}