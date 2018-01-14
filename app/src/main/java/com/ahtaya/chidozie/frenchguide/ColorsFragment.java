package com.ahtaya.chidozie.frenchguide;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    private MediaPlayer mediaPlayer = null;
    private AudioManager audioManager;

    public ColorsFragment(){}

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mediaPlayerComplete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_words, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> wordsArray = new ArrayList<>();

        wordsArray.add(new Words("Black", "Noir", R.color.black, "color", R.raw.black));
        wordsArray.add(new Words("White", "Blanc", R.color.white, "color", R.raw.white));
        wordsArray.add(new Words("Red", "Rouge", R.color.red, "color", R.raw.red));
        wordsArray.add(new Words("Yellow", "Jaune", R.color.yellow, "color", R.raw.yellow));
        wordsArray.add(new Words("Blue", "Bleu", R.color.blue, "color", R.raw.blue));
        wordsArray.add(new Words("Green", "Vert", R.color.green, "color", R.raw.green));
        wordsArray.add(new Words("Brown", "Brun", R.color.brown, "color", R.raw.brown));
        wordsArray.add(new Words("Pink", "Rose", R.color.pink, "color", R.raw.pink));
        wordsArray.add(new Words("Orange", "Orange", R.color.orange, "color", R.raw.orange));
        wordsArray.add(new Words("Gray", "Gris", R.color.gray, "color", R.raw.gray));
        wordsArray.add(new Words("Purple", "Violet", R.color.purple, "color", R.raw.purple));

        ListView colorsList = v.findViewById(R.id.listRootView);
        //WordsAdapter.color = R.color.colorsColor;
        WordsAdapter colorsArray = new WordsAdapter(getActivity(), wordsArray, R.color.colorsColor);
        colorsList.setAdapter(colorsArray);

        colorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int audioResource = wordsArray.get(i).getmAudio();
                releaseMediaPlayer();

                int audioFocus = audioManager.requestAudioFocus(audioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (audioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), audioResource);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mediaPlayerComplete);
                }
            }
        });
        return v;
    }

//    public static ColorsFragment newInstance() {
//        ColorsFragment f = new ColorsFragment();
//        return f;
//    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}