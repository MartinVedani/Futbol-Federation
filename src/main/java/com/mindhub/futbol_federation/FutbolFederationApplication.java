package com.mindhub.futbol_federation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class FutbolFederationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FutbolFederationApplication.class, args);
    }

    @Bean // le dice a Spring que cree un objeto (sin tabla, para objeto con tabla se usa
          // @Entity)
    public CommandLineRunner initData(ClubRepository clubRepository, JugadorRepository jugadorRepository,
            TecnicoRepository tecnicoRepository, PatrocinadorRepository patrocinadorRepository,
            ClubPatrocinadorRepository clubPatrocinadorRepository) {
        return args -> {
            // Clubes
            Club barcelona = new Club("FC Barcelona", Pais.ESPAÑA);
            Club newells = new Club("Newell's old boys", Pais.ARGENTINA);
            Club boca = new Club("Boca Juniors", Pais.ARGENTINA);
            Club river = new Club("River Plate", Pais.ARGENTINA);
            Club manchesterCity = new Club("Manchester City", Pais.INGLATERRA);
            Club manchesterUnited = new Club("Manchester United", Pais.INGLATERRA);
            Club westham = new Club("West Ham United", Pais.INGLATERRA);
            Club juventus = new Club("Juventus", Pais.ITALIA);
            Club shenhua = new Club("Shanghái Shenhua", Pais.CHINA);
            Club corinthians = new Club("Corinthians", Pais.BRASIL);

            // Jugadores
            Jugador messi = new Jugador("Leonel", "Messi", LocalDate.parse("1987-06-24"), 10, barcelona, Posicion.DEL);
            Jugador suarez = new Jugador("Luis", "Suárez", LocalDate.parse("1987-01-24"), 9, barcelona, Posicion.DEL);

            barcelona.getJugadores().add(messi);
            barcelona.getJugadores().add(suarez);

            Jugador tevez = new Jugador("Carlos", "Tévez", LocalDate.parse("1984-02-05"), 10, boca, Posicion.DEL);
            Jugador abila = new Jugador("Ramón", "Ábila", LocalDate.parse("1989-10-14"), 9, boca, Posicion.DEL);
            Jugador andrada = new Jugador("Esteban", "Andrada", LocalDate.parse("1991-01-26"), 1, boca, Posicion.ARQ);
            Jugador mas = new Jugador("Emmanuel", "Mas", LocalDate.parse("1989-01-15"), 3, boca, Posicion.DEF);
            Jugador salvio = new Jugador("Eduardo", "Salvio", LocalDate.parse("1990-07-13"), 11, boca, Posicion.MED);

            boca.getJugadores().add(tevez);
            boca.getJugadores().add(abila);
            boca.getJugadores().add(andrada);
            boca.getJugadores().add(mas);
            boca.getJugadores().add(salvio);

            // Técnicos
            Tecnico setien = new Tecnico("Quique", "Setién", LocalDate.parse("1958-09-27"), barcelona);
            Tecnico russo = new Tecnico("Miguel Angel", "Russo", LocalDate.parse("1958-09-27"), boca);
            Tecnico gallardo = new Tecnico("Marcelo", "Gallardo", LocalDate.parse("1958-09-27"), river);
            Tecnico kudelka = new Tecnico("Frank Darío", "Kudelka", LocalDate.parse("1958-09-27"), newells);

            // Imprimir en consola el método abstracto de Persona, modificado por Jugador y
            // Tecnico
            System.out.println(messi.saludar()); // Messi saluda
            System.out.println(messi.presentarse()); // Messi se presenta
            System.out.println(setien.presentarse()); // Setien se presenta

            /////////////////////////////////////////////////////////////////////////////////
            // Probamos métodos de Java Básicos //
            /////////////////////////////////////////////////////////////////////////////////
            System.out.println("-----------Probando métodos de Java Básicos --------------------");

            System.out.println("-----------Jugadores del Barca --------------------");
            barcelona.getJugadores().forEach(jugador -> System.out.println(jugador.nombreCompleto()));

            // Calalunya se separa de España, Messi se vuelve a la Argentina.
            barcelona.setPais(Pais.CATALUNYA);

            System.out.println("----------------Pase --------------------");
            // Probamos método estático "traspaso" de la clase Club
            Club.traspaso(messi, barcelona, newells); // se hace el pase
            System.out.println(messi.presentarse()); // Messi se presenta otra vez

            System.out.println("-----------Jugadores del Barca --------------------");
            barcelona.getJugadores().forEach(jugador -> System.out.println(jugador.nombreCompleto()));

            System.out.println("-----------Jugadores del Newell's --------------------");
            newells.getJugadores().forEach(jugador -> System.out.println(jugador.nombreCompleto()));

            // vuelven las cosas a la normalidad, Messi no dice nada.
            barcelona.setPais(Pais.ESPAÑA);
            Club.traspaso(messi, newells, barcelona);

            System.out.println("----Fin de la prueba de métodos de Java Básicos ----------");
            /////////////////////////////////////////////////////////////////////////////////
            // Fin de la prueba de métodos de Java Básicos //
            /////////////////////////////////////////////////////////////////////////////////

            clubRepository.save(barcelona);
            clubRepository.save(boca);
            clubRepository.save(river);
            clubRepository.save(newells);
            clubRepository.save(manchesterCity);
            clubRepository.save(manchesterUnited);
            clubRepository.save(westham);
            clubRepository.save(juventus);
            clubRepository.save(shenhua);
            clubRepository.save(corinthians);

            tecnicoRepository.save(setien);
            tecnicoRepository.save(russo);
            tecnicoRepository.save(gallardo);
            tecnicoRepository.save(kudelka);

            // Creamos el historial de Tevez para el @ElementCollection de la clase Jugador
            tevez.addClubAnterior(boca);
            tevez.addClubAnterior(corinthians);
            tevez.addClubAnterior(westham);
            tevez.addClubAnterior(manchesterCity);
            tevez.addClubAnterior(manchesterUnited);
            tevez.addClubAnterior(juventus);
            tevez.addClubAnterior(shenhua);

            jugadorRepository.save(tevez);

            Patrocinador patrocinador1 = new Patrocinador("Qatar Airways");
            Patrocinador patrocinador2 = new Patrocinador("AXION energy");
            Patrocinador patrocinador3 = new Patrocinador("AXE");

            patrocinadorRepository.save(patrocinador1);
            patrocinadorRepository.save(patrocinador2);
            patrocinadorRepository.save(patrocinador3);

            ClubPatrocinador clubPatrocinador1 = new ClubPatrocinador(boca, patrocinador1);
            ClubPatrocinador clubPatrocinador2 = new ClubPatrocinador(boca, patrocinador2);
            ClubPatrocinador clubPatrocinador3 = new ClubPatrocinador(boca, patrocinador3);
            ClubPatrocinador clubPatrocinador4 = new ClubPatrocinador(barcelona, patrocinador1);

            clubPatrocinadorRepository.save(clubPatrocinador1);
            clubPatrocinadorRepository.save(clubPatrocinador2);
            clubPatrocinadorRepository.save(clubPatrocinador3);
            clubPatrocinadorRepository.save(clubPatrocinador4);

        };
    }

}
