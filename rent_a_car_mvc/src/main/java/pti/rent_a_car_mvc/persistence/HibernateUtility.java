package pti.rent_a_car_mvc.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtility {
	
	private final SessionFactory factory;
	
	public HibernateUtility() {
		
		this.factory = (new Configuration()).configure().buildSessionFactory();
	}
	
	public Session getSession() {
		
		return factory.openSession();
	}
}
