package net.jhdt.videomonitor.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextTest extends EditText {

	public EditTextTest(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public EditTextTest(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public EditTextTest(Context context) {
		super(context);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(Color.GRAY);
		canvas.drawText("�������ı���", 10, getHeight() / 2 + 5, paint);
		super.onDraw(canvas);
	}
}
