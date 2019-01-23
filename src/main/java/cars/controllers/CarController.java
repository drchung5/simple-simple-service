package cars.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cars.beans.Car;
import cars.beans.Make;
import cars.persisence.CarRepository;

@RestController
public class CarController {
	
	@Autowired
	CarRepository carRepository;
	
	@PostMapping(value="/cars")
	public void add( @RequestBody Car car, HttpServletResponse response) {
		
		int id = car.getId();
		
		if( id == 0 ) {
		
			car = carRepository.save(car);
			
			System.out.println("POST: (insert) "+car);
			
			response.addHeader("Location", "/cars/"+car.getId());
			response.setStatus(HttpServletResponse.SC_CREATED);
			
		} else if ( id > 0 ) {
			
			System.out.println("POST: (update) "+car);
			
			if( carRepository.existsById(id)) {

				car = carRepository.save(car);
				response.addHeader("Location", "/cars/"+car.getId());
				response.setStatus(HttpServletResponse.SC_OK);
				
			} else {
				
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				
			}
			
		} else {

			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			
		}
	    
	}
	
	@GetMapping( value = "/cars")
	@ResponseBody 
	public Iterable<Car> getCars(
			@RequestParam("make") Optional<String> make,
			@RequestParam("model") Optional<String> model,
			@RequestParam("year") Optional<Integer> year,
			HttpServletResponse response
			) {
		
		Iterable<Car> cars = null;
		
		boolean isMake  = make.isPresent();
		boolean isModel = model.isPresent();
		boolean isYear  = year.isPresent();
		
		if( isMake && isModel && isYear ) {
			cars = carRepository.findByMakeModelYear(make.get(), model.get(), year.get());
		} else if ( isMake && isModel && !isYear) {
			cars = carRepository.findByMakeModel(make.get(), model.get());
		} else if ( isMake && !isModel && isYear) {
			cars = carRepository.findByMakeYear(make.get(), year.get());
		} else if ( isMake && !isModel && !isYear) {
			cars = carRepository.findByMake(make.get());
		} else if ( !isMake && isModel && isYear) {
			cars = carRepository.findByModelYear(model.get(), year.get());
		} else if ( !isMake && !isModel && isYear) {
			cars = carRepository.findByYear(year.get());
		} else if ( !isMake && isModel && !isYear) {
			cars = carRepository.findByModel(model.get());
		} else if ( !isMake && !isModel && !isYear) {
			cars =  carRepository.findAll();
		}
		
//		Car car = new Car();
//        if(make.isPresent())  car.setMake(make.get());
//        if(model.isPresent()) car.setModel(model.get());
//        if(year.isPresent())  car.setYear(year.get());
		
		if( cars.iterator().hasNext() ) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		return cars;
//
//		String searchMake  = make.isPresent() ? make.get() : null;
//		String searchModel = model.isPresent() ? model.get() : null;
//		Integer searchYear = year.isPresent() ? year.get() : null;
//		
//		Vector<Car> localCars = new Vector<Car>();
//		
//		for( Car car: cars ) {
//			if( searchMake != null && !car.getMake().equalsIgnoreCase(searchMake)) {
//				break;
//			}
//			if( searchModel != null && !car.getModel().equalsIgnoreCase(searchMake)) {
//				break;
//			}
//			if( searchYear != null && !(car.getYear() == searchYear)) {
//				break;
//			}
//			localCars.add(car);
//		}
//
//		if ( localCars.isEmpty() ) {
//			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//			localCars = null;
//		}
//		
//		return localCars;
//	
		
//		return null;
	}

	@GetMapping(value="/cars/{id}")
	@ResponseBody
	public Car getCarById(@PathVariable("id") int id, HttpServletResponse response) {

		Optional<Car> optionalCar = carRepository.findById(id);
		
		Car car = null;
		int status = HttpServletResponse.SC_NOT_FOUND;
		
		if(optionalCar.isPresent()) {
			car = optionalCar.get();
			status = HttpServletResponse.SC_OK;
		}

		response.setStatus(status);
		return car;
		
	}

	@GetMapping(value="/cars/{id}/make")
	@ResponseBody
	public Make getCarMake(@PathVariable("id") int id, HttpServletResponse response) {

		Optional<Car> optionalCar = carRepository.findById(id);
		
		Make make = new Make();
		
		int status = HttpServletResponse.SC_NOT_FOUND;
		
		if(optionalCar.isPresent()) {
			make.setMake(optionalCar.get().getMake());
			status = HttpServletResponse.SC_OK;
		}

		response.setStatus(status);
		return make;
	}


	@DeleteMapping(value="/cars/{id}")
	public void deleteCar( @PathVariable("id") int id, HttpServletResponse response) {

		int status = HttpServletResponse.SC_NOT_FOUND;
		
		if( carRepository.existsById(id) ) {
			carRepository.deleteById(id);
			status = HttpServletResponse.SC_OK;
		}
		
	    response.setStatus(status);
	    
	}

//	@RequestMapping(value="/cars/{id}", method=RequestMethod.PUT)
//	public void putCar(
//			@PathVariable("id") int id, 
//			@RequestBody Car car, 
//			HttpServletResponse response) {
//
//		
//		System.out.println("put("+id+")" + car);
//
//		boolean carUpdated = false;
//		
//		car.setId(id);
//		
//		for( Car c : cars ) {
//			if( c.getId() == id ) {
//				cars.remove(c);
//				cars.add(car);
//			    response.setStatus(HttpServletResponse.SC_OK);
//			    carUpdated = true;
// 			}
//		}
//		
//		if( !carUpdated ) {
//			cars.add(car);
//		    response.setStatus(HttpServletResponse.SC_CREATED);		
//		    response.setHeader("Location", "/cars/"+car.getId());
//		}  
//	}
//	
	@PatchMapping(value="/cars/{id}")
	public void patchCar(
			@PathVariable("id") int id, 
			@RequestBody Car car, 
			HttpServletResponse response) {

		Optional<Car> optionalCar = carRepository.findById(id);
		
		int status = HttpServletResponse.SC_NOT_FOUND;
		
		if(optionalCar.isPresent()) {
			Car theCar = optionalCar.get();
			
			if( car.getMake() != null ) theCar.setMake(car.getMake());
			if( car.getModel() != null ) theCar.setModel(car.getModel());
			if( car.getYear() > 0 ) theCar.setYear(car.getYear());
			
			carRepository.save(theCar);
			
			status = HttpServletResponse.SC_OK;
		}

		response.setStatus(status);
		
	}
}
