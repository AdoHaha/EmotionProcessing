/*********************************************************************
* Software License Agreement (BSD License)
*
*  Copyright (c) 2014, Andrzej Pronobis <andrzej@pronobis.pro>
*  All rights reserved.
*
*  Redistribution and use in source and binary forms, with or without
*  modification, are permitted provided that the following conditions
*  are met:
*
*   * Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*   * Redistributions in binary form must reproduce the above
*     copyright notice, this list of conditions and the following
*     disclaimer in the documentation and/or other materials provided
*     with the distribution.
*   * Neither the name of the Willow Garage nor the names of its
*     contributors may be used to endorse or promote products derived
*     from this software without specific prior written permission.
*
*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
*  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
*  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
*  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
*  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
*  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
*  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
*  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
*  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
*  LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
*  ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
*  POSSIBILITY OF SUCH DAMAGE.
*********************************************************************/

package emotionprocessing;

import processing.core.*;
import processing.data.FloatDict;
import processing.data.StringDict;
import processing.video.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
//import org.json.*;

import com.google.gson.Gson;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.FaceRectangle;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;




import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
/**
import EmotionRestClient.*;
* The main library class.
 **/




public class EmotionProcessing {
	private	List<RecognizeResult> result;
	private	EmotionServiceRestClient emo_recog;
	private PApplet parent;
	private FloatDict faceRectangle; // this will have the position of the face
	private FloatDict emotions;
public EmotionProcessing(String key) {
   
    emo_recog=new EmotionServiceRestClient( key);
    faceRectangle = new FloatDict();

    faceRectangle.set("x",(float)0);
    faceRectangle.set("y",(float)0);
    faceRectangle.set("width",(float)0);
    faceRectangle.set("height",(float)0);
    
    emotions = new FloatDict();   

    
    emotions.set("anger",(float)0.0);
    emotions.set("contempt",(float)0.0);
    emotions.set("disgust",(float)0.0);
    emotions.set("fear", (float)0.0);
    emotions.set("happiness",(float)0.0);
    emotions.set("neutral",(float)0.0);
    emotions.set("sadness",(float)0.0);
    emotions.set("surprise",(float)0.0);
   
    }

/**
 *returns faceRectangle dictionary with elements x y width height that can be used to draw rect
 *around recognized face
 */
public FloatDict getFaceRectangle()
{

		return faceRectangle;


}
/**
 *returns last recognized emotions for example:                    (1)
 * <p>
 * {"anger":7.111848E-7,"contempt":5.46429249E-8,
 * "disgust":7.002413E-8,"fear":4.01338E-7,
 * "happiness":0.9999927,"neutral":1.87778568E-7,
 * "sadness":3.40486253E-8,"surprise":5.82004441E-6}    (2)
 * here.
 * </p>
 */
public FloatDict getEmotions()
{
	return this.emotions;
}

/**
 *returns StringDict with recognized values of emotions (min 0.0, max 1.0)                    (1)
 * <p>
 * {"anger":7.111848E-7,"contempt":5.46429249E-8,
 * "disgust":7.002413E-8,"fear":4.01338E-7,
 * "happiness":0.9999927,"neutral":1.87778568E-7,
 * "sadness":3.40486253E-8,"surprise":5.82004441E-6}    (2)
 * here.
 * </p>
 * @param emotions 
 */
public 	FloatDict recognizeFromCamera(PImage cam)
{
  
   //int [] cam_pixels=cam.pixels;
   BufferedImage native_image= (BufferedImage) cam.getNative();
    ByteArrayOutputStream output = new ByteArrayOutputStream();

    
    
    
        try{
        ImageIO.write(native_image, "jpg", output);
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
          Gson gson = new Gson();
        try{
         emotions = new FloatDict();
         faceRectangle = new FloatDict();
         result=emo_recog.recognizeImage(inputStream);
         String json = gson.toJson(result);
         
         
    
         PApplet.print("I am this happy:"+ result.get(0).scores.happiness);
         PApplet.print(json);
         
         
         
         emotions.set("anger",(float) result.get(0).scores.anger);
         emotions.set("contempt",(float) result.get(0).scores.contempt);
         emotions.set("disgust",(float) result.get(0).scores.disgust);
         emotions.set("fear", (float) result.get(0).scores.fear);
         emotions.set("happiness",(float)result.get(0).scores.happiness);
         emotions.set("neutral",(float)result.get(0).scores.neutral);
         emotions.set("sadness",(float)result.get(0).scores.sadness);
         emotions.set("surprise",(float)result.get(0).scores.surprise);
         
         
         faceRectangle.set("x",(float)result.get(0).faceRectangle.left);
         faceRectangle.set("y",(float)result.get(0).faceRectangle.top);
         faceRectangle.set("width",(float)result.get(0).faceRectangle.width);
         faceRectangle.set("height",(float)result.get(0).faceRectangle.height);
         this.emotions=emotions;
         
         
        }catch(Exception e) {
        
        	PApplet.println(e);
        }
   
        }catch(Exception e)
        {
        	PApplet.println(e);
        }
        
        //scores --> emotions
      

  return emotions;  
}



  };

