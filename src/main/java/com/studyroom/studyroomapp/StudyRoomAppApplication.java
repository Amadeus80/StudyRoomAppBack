package com.studyroom.studyroomapp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.studyroom.studyroomapp.models.entity.Horario;
import com.studyroom.studyroomapp.models.entity.Reserva;
import com.studyroom.studyroomapp.models.entity.ReservaPK;
import com.studyroom.studyroomapp.models.service.AsientoService;
import com.studyroom.studyroomapp.models.service.HorarioService;
import com.studyroom.studyroomapp.models.service.ReservaService;
import com.studyroom.studyroomapp.models.service.UsuarioService;

@SpringBootApplication
public class StudyRoomAppApplication implements CommandLineRunner{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private AsientoService asientoService;

	@Autowired
	private HorarioService horarioService;

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(StudyRoomAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(bCryptPasswordEncoder.encode("password"));

		/* ReservaPK reservaPK1 = ReservaPK.builder().asiento(asientoService.findById((short) 1)).horario(horarioService.findById((short) 1)).fecha(new Date()).build();
		Reserva reserva1 = Reserva.builder().reservaPK(reservaPK1).usuario(usuarioService.findByEmail("pablo@gmail.com")).build();
		reservaService.save(reserva1);

		ReservaPK reservaPK2 = ReservaPK.builder().asiento(asientoService.findById((short) 1)).horario(horarioService.findById((short) 2)).fecha(new Date()).build();
		Reserva reserva2 = Reserva.builder().reservaPK(reservaPK2).usuario(usuarioService.findByEmail("pablo@gmail.com")).build();
		reservaService.save(reserva2); */

		for (Horario horario : horarioService.findAll()) {
			ReservaPK reservaPK = ReservaPK.builder().asiento(asientoService.findById((short) 1)).horario(horario).fecha(new Date()).build();
			Reserva reserva = Reserva.builder().reservaPK(reservaPK).usuario(usuarioService.findByEmail("pablo@gmail.com")).build();
			reservaService.save(reserva);
		}

	}
}
