package com.optoma.launcher.Settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.optoma.launcher.R;


public class Audio extends Activity {
    private static final String TAG = "LauncherLog";
    private int xPosition, yPosition;
    private final int yLimit = 9;
    private boolean[] bAudioItems = {true,false,false,false,false};
    private int[] iAudioChoice = {0,0};
    private ImageView[] ivAudioOnOff = new ImageView[5];
    private SeekBar[] sbAudio = new SeekBar[2];
    private TextView[] tvAudio = new TextView[2];
    private int[] iAudioVolume = {5,5};
    public String[] sAudioInput = {
            "Default",
            "Audio 1(Mini-Jack)",
            "RCA",
            "Audio 2",
            "Audio 3",
            "Audio 4",
            "HDMI 1",
            "HDMI 2",
            "Audio 5/Displayport",
            "Displayport"
    };

    private static int[] AudioIVID = {
            R.id.audio_internal_speaker_iv,
            R.id.audio_mute_iv,
            R.id.audio_mic_iv,
            R.id.audio_out_iv,
            R.id.audio_srs_iv
    };
    private static int[] AudioSBID = {
            R.id.audio_volume_sb,
            R.id.audio_mic_volume_sb
    };
    private static int[] AudioTVID = {
            R.id.audio_input_content_tv,
            R.id.audio_delay_content_tv
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        for(int i=0;i< ivAudioOnOff.length; i++) {
            ivAudioOnOff[i] = (ImageView) this.findViewById(AudioIVID[i]);
            if(i<sbAudio.length)
                sbAudio[i] = (SeekBar) this.findViewById(AudioSBID[i]);
            if(i<tvAudio.length)
                tvAudio[i] = (TextView) this.findViewById(AudioTVID[i]);
        }
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
                    case 5:
                        iAudioChoice[0] = (iAudioChoice[0] == 0) ? sAudioInput.length - 1 : iAudioChoice[0] - 1;
                        tvAudio[0].setText(sAudioInput[iAudioChoice[0]]);
                        break;
                    case 6:
                        iAudioChoice[1] = (iAudioChoice[1] == 0) ? 6 : iAudioChoice[1] - 2;
                        tvAudio[1].setText(new String(iAudioChoice[1]+"ms"));
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
                    case 5:
                        iAudioChoice[0] = (iAudioChoice[0] == sAudioInput.length - 1) ? 0 : iAudioChoice[0] + 1;
                        tvAudio[0].setText(sAudioInput[iAudioChoice[0]]);
                        break;
                    case 6:
                        iAudioChoice[1] = (iAudioChoice[1] == 6) ? 0 : iAudioChoice[1] + 2;
                        tvAudio[1].setText(new String(iAudioChoice[1]+"ms"));
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