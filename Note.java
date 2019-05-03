public class Note {
   //PROPERTIES
   int key;
   int velocity;
   int startTick;
   int duration;
   
   //CONSTRUCTORS
   public Note()
   {
      this(60, 0, 10);
   }
   
   public Note(int key, int startTick, int duration)
   {
      this(key, startTick, duration, 100);
   }
   
   public Note(int key, int startTick, int duration,  int velocity) {
      this.key = key;
      this.velocity = velocity;
      this.startTick = startTick;
      this.duration = duration;
   }
   
   //METHODS
   
   //GETTERS   
   public int getKey()
   {
      return key;
   }
   
   public int getVelocity()
   {
      return velocity;
   }
   
   public int getStartTick()
   {
      return startTick;
   }
   
   public int getDuration()
   {
      return duration;
   }
   
   //SETTERS   
   public void setKey(int k)
   {
      key = k;
   }
   
   public void setVelocity(int v)
   {
      velocity = v;
   }
   
   public void setStartTick(int t)
   {
      startTick = t;
   }
   
   public void setDuration(int d )
   {
      duration = d;
   }
   
}
