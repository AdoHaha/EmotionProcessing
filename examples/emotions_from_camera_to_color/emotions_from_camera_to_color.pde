import emotionprocessing.*;

import processing.video.*;





/* this example will read from camera after a click
send it to Microsoft, which will influence the color of a
square.

You need to put your KEY from Microsoft Cognitive Services for this to work
*/


Capture cam;
EmotionProcessing emo_recog;
FloatDict emotions;
FloatDict faceRect;
Float happy;
void setup() {
  size(640, 480);
colorMode(RGB, 1.0); //1 will be max
  String[] cameras = Capture.list();
    cam = new Capture(this, cameras[0]);
    cam.start();    
    emo_recog= new EmotionProcessing("KEY");
}

void draw() {
  if (cam.available() == true) {
    cam.read();
  }
  image(cam, 0, 0); //image from camera
  
try{ happy= emotions.get("happiness");



fill(happy,0.0,0.0,0.2);
rect(faceRect.get("x"), faceRect.get("y"), faceRect.get("width"), faceRect.get("height")); // rectangle will appear on someone's face

}catch(Exception e)
{
  happy=0.0;
}



}
void mousePressed ()
{
 emotions= emo_recog.recognizeFromCamera(cam);
 faceRect = emo_recog.getFaceRectangle();
}