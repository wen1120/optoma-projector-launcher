package com.optoma.launcher.achieve;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.hardware.TCorrectManager;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;
import android.widget.Toast;

import com.mstar.android.tv.TvS3DManager;

public class CmdManager {
	private static final String TAG = "CmdManager";
	private static final int ADJUST_BRIGHTNESS_MODE = 0x06;
	private static final int ADJUST_KEYSTONE = 0x04;
	private static final int ADJUST_PROJECTION = 0x08;
	private static final int ADJUST_SCALE = 0x41;
	private static final int ADJUST_SREEEN = 0x58;
	private Context context;
	private UserSettingData userSettingData;
	private TCorrectManager mTCorrectManager;
	private TvS3DManager mTvS3DManager;

	public CmdManager(Context context) {
		this.context = context;
		mTvS3DManager = TvS3DManager.getInstance();
		mTCorrectManager = (TCorrectManager) context.getSystemService("tcorrect");
		userSettingData = queryData();

	}

	public void onDestroy() {
		mTCorrectManager = null;
		mTvS3DManager = null;
	}

	public void setProjectionMode(int value) {
		Send_I2C_DATA(ADJUST_PROJECTION, value, 0x00);
		userSettingData.ProjectMode = value;
		updateDataBase("ProjectMode", value);
	}

	public int getProjectionMode() {
		return userSettingData.ProjectMode;
	}

	public synchronized void setKeystone(int value) {
		Log.i(TAG, "value = " + value);
		Send_I2C_DATA(ADJUST_KEYSTONE, value, 0x00);
		userSettingData.ProjectValue = value;
		updateDataBase("ProjectValue", value);
	}

	public int getKeystone() {
		return userSettingData.ProjectValue;
	}

	public boolean isCurrentShow3dDisplayFormat() {
		int m3ddisplayformat = mTvS3DManager.get3dDisplayFormat();
		int mCurrent3dType = mTvS3DManager.getCurrent3dType();
		Log.i(TAG, String.format("get3ddisplayformat = %s getCurrent3dType = %s ", m3ddisplayformat,mCurrent3dType));
		if (m3ddisplayformat == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_NONE) {
			return false;
		} else if (m3ddisplayformat == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_SIDE_BY_SIDE) {
			return true;
		} else if (m3ddisplayformat == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_TOP_BOTTOM) {
			return true;
		} else {
			boolean auto3dMode = m3ddisplayformat == TvS3DManager.THREE_DIMENSIONS_DISPLAY_FORMAT_AUTO
					&& (mCurrent3dType == TvS3DManager.THREE_DIMENSIONS_TYPE_SIDE_BY_SIDE_HALF || mCurrent3dType == TvS3DManager.THREE_DIMENSIONS_TYPE_TOP_BOTTOM);
			return auto3dMode;
		}
	}

