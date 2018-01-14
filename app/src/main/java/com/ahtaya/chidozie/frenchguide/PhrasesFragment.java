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

public class PhrasesFragment extends Fragment {

    private MediaPlayer mediaPlayer = null;
    private AudioManager audioManager;

    public PhrasesFragment(){}

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

        wordsArray.add(new Words("Come here", "Venez ici", R.raw.p1));
        wordsArray.add(new Words("Go out", "Sortir", R.raw.p2));
        wordsArray.add(new Words("Sit down", "Asseyez-vous", R.raw.p3));
        wordsArray.add(new Words("Eat food", "Mangez de la nourriture", R.raw.p4));
        wordsArray.add(new Words("Call me", "Appelle-moi", R.raw.p5));
        wordsArray.add(new Words("Stay there", "Reste là", R.raw.p6));
        wordsArray.add(new Words("Shut up", "Tais-toi", R.raw.p7));
        wordsArray.add(new Words("Bring it", "Amène le", R.raw.p8));
        wordsArray.add(new Words("Take this", "Prends ça", R.raw.p9));
        wordsArray.add(new Words("Leave now", "Pars maintenant", R.raw.p10));

        ListView phrasesList = v.findViewById(R.id.listRootView);
        //WordsAdapter.color = R.color.phrasesColor;
        WordsAdapter phrasesArray = new WordsAdapter(getActivity(), wordsArray, R.color.phrasesColor);
        phrasesList.setAdapter(phrasesArray);

        phrasesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

//    public static PhrasesFragment newInstance() {
//        PhrasesFragment f = new PhrasesFragment();
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