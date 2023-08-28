package pti.rent_a_car_mvc.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pti.rent_a_car_mvc.model.Car;
import pti.rent_a_car_mvc.model.Reservation;
import pti.rent_a_car_mvc.model.dto.CarReservedTimesDto;

@Repository
public class Database {
	
	public final HibernateUtility dbUtil;
	
	@Autowired
	public Database(HibernateUtility dbUtil) {
		
		this.dbUtil = dbUtil;
	}
	
	public Car findCarById(int id) {
		
		Session session = dbUtil.getSession();
		
		Car resultCar = session.get(Car.class, id);
		
		session.close();
		
		return resultCar;
	}
	
	public List<Car> findAvailableCars(LocalDate startDate, LocalDate endDate) {
		
		Session unavailableCarsSession = dbUtil.getSession();
		
		NativeQuery<Integer> unavailableCarIdsQuery = unavailableCarsSession
				.createNativeQuery("SELECT car_id FROM reservations WHERE "
								 + "(start_date BETWEEN :startDate AND :endDate) "
								 + "OR "
								 + "(end_date BETWEEN :startDate AND :endDate)"
								 + "OR "
								 + "(:startDate BETWEEN start_date AND end_date) "
								 + "OR "
								 + "(:endDate BETWEEN start_date AND end_date)",
								 Integer.class);
		
		unavailableCarIdsQuery.setParameter("startDate", startDate);
		unavailableCarIdsQuery.setParameter("endDate", endDate);
		
		List<Integer> unavailableCarIds = unavailableCarIdsQuery.getResultList();
		
		unavailableCarsSession.close();
		
		Session availableCarsSession = dbUtil.getSession();
		
		Query<Car> availableCarsQuery = null;
		
		if(!unavailableCarIds.isEmpty()) {
			
			availableCarsQuery = availableCarsSession.createQuery(
					"FROM Car c WHERE c.id NOT IN :unavailableCarIds "
				  + "AND "
				  + "c.available = TRUE",
					Car.class
				);
			availableCarsQuery.setParameter("unavailableCarIds", unavailableCarIds);
		} else {
			
			availableCarsQuery = availableCarsSession.createQuery(
					"FROM Car c WHERE c.available = TRUE",
					Car.class
				);
		}
		
		List<Car> availableCars = availableCarsQuery.getResultList();
		
		availableCarsSession.close();
		
		return availableCars;
	}
	
	
	public void persistReservation(Reservation reservation) {
		
		Session session = dbUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.persist(reservation);
		
		tx.commit();
		session.close();
	}
	

	public boolean peristCar(Car newCar) {
		
		boolean success = false;
		
		Session session = dbUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.persist(newCar);
		
		tx.commit();
		session.close();
		
		if(newCar.getId() != 0) {
			
			success = true;
		}
		
		return success;
	}

	public List<Reservation> getAllReservations() {

		Session session = dbUtil.getSession();
		
		Query<Reservation> q = session.createQuery("FROM Reservation r", Reservation.class);
		List<Reservation> reservations = q.getResultList();
		
		session.close();
		
		return reservations;
	}
	
	public List<CarReservedTimesDto> getOpenReservationsByCarId(int carId) {
		
		List<CarReservedTimesDto> reservationsByCar = new ArrayList<>();
		
		Session session = dbUtil.getSession();
		
		NativeQuery<Object[]> q = session.createNativeQuery(
				"SELECT id, start_date, end_date FROM reservations WHERE car_id = ?1 AND DATE(start_date) >  CURDATE()", 
				Object[].class);
		q.setParameter(1, carId);
		
		List<Object[]> resultSet = q.getResultList();

		for(int i = 0; i < resultSet.size(); i++) {
			
			int reservationId = Integer.parseInt(resultSet.get(i)[0].toString());
			LocalDate startDate = LocalDate.parse(resultSet.get(i)[1].toString());
			LocalDate endDate = LocalDate.parse(resultSet.get(i)[2].toString());
			
			CarReservedTimesDto dto = new CarReservedTimesDto(carId, reservationId, startDate, endDate);
			reservationsByCar.add(dto);
		}

		session.close();
		System.err.println(reservationsByCar);
		return reservationsByCar;
	}
	
	public void updateReservationsCarByResId(int reservationId, int newCarId) {

		Session session = dbUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		MutationQuery q = session.createNativeMutationQuery("UPDATE reservations SET car_id = ?1 WHERE id = ?2");
		q.setParameter(1, newCarId);
		q.setParameter(2, reservationId);
		
		q.executeUpdate();
		
		tx.commit();
		session.close();
	}

	public void deleteReservationById(int reservationId) {

		Session session = dbUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Query<Reservation> q = session.createQuery("DELETE FROM Reservation r WHERE r.id = ?1", Reservation.class);
		q.setParameter(1, reservationId);
		
		q.executeUpdate();
		
		tx.commit();
		session.close();
	}
	
	public List<Car> getAllCars() {

		Session session = dbUtil.getSession();
		
		Query<Car> q = session.createQuery("FROM Car c", Car.class);
		List<Car> cars = q.getResultList();
		
		session.close();
		
		return cars;
	}

	public void mergeCar(Car car) {
		
		Session session = dbUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.merge(car);
		
		tx.commit();
		session.close();
	}

	public void mergeCarWithoutBlob(Car car) {

		Session session = dbUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		MutationQuery q = session.createNativeMutationQuery("UPDATE cars SET type = ?1, available = ?2, price = ?3 WHERE id = ?4");
		q.setParameter(1, car.getType());
		q.setParameter(2, car.isAvailable());
		q.setParameter(3, car.getPrice());
		q.setParameter(4, car.getId());
		
		q.executeUpdate();
		
		tx.commit();
		session.close();
	}




	
}