	public synchronized void setEcoMode(int value) {
		if (isCurrentShow3dDisplayFormat()) {
		    Toast.makeText(context, "3d mode ", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.i(TAG, "setEcoMode value = " + value);
		if (value == 0) {//auto mode 
			userSettingData.PowerMode = value;
			updateDataBase("PowerMode", value);
			return;
		}
		int realValue = value - 1;
		Send_I2C_DATA(ADJUST_BRIGHTNESS_MODE, realValue, 0x00);
		userSettingData.PowerMode = value;
		updateDataBase("PowerMode", value);
		/*Log.i(TAG, "setEcoMode realValue = " + realValue);
		switch (realValue) {
		case 0:
			Send_I2C_DATA(0x0B, SystemProperties.getInt("persist.sys.standard", 0), 0x00);
			break;
		case 1:
			Send_I2C_DATA(0x0B, SystemProperties.getInt("persist.sys.warm", 1), 0x00);
			break;
		case 2:
			Send_I2C_DATA(0x0B, SystemProperties.getInt("persist.sys.cool", 2), 0x00);
			break;
		case 3:
			Send_I2C_DATA(0x0B, SystemProperties.getInt("persist.sys.eco", 29), 0x00);
			break;

		default:
			break;
		}*/

	}

	public int getEcoMode() {
		return userSettingData.PowerMode;
	}

	public synchronized void setScreenScaleMode(int value) {
		Send_I2C_DATA(ADJUST_SCALE, value, 0x00);
		userSettingData.TiAspectRatio = value;
		updateDataBase("TiAspectRatio", value);
	}

	public int getScreenScaleMode() {
		return userSettingData.TiAspectRatio;
	}

	public synchronized void setTiSuperFocus(int value) {
		Log.i(TAG, "value = " + value);
		Send_I2C_DATA(ADJUST_SREEEN, 0x00, value);
		userSettingData.TiSuperFocus = value;
		updateDataBase("TiSuperFocus", value);
	}

	public int getTiSuperFocus() {
		return userSettingData.TiSuperFocus;
	}

	public synchronized void setTiHorizontalAspect(int value) {
		Log.i(TAG, "value = " + value);
		Send_I2C_DATA(ADJUST_SREEEN, 0x01, value);
		userSettingData.TiHorizontalAspect = value;
		updateDataBase("TiHorizontalAspect", value);
	}

	public int getTiHorizontalAspect() {
		return userSettingData.TiHorizontalAspect;
	}

	public synchronized void setTiVerticalAspect(int value) {
		Log.i(TAG, "value = " + value);
		Send_I2C_DATA(ADJUST_SREEEN, 0x02, value);
		userSettingData.TiVerticalAspect = value;
		updateDataBase("TiVerticalAspect", value);
	}

	public int getTiVerticalAspect() {
		return userSettingData.TiVerticalAspect;
	}

	public synchronized void setAutoKeystoneEnable(int value) {
		userSettingData.ProjectAutoKeystoneEnable = value;
		updateDataBase("ProjectAutoKeystoneEnable", value);
	}

	public int getAutoKeystoneEnable() {
		return userSettingData.ProjectAutoKeystoneEnable;
	}

	public synchronized void setAutoRotateEnable(int value) {
		userSettingData.ProjectAutoRotateEnable = value;
		updateDataBase("ProjectAutoRotateEnable", value);
	}

	public int getAutoRotateEnable() {
		return userSettingData.ProjectAutoRotateEnable;
	}

	public int getInputSourceSetup() {
		return userSettingData.InputSourceSetup;
	}

	public void setInputSourceSetup(int value) {
		userSettingData.InputSourceSetup = value;
		updateDataBase("InputSourceSetup", value);
	}

	public void switchLedState(boolean value) {
		if (value) {
			SystemProperties.set("sys.show.led", "true");
			Send_I2C_DATA(0x0a, 0x01, 0x00);
		} else {
			SystemProperties.set("sys.show.led", "false");
			Send_I2C_DATA(0x0a, 0x00, 0x00);
		}
	}

	private synchronized void Send_I2C_DATA(int cmd, int parm0, int parm1) {
		mTCorrectManager.WriteTo6401IIc(0x34, cmd, parm0, parm1);
	}

	public UserSettingData queryData() {
		UserSettingData mUserSetting = new UserSettingData();
		Cursor cursor = context.getContentResolver().query(Uri.parse("content://mstar.tv.usersetting/systemsetting"), null, null, null, null);
		if (cursor.moveToFirst()) {
			mUserSetting.ProjectValue = cursor.getInt(cursor.getColumnIndex("ProjectValue"));
			mUserSetting.ProjectMode = cursor.getInt(cursor.getColumnIndex("ProjectMode"));
			mUserSetting.ProjectAutoKeystoneEnable = cursor.getInt(cursor.getColumnIndex("ProjectAutoKeystoneEnable"));
			mUserSetting.ProjectAutoRotateEnable = cursor.getInt(cursor.getColumnIndex("ProjectAutoRotateEnable"));
			mUserSetting.PowerMode = cursor.getInt(cursor.getColumnIndex("PowerMode"));
			mUserSetting.TiSuperFocus = cursor.getInt(cursor.getColumnIndex("TiSuperFocus"));
			mUserSetting.TiAspectRatio = cursor.getInt(cursor.getColumnIndex("TiAspectRatio"));
			mUserSetting.TiHorizontalAspect = cursor.getInt(cursor.getColumnIndex("TiHorizontalAspect"));
			mUserSetting.TiVerticalAspect = cursor.getInt(cursor.getColumnIndex("TiVerticalAspect"));
		}
		cursor.close();
		return mUserSetting;
	}

	public int updateDataBase(String key, int value) {
		int ret = -1;
		ContentValues vals = new ContentValues();
		vals.put(key, value);
		try {
			ret = context.getContentResolver().update(Uri.parse("content://mstar.tv.usersetting/systemsetting"), vals, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public class UserSettingData {
		public int ProjectValue;
		public int ProjectMode;
		public int ProjectAutoKeystoneEnable;
		public int ProjectAutoRotateEnable;
		public int PowerMode;
		public int TiSuperFocus;
		public int TiAspectRatio;
		public int TiHorizontalAspect;
		public int TiVerticalAspect;
		public int InputSourceSetup;
	}
}
