package com.example.main;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;

// handle all backend logic
public class MainController {

    // retreive all the voices that freetts has
    // to do that we need to make VoiceManager obj
    // voiceManager allows user to select and choose different voice for speech
    private static VoiceManager voiceManager = VoiceManager.getInstance();

    // we will be calling these methods to populate the combobox
    // return a list of voices as string
    public static ArrayList<String> getVoices() {
        // need KevinVoice
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        // create a for-each loop to iterrate through the available voices in out
        // VoiceManager obj then add them to the list
        ArrayList<String> voices = new ArrayList<>();
        for (Voice voice : voiceManager.getVoices()) {
            voices.add(voice.getName());
        }

        return voices;
    }

    public static ArrayList<String> getSpeedRates() {
        ArrayList<String> speedRates = new ArrayList<>(); // this will be the list that we returning
        // we will type in the speed manually
        speedRates.add("60"); // very slow
        speedRates.add("100"); // slow
        speedRates.add("140"); // normal
        speedRates.add("170"); // fast
        speedRates.add("200"); // very fast

        return speedRates;
    }

    public static ArrayList<String> getVolumeLevels() {
        ArrayList<String> volumeLevels = new ArrayList<>();
        // volumes go from 0 to 10 where 10 is loudest
        // Here we created a for loop that iterates from 0 to 10 inclusive and we will be storing the value of i to the
        // volumeLevels list
        for(int i = 0; i <= 10; i++) {
            volumeLevels.add(Integer.toString(i));
        }

        return  volumeLevels;
    }

    // speak fauture
    // only call this method once the speak button is pressed
    public static void speak(String message, String voiceType, String rate, String volume) {
        // message is the text that will be converted to speach
        // voiceType will be the voice to be used
        // rate will be how fast the voive will talk
        // volume will be how loud or quite the voice will be

        // first create the voice obj that will store the voice
        Voice voice = voiceManager.getVoice(voiceType);
        // catch error if the voice can not be found
        // we will terminate the program
        if (voice == null) {
            System.err.println("Cannot find voice: " + voiceType);
            System.exit(1);
        }

        // allocate the resources for the voice
        voice.allocate();

        // set the speed at which the text will be spoken (words per minute)
        voice.setRate(Integer.parseInt(rate));

        // set the volume (0 - 10)
        voice.setVolume(Integer.parseInt(volume));

        // convert text to speech
        voice.speak(message);

        // deallocate the resources when done
        voice.deallocate();
    }
}
