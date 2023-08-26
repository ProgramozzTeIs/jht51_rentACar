package pti.rent_a_car_mvc.persistence;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import pti.rent_a_car_mvc.model.Car;

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
	
}