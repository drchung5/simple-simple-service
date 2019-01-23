package cars.persisence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import cars.beans.Car;

@Service
public interface CarRepository extends CrudRepository<Car, Integer> {

	@Query("SELECT car FROM Car car WHERE car.make = :make AND car.model = :model AND car.year = :year")	
	public List<Car> findByMakeModelYear(@Param("make") String make, @Param("model") String model, @Param("year") int year);  

	@Query("SELECT car FROM Car car WHERE car.make = :make AND car.model = :model")	
	public List<Car> findByMakeModel(@Param("make") String make, @Param("model") String model);  
	
	@Query("SELECT car FROM Car car WHERE car.make = :make AND car.year = :year")	
	public List<Car> findByMakeYear(@Param("make") String make, @Param("year") int year);  
	
	@Query("SELECT car FROM Car car WHERE car.make = :make")	
	public List<Car> findByMake(@Param("make")String make);  
	
	@Query("SELECT car FROM Car car WHERE car.model = :model AND car.year = :year")	
	public List<Car> findByModelYear(@Param("model") String model, @Param("year") int year);  
	
	@Query("SELECT car FROM Car car WHERE car.year = :year")	
	public List<Car> findByYear(@Param("year") int year);  
	
	@Query("SELECT car FROM Car car WHERE car.model = :model")	
	public List<Car> findByModel(@Param("model") String model);  
  
}
