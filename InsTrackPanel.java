package codeChoire.gui;

import codeChoire.midiCore.*;
import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;

/*
 * @author Celil Bugra Karacan (github.com/psykobug)
 * @version 0.1.0
 * @date 2019-05-10
 */

public class InsTrackPanel extends JPanel
{
   //PROPERTIES
   GridBagConstraints constraints;
   InsTrack insTrack;
   JComboBox<String> programFamilyBox;
   JComboBox<String> programBox;
   Instrument[] programs;
   String[] programNames;
   String[] familyProgramNames;
   JTextField textField;
   int maxProgramNameLength;
   String emptyProgramName;
   
   final int PROGRAM_PER_FAMILY = 8;
   final int PANEL_WIDTH = 800;
   final int PANEL_HEIGHT = 100;
   final String[] programFamilies = { "Piano", "Chromatic Percussion", "Organ", "Guitar", "Bass", "Strings", "Ensemble",
      "Brass", "Reed", "Pipe", "Synth Lead", "Synth Pad", "Synth Effects", "Ethnic", "Percussive", "Sound Effects"
   }; //represents channel of the InsTrack
   
   //CONSTRUCTOR
   InsTrackPanel( InsTrack insTrack)
   {
      this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
      
      //Layout Set
      this.setLayout(new GridBagLayout());
      constraints = new GridBagConstraints();
      constraints.gridx = 0; //single column
      constraints.fill = GridBagConstraints.BOTH;
      constraints.gridy = 0;
      
      
      this.insTrack = insTrack;
      
      emptyProgramName = "";
      maxProgramNameLength = 0;
      
      programs = MidiConductor.getOrchestra();
      if (programs != null)
      {
         programNames = new String[programs.length];
         for (int j = 0; j < programs.length; j++)
         {
            programNames[j] = programs[j].toString();
         }
      }
      
      for (int j = 0; j < programs.length; j++)
      {
         if (programNames[j] != null && programNames[j].length() > maxProgramNameLength)
         {
            maxProgramNameLength = programNames[j].length();
         }
      }
      
      for (int j = 0; j < maxProgramNameLength; j++)
      {
         emptyProgramName = emptyProgramName + " ";
      }
      
      updateProgramBox();
   }
   
   //METHODS
   public void reDraw()
   {     
      if (programs != null)
      {
         programNames = new String[programs.length];
         
         for (int j = 0; j < programs.length; j++)
         {
            programNames[j] = programs[j].toString();
         }
      }
      
      constraints = new GridBagConstraints();
      
      textField = new JTextField("asdsd");
      textField.setPreferredSize(new Dimension (100,26));
      constraints.fill = GridBagConstraints.HORIZONTAL;
      add(textField, constraints);
      
      
      programFamilyBox = new JComboBox<String>(programFamilies);
      
      add(programFamilyBox);
      
      programBox = new JComboBox<String>(new String[]{emptyProgramName});
      constraints.gridx = 2;
      add(programBox);
      
   }
   
   //METHODS
   public void updateProgramBox()
   {
      int channel = insTrack.getChannel();
      familyProgramNames = new String[PROGRAM_PER_FAMILY];
      
      for (int j = 0; j < PROGRAM_PER_FAMILY; j++)
      {
         familyProgramNames[j] = programNames[ j + channel];
      }
      
      programBox = new JComboBox<String>( programNames); // 8 Instrument per Family
   }
   
   //GETTERS
   public InsTrack getInsTrack()
   {
      return insTrack;
   }
   
   //SETTERS
   public void setInsTrack(InsTrack insTrack)
   {
      this.insTrack = insTrack;
   }
}