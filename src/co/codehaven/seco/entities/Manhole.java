package co.codehaven.seco.entities;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.gson.annotations.Expose;

@PersistenceCapable
public class Manhole {

	private static final double MAX_HEIGHT = 400.0f;
	private static final double MIN_HEIGHT = 5.0f;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	@Persistent
	@Expose
	private int id;
	@Persistent
	@Expose
	private double currentHeight;
	@Expose
	private double fillRatio;
	@Persistent
	@Expose
	private String name;
	@Persistent
	@Expose
	private String street;
	@Persistent
	@Expose
	private double latitude;
	@Persistent
	@Expose
	private double longitude;

	@Persistent
	@Expose
	private double dimensionX;
	@Persistent
	@Expose
	private double dimensionY;
	@Persistent
	@Expose
	private double dimensionZ;
	
	@Persistent
	@Expose
	private int gasState;
	@Persistent
	@Expose
	private long lastManteinance;
	@Persistent
	@Expose
	private long lastUpdated;

	@Expose
	private int volumeState;

	public void update(Manhole other) {
		this.currentHeight = other.currentHeight;
		this.gasState = other.gasState;
		this.lastUpdated = other.lastUpdated;
		this.lastManteinance = other.lastManteinance;
	}

	public Manhole() {

	}

	public double getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(double value) {
		if (value >= MAX_HEIGHT)
			currentHeight = MAX_HEIGHT;
		else if (value <= MIN_HEIGHT)
			currentHeight = MIN_HEIGHT;
		else
			currentHeight = value;
		lastUpdated = System.currentTimeMillis() / 1000;
	}

	public void recalculate() {
		fillRatio = (dimensionZ - currentHeight) / dimensionZ;

		if (fillRatio <= 0.50f)
			volumeState = 0;
		else if (fillRatio <= 0.80f)
			volumeState = 1;
		else
			volumeState = 2;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDimensionX() {
		return dimensionX;
	}

	public void setDimensionX(double dimensionX) {
		this.dimensionX = dimensionX;
	}

	public double getDimensionY() {
		return dimensionY;
	}

	public void setDimensionY(double dimensionY) {
		this.dimensionY = dimensionY;
	}

	public double getDimensionZ() {
		return dimensionZ;
	}

	public void setDimensionZ(double dimensionZ) {
		this.dimensionZ = dimensionZ;
	}

	public long getLastManteinance() {
		return lastManteinance;
	}

	public void setLastManteinance(long lastManteinance) {
		this.lastManteinance = lastManteinance;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public int getGasState() {
		return gasState;
	}

	public void setGasState(int gasState) {
		this.gasState = gasState;
	}

	public int getVolumeState() {
		return volumeState;
	}

	public void setVolumeState(int volumeState) {
		this.volumeState = volumeState;
	}

	

}