package com.example.thinkpad.ez300k;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by evgen on 01.11.2015.
 */
public class MediaPlayerServices extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener {
    enum State {
        //Retrieving,
        //Stopped,
        Preparing,
        Playing,
        Paused
    }

    private static String PLAYER_TAG = "It's media player, baby";

    public static String PARCELABLE_PLAYLIST = "NIKTO NE ZNAET 4TO TYT";

    public static final String ACTION_PLAY = "com.example.thinkpad.ez300k.mediaplayerservices.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.example.thinkpad.ez300k.mediaplayerservices.ACTION_PAUSE";
    public static final String ACTION_ADDTOPLAYLIST = "com.example.thinkpad.ez300k.mediaplayerservices.ACTION_ADDTOPLAYLIST";
    //public static String ACTION_PLAYSONG = "PLAYSONG";
    public static final String ACTION_PLAYPLAYLIST = "com.example.thinkpad.ez300k.mediaplayerservices.ACTION_PLAYPLAYLIST";
    //public static String ACTION_PLAYSONG = "PLAY";


    private static MediaPlayer mMediaPlayer;
    private static ArrayList<Song> mSongs;
    private static int mPosition;
    private State mState = State.Paused;


    private final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder = null;



    public MediaPlayerServices() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mPosition = 0;
        mSongs = new ArrayList<>();
    }

    private void initOther() {

    }

    public void setList(ArrayList<Song> songs) {
        mSongs = songs;
    }

    private void initMediaPlayerIfNeed() {
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (mSongs == null)
            initOther();

        if (action.equals(ACTION_PLAY)) playRequest();
        else if (action.equals(ACTION_PAUSE)) pauseRequest();
        else if (action.equals(ACTION_ADDTOPLAYLIST)) addToPlayList(intent);
        else if (action.equals(ACTION_PLAYPLAYLIST)) playPlayList(intent);
        else {
        }

        return START_NOT_STICKY;
    }

    private void playRequest() {
        if (mSongs.size() == 0)
            return;
        initMediaPlayerIfNeed();
        checkBorder();

        if (mState == State.Paused) {
            mState = State.Playing;
            Song songToPlay = mSongs.get(mPosition);
            try {
                mMediaPlayer.setDataSource(/*songToPlay.songURL*/"http://cs1-41v4.vk-cdn.net/p7/90c35f5c296ca1.mp3?extra=q9Qdci3IWYXcko_NUfR-QlhAgmuNolZ5RGE2WBM4H82VcbrjkcZOhsAV3KiqlZ-xXa0jI3ujUAXJEXuTTmp7QAAPWvEqGMHBpw,147");
                mState = State.Preparing;
                mMediaPlayer.prepareAsync();
            } catch (IOException ex) {
                Log.e("MusicService", "IOException playing next song: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void pauseRequest() {
        if (mState == State.Playing) {
            mState = State.Paused;
            //mMediaPlayer.pause();
            relaxResources();
        }
    }

    private void addToPlayList(Intent intent) {
        ArrayList<Song> songs = Parcels.unwrap(intent.getParcelableExtra(PARCELABLE_PLAYLIST));
        if (songs==null || songs.size() == 0) {
            Log.e(PLAYER_TAG, "addToPlayList null or size==0");
            return;
        }
        mSongs.addAll(songs);
    }

    private void playPlayList(Intent intent) {
        ArrayList<Song> songs = Parcels.unwrap(intent.getParcelableExtra(PARCELABLE_PLAYLIST));
        if (songs==null || songs.size()==0){
            Log.e(PLAYER_TAG, "playPlayList null or size==");
            return;
        }
        pauseRequest();
        mSongs.clear();
        mSongs.addAll(songs);
        playFromStart();
    }

    private void relaxResources() {
        stopForeground(true);
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void checkBorder() {
        if (mPosition >= mSongs.size() || mPosition < 0)
            mPosition = 0;
    }

    private void playFromStart() {
        mPosition = 0;
        playRequest();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mState = State.Playing;
        mp.start();
        setUpAsForeground(mSongs.get(mPosition) + " (playing)");
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mMediaPlayer == null) initMediaPlayerIfNeed();
                else if (!mMediaPlayer.isPlaying()) mMediaPlayer.start();
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mMediaPlayer.isPlaying()) mMediaPlayer.pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mMediaPlayer.isPlaying()) mMediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    void playNextSong(Song songToPlay) {

    }

    void setUpAsForeground(String text) {
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        // Build the notification object.
        mNotificationBuilder = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_play_pause)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("RandomMusicPlayer")
                .setContentText(text)
                .setContentIntent(pi)
                .setOngoing(true);
        startForeground(NOTIFICATION_ID, mNotificationBuilder.build());
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
