public class Note {
   //PROPERTIES
   int key;
   int velocity;
   int startTime;
   int finishTime;
   
   //CONSTRUCTORS
   public Note()
   {
      this(60, 0, 10);
   }
   
   public Note(int key, int startTime, int finishTime)
   {
      this(key, startTime, finishTime, 100);
   }
   
   public Note(int key, int startTime, int finishTime,  int velocity) {
      this.key = key;
      this.velocity = velocity;
      this.startTime = startTime;
      this.finishTime = finishTime;
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
   
   public int getStartTime()
   {
      return startTime;
   }
   
   public int getFinishTime()
   {
      return finishTime;
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
   
   public void setStartTime(int t)
   {
      startTime = t;
   }
   
   public void setFinishTime(int t )
   {
      finishTime = t;
   }
   
}
