package codeChoire.midiCore;

import java.io.*;
import java.util.*;
import javax.sound.midi.*;

/**
 * Class to interact with javax.sound.midi API.
 * 
 * @author Celil Bugra Karacan (github.com/psykobug)
 * @version 0.1.0
 * @date 2019-05-07
 */
public class MidiConductor
{
   //
   // STATIC METHODS
   //
   
   
   
   public static int getTrackInfo(Track track)
   {
      ArrayList<MidiEvent> eventList;
      String msgInfo; // MidiMessages (information output) of the sequence
      MidiEvent event;
      MidiMessage msg;
      ShortMessage sm;
      MetaMessage mm;
      byte[] data; // for tempo calc
      int mpq; // for tempo calc
      
      msgInfo = "";
      
      // events of track
      for (int i = 0; i < track.size(); i++)
      {
         event = track.get(i);
         msg = event.getMessage();
      } // end of events loop
      
      msgInfo = msgInfo + "\n";
      
      return 0;
      
   } // end of method getrackInfo( Track)
   
   /**
    * Gives an orchestra of 128 General MIDI 1 instruments
    * @return Instrument[] Instruments in (from bank 0)
    */
   public static Instrument[] getOrchestra()
   {
      final int DEFAULT_BANK = 0; // General MIDI 1 compatible
      Synthesizer synth;
      Soundbank soundBank;
      int instrNum;
      Instrument[] instruments;
      Instrument[] generalMidiInstruments;
      
      generalMidiInstruments = null;
      
      // getting available instruments
      try {
         synth = MidiSystem.getSynthesizer();
         synth.open();
         soundBank = synth.getDefaultSoundbank();
         instruments = soundBank.getInstruments();
         
         instrNum = 0;
         generalMidiInstruments = new Instrument[128];
         while ( instrNum < 128 && instrNum < instruments.length)
         {
            generalMidiInstruments[instrNum] = soundBank.getInstrument( new Patch( DEFAULT_BANK, instrNum));
            
            instrNum++;
         }
      }
      catch (Exception e) 
      {
         e.printStackTrace();
      }
      
      return generalMidiInstruments;
   } // end of getOrchestra()
   
