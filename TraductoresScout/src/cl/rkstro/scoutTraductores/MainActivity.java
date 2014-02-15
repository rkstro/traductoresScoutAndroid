package cl.rkstro.scoutTraductores;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner; 
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
	private EditText inputText;
	private EasyTracker tracker;
	private Boolean fromShare;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fromShare = false;
		inputText = (EditText)findViewById(R.id.textForTranslate);
		
		/* Receive the Intent */
		Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();
	    Log.d("INTENT", Intent.ACTION_SEND+"-"+action);
	    Log.d("INTENT", type!=null?type:"null");
	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if ("text/plain".equals(type)) {
	        	String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	        	Log.d("INTENT", sharedText!=null?sharedText:"no_hay");
	        	if (sharedText != null) {
	        		inputText.setText(sharedText);
	        		fromShare = true;
	            }
	        }
	    }
		
				
		final Spinner selectorTranslate = (Spinner)findViewById(R.id.selectorTranslate);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_traductores, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selectorTranslate.setAdapter(adapter);
		
		Button goTranslate = (Button) findViewById(R.id.button_translate);
		goTranslate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String input     = inputText.getText().toString();
				String traductor = (String)selectorTranslate.getSelectedItem();
				String output    = null;
				
				if(traductor.equals("A Morse"))
					output = ScoutTraductores.morse(input, "encode");
				else if(traductor.equals("Desde Morse"))
					output = ScoutTraductores.morse(input, "decode");
				else if(traductor.equals("Semafora"))
					output = input;
				else if(traductor.equals("Gato"))
					output = input;
				else if(traductor.equals("Cenit Polar"))
					output = ScoutTraductores.cenit(input);
				else if(traductor.equals("Murcielago"))
					output = ScoutTraductores.murcielago(input);
				else if(traductor.equals("Romana"))
					output = ScoutTraductores.romana(input);
				else if(traductor.equals("+1"))
					output = ScoutTraductores.plusOne(input);
				else if(traductor.equals("-1"))
					output = ScoutTraductores.minusOne(input);
				else if(traductor.equals("Sufamelico"))
					output = ScoutTraductores.sufamelico(input);
				else if(traductor.equals("Neumatico"))
					output = ScoutTraductores.neumatico(input);
				else if(traductor.equals("Baden Powell"))
					output = ScoutTraductores.badenPowell(input);
				else if(traductor.equals("Agujerito"))
					output = ScoutTraductores.agujerito(input);
				
				
				if(!traductor.equals("Seleccionar Traductor")){
					
					if(tracker!=null)
						tracker.send(MapBuilder
							      .createEvent("Boton",      // Event category (required)
							                   "Traduccion", // Event action (required)
							                   traductor,    // Event label
							                   null)         // Event value
							      .build()
							  );
					
					Intent goTranslateIntent = new Intent(getApplicationContext(), TranslatedActivity.class);
					goTranslateIntent.putExtra("traductor", traductor);
					goTranslateIntent.putExtra("output", output);
					MainActivity.this.startActivity(goTranslateIntent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onPause(){
		super.onPause();
		if(!fromShare){
			SharedPreferences.Editor editor = getPreferences(0).edit();
			editor.putString("form_text", inputText.getText().toString());
			editor.putInt("selection-start", inputText.getSelectionStart());
	        editor.putInt("selection-end", inputText.getSelectionEnd());
			editor.commit();
		}
	}
	
	protected void onResume(){
		super.onResume();
		if(!fromShare){
			SharedPreferences prefs = getPreferences(0); 
			String restoredText = prefs.getString("form_text", null);
			if (restoredText != null) {
	            inputText.setText(restoredText, TextView.BufferType.EDITABLE);
	            int selectionStart = prefs.getInt("selection-start", -1);
	            int selectionEnd = prefs.getInt("selection-end", -1);
	            if (selectionStart != -1 && selectionEnd != -1) {
	            	inputText.setSelection(selectionStart, selectionEnd);
	            }
			}
		}
	}
	
	@Override
	public void onStart() {
	  super.onStart();
	  tracker = EasyTracker.getInstance(this);
	  tracker.activityStart(this);
	}
	
	@Override
	public void onStop() {
	  super.onStop();
	  if(tracker==null)
		  tracker = EasyTracker.getInstance(this);
	  tracker.activityStop(this);
	}
	
	public EasyTracker getTracker(){
		return tracker;
	}
	
}
