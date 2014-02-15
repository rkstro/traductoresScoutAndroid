package cl.rkstro.scoutTraductores;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;

public class TranslatedActivity extends ActionBarActivity {
	
	private ShareActionProvider mShareActionProvider;
	private String translated;
	private String traductor;
	private EasyTracker tracker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translated);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Get intent Data
		Bundle getData = getIntent().getExtras();
		translated = getData.getString("output");
		traductor = getData.getString("traductor");
		
		getSupportActionBar().setSubtitle(traductor);
				
		TextView translatedView = (TextView)findViewById(R.id.text_translated);
		translatedView.setText(translated);
		
		if(traductor.equals("Semafora")){
			Typeface font = Typeface.createFromAsset(getAssets(), "Semaphore.ttf");  
			translatedView.setTypeface(font);
			translatedView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60);
		} else if(traductor.equals("Gato")){
			Typeface font = Typeface.createFromAsset(getAssets(), "ClaveRejilla.ttf");  
			translatedView.setTypeface(font);
			translatedView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.translated, menu);
		
	    MenuItem item = menu.findItem(R.id.menu_item_share);
	    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
	    
	    //Set intent to share
	    Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, translated);
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, translated);
        setShareIntent(shareIntent);
	    
        //If translate is graphic its disabled
        if(traductor.equals("Semafora") || traductor.equals("Gato")){
        	item.setVisible(false);
        }
        
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_item_share:
			if(tracker!=null)
				tracker.send(MapBuilder
					      .createEvent("Boton",     // Event category (required)
					                   "Compartir", // Event action (required)
					                   null,        // Event label
					                   null)        // Event value
					      .build()
					  );
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Call to update the share intent
	private void setShareIntent(Intent shareIntent) {
	    if (mShareActionProvider != null) {
	        mShareActionProvider.setShareIntent(shareIntent);
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
