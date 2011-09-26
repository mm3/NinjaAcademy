package com.efzgames.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.efzgames.framework.gl.SpriteBatcher;
import com.efzgames.framework.gl.Texture;
import com.efzgames.framework.gl.TextureRegion;
import com.efzgames.framework.impl.GLGraphics;

public class SpriteText {

	private int height;
	private int width;
	private Texture texture;
	private TextureRegion textureRegion;

	public SpriteText(GLGame game, String text, Typeface font, int size, int color, int shadowcolor) {
		Paint textPaint = new Paint();
		textPaint.setShadowLayer(0.2f, 5, 0, shadowcolor);
		create(game, text, font, size, color, textPaint);
	}
	
	public SpriteText(GLGame game, String text, Typeface font, int size, int color) {
		Paint textPaint = new Paint();
		create(game, text, font, size, color, textPaint);
	}

	private void create(GLGame game, String text, Typeface font, int size,
			int color, Paint textPaint) {
		textPaint.setTypeface(font);
		textPaint.setTextSize(size);
		textPaint.setAntiAlias(true);
		textPaint.setColor(color);

		Rect bounds = new Rect();
		textPaint.getTextBounds(text, 0, text.length(), bounds);
		
		int padding =5;
		int descent = (int) Math.ceil(textPaint.descent());
		int ascent = (int) Math.ceil(-textPaint.ascent());
		width = (int) Math.ceil(textPaint.measureText(text)) + 2*padding;
		height = ascent + descent+2*padding;
		
		// Create an empty, mutable bitmap
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);

		// draw the text centered
		canvas.drawText(text, padding, ascent, textPaint);

		texture = new Texture(game.getGLGraphics(), bitmap);
		textureRegion = new TextureRegion(texture, 0, 0, width, height);
	}

	public void reload() {
		texture.reload();
	}
	
	public void draw(SpriteBatcher batcher,float x, float y){
		batcher.beginBatch(texture);
		batcher.drawSprite(x, y, width, height, textureRegion);
		batcher.endBatch();
	}
}