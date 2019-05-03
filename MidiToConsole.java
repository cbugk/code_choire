import java.io.File;

import javax.sound.midi.*;

public class MidiToConsole 
{
   
   public static void main(String[] args) throws Exception 
   {
      Sequence sequence = MidiSystem.getSequence(new File("ask.mid"));
      
      int trackNumber = -1;
      for (Track track :  sequence.getTracks()) 
      {
         trackNumber++;
         System.out.println("Track " + trackNumber + ": size = " + track.size() + " | tick = " + track.ticks());
         System.out.println("##########################################################################################################################");
         for (int i=0; i < track.size(); i++) 
         { 
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) 
            {
               ShortMessage sm = (ShortMessage) message;
               if (sm.getCommand() != 144 && sm.getCommand() != 128)
               {
                  System.out.print("@" + event.getTick() + " ");
                  System.out.println(
                                     "Channel: " + sm.getChannel() + " | Command: " + sm.getCommand() + " | Key: " + 
                                     sm.getData1() + " | Velo: " + sm.getData2() + " ___ShrtMsg"
                                    );
               }
            }
            else if (message instanceof MetaMessage)
            {
               System.out.print("@" + event.getTick() + " ");
               MetaMessage mm = (MetaMessage) message;
               System.out.print("Type: " + mm.getType() + " | ");
               if (mm.getType() == 1 || mm.getType() == 2 || mm.getType() == 3 || mm.getType() == 4 || mm.getType() == 5 || mm.getType() == 6 || mm.getType() == 7 || mm.getType() == 9)
               {
                  System.out.print("Data: ");
                  for (int j = 0; j < mm.getData().length; j++)
                  {
                     if (mm.getData()[j] < 255 && mm.getData()[j] > 0)
                        System.out.print(Character.toString( (char) mm.getData()[j]));
                     else
                        System.out.print(mm.getData()[j]);
                  }
               }
               else if (mm.getType() == 47)
               {
                  System.out.print("End of Track | ");
                  
               }
               else if (mm.getType() == 81)
               {
                  System.out.print("Set Tempo | ");
                  byte[] data = mm.getData();
                  int mpq = ((data[0] & 0x7f) << 14) | ((data[1] & 0x7f) << 7) | (data[2] & 0x7f);
               }
               System.out.println(
                                  "--------------------------MetaMsg"
                                 );
            }
            else
            {
               System.out.print("@" + event.getTick() + " ");
               System.out.println("===============================Other message: " + message.getClass());
            }
         }
         
         System.out.println();
      }
      
   }
}