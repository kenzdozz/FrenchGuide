package com.ahtaya.chidozie.frenchguide;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SentencesFragment extends Fragment {

    private MediaPlayer mediaPlayer = null;
    private AudioManager audioManager;

    public SentencesFragment(){}

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

        // clone the inflater using the ContextThemeWrapper
        View v = inflater.inflate(R.layout.activity_words, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> wordsArray = new ArrayList<>();

        wordsArray.add(new Words("What is your name?", "Comment vous appelez-vous?", R.raw.s1));
        wordsArray.add(new Words("Can we be friends?", "Peut-on être amis?", R.raw.s2));
        wordsArray.add(new Words("I like you.", "Je vous aime bien.", R.raw.s3));
        wordsArray.add(new Words("Where do you live?", "Où habites-tu?", R.raw.s4));
        wordsArray.add(new Words("Let us play a game.", "Laissez-nous jouer à un jeu.", R.raw.s5));
        wordsArray.add(new Words("Tell me a secret.", "Dis moi un secret.", R.raw.s6));
        wordsArray.add(new Words("Give me your number.", "Donne-moi ton numéro.", R.raw.s7));
        wordsArray.add(new Words("Can I call you tonight?", "Puis-je vous appeler ce soir?", R.raw.s8));
        wordsArray.add(new Words("We will see tomorrow.", "Nous verrons demain.", R.raw.s9));
        wordsArray.add(new Words("Take care of yourself.", "Prenez soin de vous.", R.raw.s10));

        ListView sentenceList = v.findViewById(R.id.listRootView);
        //WordsAdapter.color = R.color.sentenceColor;
        WordsAdapter sentenceArray = new WordsAdapter(getActivity(), wordsArray, R.color.sentenceColor);
        sentenceList.setAdapter(sentenceArray);


        sentenceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

//    public static SentencesFragment newInstance() {
//        SentencesFragment f = new SentencesFragment();
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