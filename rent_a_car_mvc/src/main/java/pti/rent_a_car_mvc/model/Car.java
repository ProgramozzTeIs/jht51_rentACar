package pti.rent_a_car_mvc.model;

import org.apache.tomcat.util.codec.binary.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="cars")
public class Car {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="available")
	private boolean available;
	
	@Column(name="price")
	private int price;
	
	@Column(name="picture")
	private String picture;

	public Car() {}
	
	public Car(int id, String type, boolean available, int price, byte[] rawImageData) {
		
		this.id = id;
		this.type = type;
		this.available = available;
		this.price = price;
		
		this.picture = Base64.encodeBase64String(rawImageData);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
	public void encodeRawImageToBase64(byte[] rawImageData) {
		
		this.picture = Base64.encodeBase64String(rawImageData);
	}
	
	@Override
	public String toString() {
		return "Car [id=" + id + ", type=" + type + ", available=" + available + ", price=" + price + "]";
	}

}
