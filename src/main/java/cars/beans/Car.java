package cars.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Cars")
@SequenceGenerator(name="car_generator", initialValue=1000, allocationSize=100)
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_generator")
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	private String make;
	private String model;
	private int year;
	
	public Car() {}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return id + ": " + year + " " + make + " " + model;
	}

}
