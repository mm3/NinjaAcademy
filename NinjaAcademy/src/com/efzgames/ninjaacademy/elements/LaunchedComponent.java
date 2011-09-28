package com.efzgames.ninjaacademy.elements;

import com.efzgames.framework.gl.Animation;
import com.efzgames.framework.gl.SpriteBatcher;
import com.efzgames.framework.gl.Texture;
import com.efzgames.framework.gl.TextureRegion;
import com.efzgames.framework.impl.GLGame;
import com.efzgames.framework.math.Vector2;
import com.efzgames.ninjaacademy.screens.GameScreen;

public class LaunchedComponent extends AnimatedComponent {

	// / Angular velocity in radians per second.
	public float angularVelocity;
	// / Velocity in pixels per second.
	public Vector2 velocity;
	// / Acceleration in pixels per squared second.
	public Vector2 acceleration;
	// // The component's rotation in radians.
	public float rotation;

	boolean isEventFired = false;

	public float notifyHeight;

	public EventHandler droppedPastHeight;

	public LaunchedComponent(GLGame glGame, GameScreen gameScreen,
			Texture texture, TextureRegion textureRegion) {
		super(glGame, gameScreen, texture, textureRegion);

	}
	
	public LaunchedComponent(GLGame glGame, GameScreen gameScreen,
			Animation animation) {
		super(glGame, gameScreen, animation);

	}

	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		float elapsedSeconds = deltaTime;
		velocity = velocity.add(Vector2.mul(acceleration, elapsedSeconds));
		position = position.add(Vector2.mul(velocity, elapsedSeconds));

		// Check whether the event generated by falling past a certain point
		// needs to be fired.
		if (!isEventFired && position.y < notifyHeight && velocity.y < 0) {
			if (droppedPastHeight != null) {
				droppedPastHeight.onEvent(this);
			}

			isEventFired = true;
		}

		rotation += angularVelocity * elapsedSeconds;
	}

	@Override
	public void present(float deltaTime, SpriteBatcher batcher) {
		
		batcher.beginBatch(texture);
		animation.present(deltaTime, batcher, position, rotation, visualCenter);
		batcher.endBatch();
	}
	
	public void Launch(Vector2 initialPosition, Vector2 initialVelocity, Vector2 acceleration, 
            float angularVelocity)
    {
            Launch(initialPosition, initialVelocity, acceleration, 0, angularVelocity);
    }

	public void Launch(Vector2 initialPosition, Vector2 initialVelocity,
			Vector2 acceleration, float initialRotation, float angularVelocity) {
		position = initialPosition;
		velocity = initialVelocity;
		rotation = initialRotation;
		this.acceleration = acceleration;
		this.angularVelocity = angularVelocity;
		isEventFired = false;
	}
	
}
