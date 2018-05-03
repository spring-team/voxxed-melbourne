package com.example.excelsior;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootApplication
public class ExcelsiorApplication {

		private final File file = new File(".");

		public static void main(String[] args) {
				SpringApplication.run(ExcelsiorApplication.class, args);
		}
}

@Component
class DoctorRunner implements ApplicationRunner {

		private final DoctorRepository doctorRepository;

		DoctorRunner(DoctorRepository doctorRepository) {
				this.doctorRepository = doctorRepository;
		}

		@Override
		public void run(ApplicationArguments applicationArguments) throws Exception {

				Stream.of("Dr. Johnson", "Dr. Syer", "Dr. Pollack", "Dr. De Volder", "Dr. Strange", "Dr. Seuss", "Dr. Who")
					.forEach(name -> this.doctorRepository.save(new Doctor(null, name)));

				this.doctorRepository.findAll().forEach(System.out::println);
		}
}

@RestController
class DoctorRestController {

		private final DoctorRepository doctorRepository;

		DoctorRestController(DoctorRepository doctorRepository) {
				this.doctorRepository = doctorRepository;
		}

		@GetMapping("/")
		Map<String, String> hi() {
				return Collections.singletonMap("greeting", "Hello, world");
		}

		@GetMapping("/doctors")
		Collection<Doctor> doctors() {
				return this.doctorRepository.findAll();
		}
}

interface DoctorRepository extends JpaRepository<Doctor, Long> {
}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Doctor {
		@Id
		@GeneratedValue
		private Long id;
		private String name;
}