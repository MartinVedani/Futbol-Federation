package com.mindhub.futbol_federation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

// @Entity le dice a Spring que cree una tabla (incluyendo el objeto necesario) llamada Jugador
// en la base de datos. La palabra extends indica que la clase Jugador hereda los atributos y
// métodos de la clase Persona.
@Entity
public class Jugador extends Persona {
    private int numero;
    @ManyToOne // muchos jugadores pueden tener un mismo club.
    @JoinColumn(name = "club_id") // el ID lo almacena siempre el "many" and carnaliidad 1:muchos
    private Club club;
    @Enumerated(EnumType.STRING)
    private Posicion posicion;

    @ElementCollection // crea una tabla "trayectoria" sin tener q crear la clase
    @CollectionTable(name = "trayectoria")
    private List<Club> clubesAnteriores;

    public Jugador() {
    }

    public Jugador(String nombre, String apellido, LocalDate fecha_nacimiento, int numero, Club club,
            Posicion posicion) {
        // la funcion super() hace referencia al constructor de la clase madre (Persona)
        // y lleva como
        // argumento los datos que deben ser llenados para cumplir con al menos uno los
        // constructores de Persona (porque creamos 2 metodos, uno sin numero de
        // documento).
        super(nombre, apellido, fecha_nacimiento);
        this.numero = numero;
        this.club = club;
        this.posicion = posicion;
        this.clubesAnteriores = new LinkedList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public List<Club> getClubesAnteriores() {
        return clubesAnteriores;
    }

    public void addClubAnterior(Club club) {
        this.clubesAnteriores.add(club);
    }

    // la anotación @Override indica que vamos a sobrescribir un método abstracto de
    // la clase madre (polimorfismo). Es un método obligatorio asi que debemos
    // hacerlo para poder compilar.
    @Override
    public String presentarse() {
        return "Hola! Mi nombre es " + this.nombreCompleto() + " y soy " + this.getPosicion() + " de "
                + this.getClub().getNombre();
    }

    public Map<String, Object> jugadorDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("nombre", this.getNombre());
        dto.put("apellido", this.getApellido());
        dto.put("fecha_nacimiento", this.getFecha_nacimiento());
        return dto;
    }
}
