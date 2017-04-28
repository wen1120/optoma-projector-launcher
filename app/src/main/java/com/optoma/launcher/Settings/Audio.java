package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.optoma.launcher.R;


public class Audio extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition;
    private final int yLimit = 9;
    private boolean[] bAudioItems = {true,false,false,false,false};
    private ImageView[] ivAudioOnOff = new ImageView[5];
    private SeekBar[] sbAudio = new SeekBar[2];
    private int[] iAudioVolume = {5,5};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ivAudioOnOff[0] = (ImageView) this.findViewById(R.id.audio_internal_speaker_iv);
        ivAudioOnOff[1] = (ImageView) this.findViewById(R.id.audio_mute_iv);
        ivAudioOnOff[2] = (ImageView) this.findViewById(R.id.audio_mic_iv);
        ivAudioOnOff[3] = (ImageView) this.findViewById(R.id.audio_out_iv);
        ivAudioOnOff[4] = (ImageView) this.findViewById(R.id.audio_srs_iv);
        sbAudio[0] = (SeekBar) this.findViewById(R.id.audio_volume_sb);
        sbAudio[1] = (SeekBar) this.findViewById(R.id.audio_mic_volume_sb);
        xPosition = yPosition = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int ImageSource = 0;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if(yPosition > 0) {
                    yPosition--;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if(yPosition < yLimit - 1) {
                    yPosition++;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                switch (yPosition) {
                    case 0:
                    case 1:
                    case 2:
                        bAudioItems[yPosition] = !bAudioItems[yPosition];
                        ImageSource = bAudioItems[yPosition] ? R.drawable.on : R.drawable.off;
                        ivAudioOnOff[yPosition].setImageResource(ImageSource);
                        Log.d(TAG, "yPosition =  " + yPosition + ",bAudioItems[yPosition] = " + bAudioItems[yPosition]);
                        break;
                    case 3:
                    case 4:
                        if(iAudioVolume[yPosition-3] > 0) {
                            iAudioVolume[yPosition-3]--;
                            sbAudio[yPosition-3].setProgress(iAudioVolume[yPosition-3]);
                        }
                        Log.d(TAG, "yPosition =  " + yPosition + ",iAudioVolume[yPosition-3] = " + iAudioVolume[yPosition-3]);
                        break;
                    case 7:
                    case 8:
                        bAudioItems[yPosition-4] = !bAudioItems[yPosition-4];
                        ImageSource = bAudioItems[yPosition-4] ? R.drawable.on : R.drawable.off;
                        ivAudioOnOff[yPosition-4].setImageResource(ImageSource);
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                switch (yPosition) {
                    case 0:
                    case 1:
                    case 2:
                        bAudioItems[yPosition] = !bAudioItems[yPosition];
                        ImageSource = bAudioItems[yPosition] ? R.drawable.on : R.drawable.off;
                        ivAudioOnOff[yPosition].setImageResource(ImageSource);
                        break;
                    case 3:
                    case 4:
                        if(iAudioVolume[yPosition-3] < 10) {
                            iAudioVolume[yPosition-3]++;
                            sbAudio[yPosition-3].setProgress(iAudioVolume[yPosition-3]);
                        }
                        break;
                    case 7:
                    case 8:
                        bAudioItems[yPosition-4] = !bAudioItems[yPosition-4];
                        ImageSource = bAudioItems[yPosition-4] ? R.drawable.on : R.drawable.off;
                        ivAudioOnOff[yPosition-4].setImageResource(ImageSource);
                        break;
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}