package codeChoire.gui;

import codeChoire.gui.*;
import codeChoire.midiCore.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class for TrackPanel of Code_Choire, see program manual for further info
 * 
 * @author Celil Bugra Karacan (github.com/psykobug/code_choire)
 * @version 0.1.0
 * @date 2019-05-09
 */
public class TrackPanel extends JPanel
{
   //PROPERTIES
   ArrayList<InsTrackPanel> insTrackPanelList;
   LabelTimelinePanel labelPanel;
   GridBagConstraints constraints;
   
   //CONSTRUCTORS
   public TrackPanel( ArrayList<InsTrack> insTrackList)
   {
      //Layout Set
      this.setLayout(new GridBagLayout());
      constraints = new GridBagConstraints();
      constraints.gridx = 0; //single column
      constraints.fill = GridBagConstraints.HORIZONTAL;
      
      
      labelPanel = new LabelTimelinePanel();
      
      if (insTrackList != null)
      {
         insTrackPanelList = new ArrayList<InsTrackPanel>();
         
         for (int j = 0; j < insTrackList.size(); j++)
         {
            insTrackPanelList.add( new InsTrackPanel(insTrackList.get(j)));
            //same as calling reDrawInsTrackPanels()
         }
      }
      
      addComponents();
      reDrawInsTrackPanels();
   }
   
   //METHODS
   public void reDrawInsTrackPanels()
   {
      for (int j = 0; j < insTrackPanelList.size(); j++)
      {
         insTrackPanelList.get(j).reDraw();
      }
   }
   
   public void addComponents()
   {
      //add to top
      constraints.gridy = 0; //resets at each call
      this.add(labelPanel, constraints);
      
      //add InsTrackPanels afterwards
      for (int j = 0; j < insTrackPanelList.size(); j++)
      {
         constraints.gridy = j + 1; 
         this.add(insTrackPanelList.get(j), constraints);
      }
   }
   
   //GETTERS
   public ArrayList<InsTrackPanel> getInsTrackPanelList()
   {
      return insTrackPanelList;
   }
   
   //SETTERS
   public void setInsTrackList( ArrayList<InsTrackPanel> insTrackPanelList)
   {
      this.insTrackPanelList = insTrackPanelList;
   }
   
   
   
}