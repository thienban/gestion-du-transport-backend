package gdp.api.entities;

import com.google.maps.model.Distance;
import com.google.maps.model.Duration;

public class TrajetInfo {
	private Distance distance;

	private Duration duration;

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	/**
	 * @return the distance
	 */
	public Distance getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(Distance distance) {
		this.distance = distance;
	}

}
