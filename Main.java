import codeChoire.gui.*;
import codeChoire.midiCore.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.sound.midi.*;

/*
 * @author Celil Bugra Karacan (github.com/psykobug)
 * @version 0.1.0
 * @date 2019-05-10
 */
public class Main
{
   //PROPERTIES
   
   
   //CONSTRUCTOR
   public static void main(String[] args)
   {
      JFrame frame;
      TrackPanel tPanel;
      
      frame = new JFrame("TrackPanelTest");
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.setLayout( new BorderLayout() );
      frame.setBounds(300,100,400,400);
      
      
      
      //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
      Sequence sequence = null;
      ArrayList<InsTrack> insTrackList;
      ArrayList<Track> trackList;
      InsTrack insTrack = null;
      Track track;
      int trackChannel;
      int trackProgram;
      trackList = new ArrayList<Track>();
      trackProgram = 23;
      trackChannel = 2;
      
      try{
         //sequence = MidiConductor.getSequence( FILE_IN);
         sequence = new Sequence( Sequence.PPQ, 4);
         track = sequence.createTrack();
         trackList.add( track);
         insTrack = new InsTrack( track, trackChannel, trackProgram);
         insTrack.addNote( new Note(37, 0, 20));
         insTrack.addNote( new Note(60, 15, 20));
         insTrack.addNote( new Note(37, 35, 20));
         insTrack.addNote( new Note(37, 55, 20));
         insTrack.addNote( new Note(37, 75, 20));
         insTrack.addNote( new Note(37, 25, 20));
         
         insTrack.composeTrack();
         //MidiConductor.removeTrack(sequence, trackList.get(0), trackList);
         //sequencer = MidiSystem.getSequencer();
         //sequencer.open();
         //sequencer.setSequence(sequence);
         //sequencer.start();
      }
      catch (Exception ex)
      {
      }
      //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
      
      //create and add InsTracks to insTrackList
      insTrackList = new ArrayList<InsTrack>();
      insTrackList.add(insTrack);
      
      //create TrackPanel with insTrack
      tPanel = new TrackPanel( insTrackList);
      tPanel.add(new JLabel("hello"));
      
      frame.add(tPanel);
      
      System.out.print( MidiConductor.eventsToString( sequence));
      
      frame.pack();
      frame.setVisible(true);   
   }
}