package pti.rent_a_car_mvc.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="reservations")
public class Reservation {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="user_name")
	private String user_name;
	
	@Column(name="user_email")
	private String user_email;
	
	@Column(name="user_phone")
	private String user_phone;
	
	@Column(name="user_address")
	private String user_address;
	
	@Column(name="start_date")
	private LocalDate start_date;
	
	@Column(name="end_date")
	private LocalDate end_date;
	
	@OneToOne
	@JoinColumn(name="car_id", referencedColumnName="id")
	private Car car;
	
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getUser_email() {
		return user_email;
	}
	
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	public String getUser_phone() {
		return user_phone;
	}
	
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	public String getUser_address() {
		return user_address;
	}
	
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	
	public LocalDate getStart_date() {
		return start_date;
	}
	
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	
	public LocalDate getEnd_date() {
		return end_date;
	}
	
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", user_name=" + user_name + ", user_email=" + user_email + ", user_phone="
				+ user_phone + ", user_address=" + user_address + ", start_date=" + start_date + ", end_date="
				+ end_date + ", car=" + car + "]";
	}


}
