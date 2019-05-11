package codeChoire.midiCore;

/**
 * Class to represent a musical note.
 * 
 * @author Celil Bugra Karacan (github.com/psykobug)
 * @version 0.1.0
 * @date 2019-05-07
 */
public class Note
{
 // PROPERTIES
 int key;
 int velocity;
 int tick;
 int duration;

 // CONSTRUCTORS
 public Note()
 {
  this(60, 0, 10);
 }

 public Note(int key, int tick, int duration)
 {
  this(key, tick, duration, 100);
 }

 public Note(int key, int tick, int duration, int velocity)
 {
  this.key = key;
  this.velocity = velocity;
  this.tick = tick;
  this.duration = duration;
 }

 // METHODS

 // GETTERS
 public int getKey()
 {
  return key;
 }

 public int getVelocity()
 {
  return velocity;
 }

 public int getTick()
 {
  return tick;
 }

 public int getDuration()
 {
  return duration;
 }

 // SETTERS
 public void setKey(int k)
 {
  key = k;
 }

 public void setVelocity(int v)
 {
  velocity = v;
 }

 public void setTick(int t)
 {
  tick = t;
 }

 public void setDuration(int d)
 {
  duration = d;
 }

}
