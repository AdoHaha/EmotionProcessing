import microsoftemotionprocessing.*;

import processing.video.*;


/* this example will read from camera after a click
send it to Microsoft, which will influence the color of a
square.

You need to put your KEY from Microsoft Cognitive Services for this to work
*/


Capture cam;
MicrosoftEmotionProcessing emo_recog;
FloatDict emotions;
Float happy;
void setup() {
  size(640, 480);
colorMode(RGB, 1.0); //1 will be max
  String[] cameras = Capture.list();
    cam = new Capture(this, cameras[0]);
    cam.start();    
    emo_recog= new MicrosoftEmotionProcessing("KEY");
}

void draw() {
  if (cam.available() == true) {
    cam.read();
  }
  image(cam, 0, 0);
  // The following does the same, and is faster when just drawing the image
  // without any additional resizing, transformations, or tint.
  //set(0, 0, cam);
try{ happy= emotions.get("happiness");

}catch(Exception e)
{
  happy=0.0;
}

fill(happy,0.0,0.0);
rect(30, 20, 55, 55);


}
void mousePressed ()
{
 emotions= emo_recog.recognizeFromCamera(cam);
}