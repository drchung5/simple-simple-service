package cars.persisence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cars.beans.Car;

@Service
public interface CarRepository extends CrudRepository<Car, Integer> {
	
}
