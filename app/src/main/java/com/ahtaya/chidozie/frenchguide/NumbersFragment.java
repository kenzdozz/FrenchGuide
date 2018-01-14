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

public class NumbersFragment extends Fragment {

    private MediaPlayer mediaPlayer = null;
    private AudioManager audioManager;

    public NumbersFragment(){}

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

        wordsArray.add(new Words("One", "Un", R.drawable.one, "img", R.raw.one));
        wordsArray.add(new Words("Two", "Deux", R.drawable.two, "img", R.raw.two));
        wordsArray.add(new Words("Three", "Trois", R.drawable.three, "img", R.raw.three));
        wordsArray.add(new Words("Four", "Quatre", R.drawable.four, "img", R.raw.four));
        wordsArray.add(new Words("Five", "Cinq", R.drawable.five, "img", R.raw.five));
        wordsArray.add(new Words("Six", "Six", R.drawable.six, "img", R.raw.six));
        wordsArray.add(new Words("Seven", "Sept", R.drawable.seven, "img", R.raw.seven));
        wordsArray.add(new Words("Eight", "Huit", R.drawable.eight, "img", R.raw.eight));
        wordsArray.add(new Words("Nine", "Neuf", R.drawable.nine, "img", R.raw.nine));
        wordsArray.add(new Words("Ten", "Dix", R.drawable.ten, "img", R.raw.ten));

        ListView numbersList = v.findViewById(R.id.listRootView);
        //WordsAdapter.color = R.color.numbersColor;
        WordsAdapter numbersArray = new WordsAdapter(getActivity(), wordsArray, R.color.numbersColor);
        numbersList.setAdapter(numbersArray);

        numbersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

//    public static NumbersFragment newInstance() {
//        NumbersFragment f = new NumbersFragment();
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