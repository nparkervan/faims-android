package au.org.intersect.faims.android.ui.map.tools;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import au.org.intersect.faims.android.log.FLog;
import au.org.intersect.faims.android.ui.form.MapToggleButton;
import au.org.intersect.faims.android.ui.map.CustomMapView;

public class EditTool extends SelectTool {
	
	public static final String NAME = "Edit";
	
	private MapToggleButton lockButton;
	
	public EditTool(Context context, CustomMapView mapView) {
		super(context, mapView, NAME);
		
		lockButton = createLockButton(context);
		updateLockButton();
		
		updateLayout();
	}
	
	@Override
	public void activate() {
		clearLock();
		super.activate();
	}
	
	@Override
	public void deactivate() {
		clearLock();
		super.activate();
	}
	
	@Override
	public void update() {
		clearLock();
		super.update();
	}
	
	@Override
	protected void updateLayout() {
		super.updateLayout();
		if (lockButton != null) layout.addView(lockButton);
	}
	
	private MapToggleButton createLockButton(final Context context) {
		MapToggleButton button = new MapToggleButton(context);
		button.setChecked(mapView.isDrawViewLocked());
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateLock();
			}
			
		});
		return button;
	}
	
	private void updateLockButton() {
		lockButton.setText(lockButton.isChecked() ? "UnLock" : "Lock");
	}
	
	private void updateLock() {
		updateLockButton();
		mapView.setDrawViewLock(lockButton.isChecked());
		
		try {
			if (lockButton.isChecked()) {
				mapView.prepareSelectionTransform();		
			} else {
				mapView.doSelectionTransform();
			}
		} catch (Exception e) {
			FLog.e("error doing seleciton transform", e);
			showError("Error doing selection transform");
		}
	}
	
	private void clearLock() {
		lockButton.setChecked(false);
		updateLockButton();
		mapView.setDrawViewLock(false);
		mapView.clearSelectionTransform();
	}
}
