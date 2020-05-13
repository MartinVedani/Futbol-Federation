package com.mindhub.futbol_federation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String nombre;
    @Enumerated(EnumType.STRING) //EnumType le dice a la base q enumere sólamente
    private Pais pais;   // la lista de opciones "string" que contruyen la clase Pais
    @OneToOne(mappedBy = "club")
    private Tecnico tecnico;
    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Jugador> jugadores;
    // "Set<Juagadores>" es un array de elementos de clase Jugadores.
    // Set NO permite que los elementos se puedan repetir.

    // Cardinalidades anteriores: 1 club -> 1 tecnico; 1 club -> muchos jugadores.
    // mappedBy hace referencia a la propiedad "club" definida en las clases Jugador
    // y Tenico. Al ir a estas clases, podemos ver que la propiedad "club" se mapeará a
    // las tablas de Tecnico y Jugador mediante @JoinColumn(name = "club_id").
    // CascadeType.ALL dice que lo que le sucede a club, también le sucede a jugadores.


    // Cardinalidad: many:many
    // 1:many (1 club, muchos patrocinadores acá) + 1:many (1 patrocinador, muhcos clubes en
    // la clase Patrocinador) lo que termina creando una clase y una tabla intermedia llamada
    // Club_Patrocinador que tendra 2 canalidades many:1 con dos @JoinColumn: "club_id" y
    // "patrocinador_id". Spring tiene la relacion many:many pero el programador pierde el
    // control sobre la tabla y la clase intermedia que puede ser mut útil.
    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClubPatrocinador> clubPatrocinadorSet;

    public Club(){}

    public Club(String nombre, Pais pais) {
        this.nombre = nombre;
        this.pais = pais;
        this.jugadores = new HashSet<>();
        //HashSet inicializa un nuevo array. Es el equivalente a "var this.jugadores = []" en js.
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Set<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(Set<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Set<ClubPatrocinador> getClubPatrocinadorSet() {
        return clubPatrocinadorSet;
    }

    public void setClubPatrocinadorSet(Set<ClubPatrocinador> clubPatrocinadorSet) {
        this.clubPatrocinadorSet = clubPatrocinadorSet;
    }

    //Método estático pertenece SÓLO a la clase y NO a los objetos dentro de la clase.
    // Por ejemplo, Barcelona (por más qe es un club) no puede acceder al traspaso.
    // Solo club puede acceder al pase mediante el llamado Club.traspaso
    public static void traspaso(Jugador jugador, Club clubViejo, Club clubNuevo){
        jugador.setClub(clubNuevo);
        clubViejo.setJugadores(
                clubViejo.getJugadores().stream().filter(j -> j != jugador).collect(Collectors.toSet()));
        clubNuevo.getJugadores().add(jugador);
    }
    //stream() es una librería de funciones superiores para hacer loops con: for, filter, map, etc.
    // "j" es cada jugador dentro del array "jugadores". Los jugadores que NO son (!=) el que se
    //traspasa, se agrega a .collect() y luego los "stream coleccionados" (los jugadores que pasaron
    // el filtro, pq != el jugador que traspasa) se agregan como el nuevo array de jugadores del
    // club viejo usando Collector.toSet().
    // Por último, al club nuevo le pido el array de jugadores actuales con .getJugadores()
    // y  le sumo el jugador traspasado con .add() , que es el equivalente de .push() de js

    public Map<String,Object> clubDTO(){
        Map<String,Object> dto = new LinkedHashMap<>();
        dto.put("club", this.getNombre());
        dto.put("pais", this.getPais().toString());
        dto.put("tecnico", this.getTecnico().tecnicoDTO());
        dto.put("jugadores", this.getJugadores().stream().map(Jugador::jugadorDTO));
        return dto;
    }
}