   public static void start(Sequencer sequencer)
   {
      try
      {
         if (!sequencer.isOpen())
         {
            sequencer.open();
         }
         sequencer.start();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
   
   public static void stop(Sequencer sequencer)
   {
      try
      {
         if (sequencer.isOpen())
         {
            sequencer.stop();
         }
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
   
   public static void write(Sequence sequence, String fileOut)
   {
      try
      {
         MidiSystem.write(sequence, 1, new File(fileOut)); // type 1 MIDI File
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
      }
   }
   
   public static String eventsToString(Sequence sequence)
   {
      String seqMsgInfo; //// MidiMessages (information output) of the sequence
      int trackNum;
      MidiEvent event;
      MidiMessage msg;
      ShortMessage sm;
      MetaMessage mm;
      byte[] data; // for tempo calc
      int mpq; // for tempo calc
      
      // initiated
      seqMsgInfo = "\n";
      trackNum = 0;
      
      for (Track track : sequence.getTracks()) // for all tracks of sequence
      {
         // track info
         seqMsgInfo = seqMsgInfo + "[ Track: " + trackNum + " Size: " + track.size() + " tick: " + track.ticks()
            + " ]\n";
         
         // events per track
         for (int i = 0; i < track.size(); i++)
         {
            event = track.get(i);
            msg = event.getMessage();
            
            if (msg instanceof ShortMessage)
            {
               sm = (ShortMessage) msg;
               
               if (sm.getCommand() == 144) // "Note ON"
               {
                  seqMsgInfo = seqMsgInfo + "Note_ON: Velo=" + String.format( "%03d", sm.getData2()) + " Key=" + String.format( "%03d", sm.getData1())
                     + " Channel: " + String.format( "%02d", sm.getChannel()) + " Command=" + sm.getCommand() + " Tick="
                     + event.getTick() + "\n";
               } else if (sm.getCommand() == 128) // "Note OFF"
               {
                  seqMsgInfo = seqMsgInfo + "NoteOFF: Velo=" + String.format( "%03d", sm.getData2()) + " Key=" + String.format( "%03d", sm.getData1())
                     + " Channel: " + String.format( "%02d", sm.getChannel()) + " Command=" + sm.getCommand() + " Tick="
                     + event.getTick() + "\n";
               } else
               {
                  seqMsgInfo = seqMsgInfo + "ShrtMSG: Velo=" + String.format( "%03d", sm.getData2()) + " Key=" + String.format( "%03d", sm.getData1())
                     + " Channel: " + String.format( "%02d", sm.getChannel()) + " Command=" + sm.getCommand() + " Tick="
                     + event.getTick() + "\n";
               }
            } // end of ShortMessage
            
            else if (msg instanceof MetaMessage)
            {
               mm = (MetaMessage) msg;
               
               seqMsgInfo = seqMsgInfo + "MetaMSG: Type=" + String.format( "%03d", mm.getType()) + " Tick="
                  + event.getTick();
               
               if // Text messages
                  (mm.getType() == 1 || mm.getType() == 2 || mm.getType() == 3 || mm.getType() == 4
                      || mm.getType() == 5 || mm.getType() == 6 || mm.getType() == 7 || mm.getType() == 9)
               {
                  seqMsgInfo = seqMsgInfo + " Data: ";
                  
                  for (int j = 0; j < mm.getData().length; j++)
                  {
                     if (0 < mm.getData()[j] && mm.getData()[j] < 255) // if ASCII compatible
                        seqMsgInfo = seqMsgInfo + Character.toString((char) mm.getData()[j]); // convert from
                     // ASCII
                     else
                        seqMsgInfo = seqMsgInfo + String.format( "%02d", mm.getData()[j]);
                  }
               } else if (mm.getType() == 47)
               {
                  seqMsgInfo = seqMsgInfo + " End of Track";
               } else if (mm.getType() == 81)
               {
                  // calculate tempo
                  data = mm.getData();
                  mpq = ((data[0] & 0x7f) << 14) | ((data[1] & 0x7f) << 7) | (data[2] & 0x7f);
                  
                  seqMsgInfo = seqMsgInfo + " Set Tempo=" + mpq;
               }
               
               seqMsgInfo = seqMsgInfo + "\n";
            } // end of MetaMessage
            
            else // SysexMessages (no encounter yet)
            {
               seqMsgInfo = seqMsgInfo + "OtherMSG: class=" + msg.getClass() + " Tick=" + event.getTick() + "\n";
            } // end of message sorting
            
            trackNum++;
            
         } // end of events loop (per track)
         
         seqMsgInfo = seqMsgInfo + "\n";
         
      } // end of tracks loop
      
      return seqMsgInfo;
      
   } // end of method evenstToString()
   
   
   /**
    * Creates new Track to replace supplied Track
    * @param track Track to be replaced (emptied)
    * @return Track Empty track
    */
   public static void emptyTrack( Sequence sequence, InsTrack insTrack, ArrayList<InsTrack> insTrackList)
   {
      int index;
      
      index = insTrackList.indexOf(insTrack); // store index
      
      if (index >= 0) // exists
      {
         sequence.deleteTrack( insTrackList.get(index).getTrack());
         insTrackList.get(index).setTrack(sequence.createTrack());
      }
   }
   
   /**
    * Removes provided Track from both "sequence" and "trackList"
    * @param track Track to be removed
    * @return boolean Indicates whether performed or not
    */
   public static boolean removeInsTrack( Sequence sequence, InsTrack insTrack, ArrayList<InsTrack> insTrackList)
   {
      if ( insTrackList.remove(insTrack))
      {
         sequence.deleteTrack( insTrackList.get( insTrackList.indexOf(insTrack)).getTrack());
         return true;
      }
      else
      {
         return false;
      }
   }
   
   // GETTERS
   public static Sequence getSequence(String fileIn)
   {
      File midiFile;
      Sequence sequence;
      
      fileIn = setFileExtention(fileIn);
      
      midiFile = new File(fileIn);
      sequence = null;
      
      try
      {
         sequence = MidiSystem.getSequence(midiFile);
      } catch (InvalidMidiDataException invalidEx)
      {
         invalidEx.printStackTrace();
      } catch (IOException ioEx)
      {
         ioEx.printStackTrace();
      }
      
      return sequence;
   }
   
   // SETTERS
   public static void setSolo( ArrayList<InsTrack> inssTrackList)
   {
      
   }
   
   public static String setFileExtention(String fileName)
   {
      // sets extention as .mid
      if (fileName.length() < 4 || !fileName.substring(fileName.length() - 4).equals(".mid"))
         return fileName + ".mid";
      else
         return fileName;
   }
   
} // end of class