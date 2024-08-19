package hotel.Services;


import org.springframework.data.repository.CrudRepository;

import hotel.Model.Position;




public interface IChucvuSercives extends CrudRepository<Position, String> {
	
}
