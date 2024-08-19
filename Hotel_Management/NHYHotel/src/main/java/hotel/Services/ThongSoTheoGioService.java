package hotel.Services;

import org.springframework.data.repository.CrudRepository;

import hotel.Model.HourlyParameters;

public interface ThongSoTheoGioService extends CrudRepository<HourlyParameters, Integer> {

}
