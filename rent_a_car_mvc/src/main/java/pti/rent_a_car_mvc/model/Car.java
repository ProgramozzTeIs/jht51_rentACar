package pti.rent_a_car_mvc.model;

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
	
	@Lob
	@Column(name="picture")
	private byte[] picture;

	
	
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
	
	public byte[] getPicture() {
		return picture;
	}
	
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	
	public String getBase64PictureString() {
		
		String base64String = null;
		
		if(picture != null) {
			base64String = new String(picture);
		}
		
		return base64String;
	}

	
	@Override
	public String toString() {
		return "Car [id=" + id + ", type=" + type + ", available=" + available + ", price=" + price + "]";
	}

}
