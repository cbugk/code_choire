package codeChoire.midiCore;

import codeChoire.midiCore.*;
import javax.sound.midi.*;
import java.util.ArrayList;

/**
 * class to fool around with codeChoire.midiCore package
 * @author Celil Bugra Karacan (github.com/psykobug)
 * @version 0.1.0
 * @date 2019-05-07
 */
public class SequencerHandler
{
   static Sequencer sequencer;
   
   //MAIN METHOD
   public static void main( String[] args)
   {
      final String FILE_IN = "../midi_files/kameraden";
      final String FILE_OUT = "./test.mid";
      InsTrack insTrack;
      Track track;
      Sequence sequence = null;
      ArrayList<InsTrack> insTrackList;
      
      int trackChannel;
      int trackProgram;
      
      insTrackList = new ArrayList<InsTrack>();
      
      trackProgram = 23;
      trackChannel = 2;
      
      try{
         //sequence = MidiConductor.getSequence( FILE_IN);
         sequence = new Sequence( Sequence.PPQ, 4);
         
         track = sequence.createTrack();
         insTrack = new InsTrack( track, trackChannel, trackProgram);
         insTrack.setTrack(track);
         
         insTrackList.add( insTrack);
         
         
         insTrack.addNote( new Note(37, 0, 20));
         insTrack.addNote( new Note(60, 15, 20));
         insTrack.addNote( new Note(37, 35, 20));
         insTrack.addNote( new Note(37, 55, 20));
         insTrack.addNote( new Note(37, 75, 20));
         insTrack.addNote( new Note(37, 25, 20));
         insTrack.composeTrack();
         
         //MidiConductor.removeTrack(sequence, trackList.get(0), trackList);
         
         sequencer = MidiSystem.getSequencer();
         sequencer.open();
         sequencer.setSequence(sequence);
      }
      catch (Exception ex)
      {
      }
      
      MidiConductor.start(sequencer);
      
      try{
      Thread.sleep(1500);
      } catch (Exception ex) {}
      
      MidiConductor.stop(sequencer);
      
      try{
      Thread.sleep(1500);
      } catch (Exception ex) {}
      
      MidiConductor.start(sequencer);
      //MidiConductor.getOrchestra();      
      System.out.print( MidiConductor.eventsToString( sequence));
   }

}