  Microsoft Emotion Processing in Processing
  =============
  
 This library for Processing (processing.org) uses Microsoft Cognitive Services to recognize emotions from images.
 It is therefore a client of Microsoft Cognitive Services. 
 

Installation
------------

Just clone the repo to the `libraries` folder inside your Processing sketchbook and restart Processing.

Basic Usage
-----

1. Import the library
    ```
    import microsoftemotionprocessing.*;
    ```

2. Create a global variable for the main library class
    ```
    MicrosoftEmotionProcessing emo_recog;
    ```
3. Initialize the library and connect to Microsoft Cognitive Services inside your `setup()`. You need
to provide a KEY, that you can get from Microsoft (register at [https://www.microsoft.com/cognitive-services])
    ```
    void setup() {
      ...
      emo_recog= new MicrosoftEmotionProcessing("KEY");
      ...
    }
    ```

Next you can use a method recognizeFromCamera to recognise emotions of a person on any PImage picture.
You will get a FloatDict of emotions in return

    ```
    FloatDict emotions;
    emotions = emo_recog.recognizeFromCamera(cam);
    println(emotions.get("happiness");
    ```

See examples for a detailed example.
    
    
