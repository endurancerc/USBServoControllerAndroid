package com.appspot.usbhidterminal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.appspot.usbhidterminal.core.Consts;
import com.appspot.usbhidterminal.core.events.DeviceAttachedEvent;
import com.appspot.usbhidterminal.core.events.DeviceDetachedEvent;
import com.appspot.usbhidterminal.core.events.LogMessageEvent;
import com.appspot.usbhidterminal.core.events.PrepareDevicesListEvent;
import com.appspot.usbhidterminal.core.events.SelectDeviceEvent;
import com.appspot.usbhidterminal.core.events.ShowDevicesListEvent;
import com.appspot.usbhidterminal.core.events.USBDataReceiveEvent;
import com.appspot.usbhidterminal.core.events.USBDataSendEvent;
import com.appspot.usbhidterminal.core.services.SocketService;
import com.appspot.usbhidterminal.core.services.USBHIDService;
import com.appspot.usbhidterminal.core.services.WebServerService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class USBHIDTerminal extends Activity implements View.OnClickListener {

	private SharedPreferences sharedPreferences;
	private Intent usbService;
	//private Button btnSelectHIDDevice;
	private ImageView btnSelectHIDDevice;
	private String settingsDelimiter;
	private String receiveDataFormat;
	private String delimiter;
	private SeekBar sbCH1, sbCH2, sbCH3, sbCH4, sbCH5, sbCH6, sbCH7, sbCH8;
	private TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8;
	private int sb1_val,sb2_val,sb3_val,sb4_val,sb5_val,sb6_val,sb7_val,sb8_val = 1;
	byte[] arr = new byte[8];
	private Boolean connected = false;
	protected EventBus eventBus;
	private TextView textView9;


	private void prepareServices() {
		usbService = new Intent(this, USBHIDService.class);
		startService(usbService);
		//webServerServiceIsStart(sharedPreferences.getBoolean("enable_web_server", false));
		//socketServiceIsStart(sharedPreferences.getBoolean("enable_socket_server", false));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		try {
			eventBus = EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).installDefaultEventBus();
		} catch (EventBusException e) {
			eventBus = EventBus.getDefault();
		}
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		//sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
		initUI();
	}

	public void update() {
		if(connected) {
			sb1_val = sbCH1.getProgress() + 1;
			sb2_val = sbCH2.getProgress() + 1;
			sb3_val = sbCH3.getProgress() + 1;
			sb4_val = sbCH4.getProgress() + 1;
			sb5_val = sbCH5.getProgress() + 1;
			sb6_val = sbCH6.getProgress() + 1;
			sb7_val = sbCH7.getProgress() + 1;
			sb8_val = sbCH8.getProgress() + 1;

			arr[0] = (byte)sb1_val;
			arr[1] = (byte)sb2_val;
			arr[2] = (byte)sb3_val;
			arr[3] = (byte)sb4_val;
			arr[4] = (byte)sb5_val;
			arr[5] = (byte)sb6_val;
			arr[6] = (byte)sb7_val;
			arr[7] = (byte)sb8_val;

			eventBus.post(new USBDataSendEvent(arr));
		}
	}



	private void initUI() {

		btnSelectHIDDevice = (ImageView) findViewById(R.id.btnSelectHIDDevice);
		btnSelectHIDDevice.setOnClickListener(this);


		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);
		textView5 = (TextView) findViewById(R.id.textView5);
		textView6 = (TextView) findViewById(R.id.textView6);
		textView7 = (TextView) findViewById(R.id.textView7);
		textView8 = (TextView) findViewById(R.id.textView8);

		textView9 = (TextView) findViewById(R.id.textView9);

		sbCH1 = (SeekBar) findViewById(R.id.sbCH1);

		// Set up a listener for the SeekBar change events
		sbCH1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView1.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH2 = (SeekBar) findViewById(R.id.sbCH2);

		// Set up a listener for the SeekBar change events
		sbCH2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView2.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH3 = (SeekBar) findViewById(R.id.sbCH3);

		// Set up a listener for the SeekBar change events
		sbCH3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView3.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH4 = (SeekBar) findViewById(R.id.sbCH4);

		// Set up a listener for the SeekBar change events
		sbCH4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView4.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH5 = (SeekBar) findViewById(R.id.sbCH5);

		// Set up a listener for the SeekBar change events
		sbCH5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView5.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH6 = (SeekBar) findViewById(R.id.sbCH6);

		// Set up a listener for the SeekBar change events
		sbCH6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView6.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH7 = (SeekBar) findViewById(R.id.sbCH7);

		// Set up a listener for the SeekBar change events
		sbCH7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView7.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		sbCH8 = (SeekBar) findViewById(R.id.sbCH8);

		// Set up a listener for the SeekBar change events
		sbCH8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			//@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// Update the TextView with the SeekBar value
				textView8.setText(String.valueOf(progress + 1));
				update();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void onClick(View v) {
		if (v == btnSelectHIDDevice) {
			eventBus.post(new PrepareDevicesListEvent());
		}
	}

	void showListOfDevices(CharSequence devicesName[]) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if (devicesName.length == 0) {
			builder.setTitle(Consts.MESSAGE_CONNECT_YOUR_USB_HID_DEVICE);
		} else {
			builder.setTitle(Consts.MESSAGE_SELECT_YOUR_USB_HID_DEVICE);
		}

		builder.setItems(devicesName, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				eventBus.post(new SelectDeviceEvent(which));
			}
		});
		builder.setCancelable(true);
		builder.show();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(USBDataReceiveEvent event) {
//		mLog(event.getData() + " \nReceived " + event.getBytesCount() + " bytes", true);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(LogMessageEvent event) {
//		mLog(event.getData(), true);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(ShowDevicesListEvent event) {
		showListOfDevices(event.getCharSequenceArray());
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(DeviceAttachedEvent event) {
		connected = true;
		textView9.setText("Servo Controller Connected");
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onEvent(DeviceDetachedEvent event) {
		connected = false;
		textView9.setText("Servo Controller Not Connected");
	}

	@Override
	protected void onStart() {
		super.onStart();
		receiveDataFormat = sharedPreferences.getString(Consts.RECEIVE_DATA_FORMAT, Consts.TEXT);
		prepareServices();
		setDelimiter();
		eventBus.register(this);
	}

	@Override
	protected void onStop() {
		eventBus.unregister(this);
		super.onStop();
	}


	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		String action = intent.getAction();
		if (action == null) {
			return;
		}
		switch (action) {
			case Consts.WEB_SERVER_CLOSE_ACTION:
				stopService(new Intent(this, WebServerService.class));
				break;
			case Consts.USB_HID_TERMINAL_CLOSE_ACTION:
				stopService(new Intent(this, SocketService.class));
				stopService(new Intent(this, WebServerService.class));
				stopService(new Intent(this, USBHIDService.class));
				((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(Consts.USB_HID_TERMINAL_NOTIFICATION);
				finish();
				break;
			case Consts.SOCKET_SERVER_CLOSE_ACTION:
				stopService(new Intent(this, SocketService.class));
				sharedPreferences.edit().putBoolean("enable_socket_server", false).apply();
				break;
		}
	}

	private void setDelimiter() {
		settingsDelimiter = sharedPreferences.getString(Consts.DELIMITER, Consts.DELIMITER_NEW_LINE);
		if (settingsDelimiter != null) {
			if (settingsDelimiter.equals(Consts.DELIMITER_NONE)) {
				delimiter = "";
			} else if (settingsDelimiter.equals(Consts.DELIMITER_NEW_LINE)) {
				delimiter = Consts.NEW_LINE;
			} else if (settingsDelimiter.equals(Consts.DELIMITER_SPACE)) {
				delimiter = Consts.SPACE;
			}
		}
		usbService.setAction(Consts.RECEIVE_DATA_FORMAT);
		usbService.putExtra(Consts.RECEIVE_DATA_FORMAT, receiveDataFormat);
		usbService.putExtra(Consts.DELIMITER, delimiter);
		startService(usbService);
	}

}