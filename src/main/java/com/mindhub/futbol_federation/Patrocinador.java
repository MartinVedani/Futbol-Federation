package com.mindhub.futbol_federation;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Patrocinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;

    // Cardinalidad: many:many
    // 1:many (1 patrocinador, muchos clubes acá) y 1:many (1 club, muchos patrocinadores en la
    // clase Club lo que termina creando una clase y una tabla intermedia llamada
    // Club_Patrocinador que tendra 2 canalidades many:1 con dos @JoinColumn: "club_id"
    // y "patrocinador_id". Spring tiene la relacion many:many pero el programador pierde el
    // control sobre la tabla y la clase intermedia que puede ser mut útil.
    @OneToMany(mappedBy = "patrocinador", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClubPatrocinador> clubPatrocinadorSet;

    public Patrocinador(){}

    public Patrocinador(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<ClubPatrocinador> getClubPatrocinadorSet() {
        return clubPatrocinadorSet;
    }

    public void setClubPatrocinadorSet(Set<ClubPatrocinador> clubPatrocinadorSet) {
        this.clubPatrocinadorSet = clubPatrocinadorSet;
    }
}
