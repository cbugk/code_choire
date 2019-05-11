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
public class LabelTimelinePanel extends JPanel
   {
      //PROPERTIES
      JLabel label;
      
      //CONSTRUCTOR
      LabelTimelinePanel()
      {
         this.add(label = new JLabel("Track Name     Instrument     S   M       Volume     Panning"));
      }
   }