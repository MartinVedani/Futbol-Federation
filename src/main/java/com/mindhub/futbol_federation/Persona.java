package com.mindhub.futbol_federation;
// Los nombres de los paquetes se estila dejarlo en minúscula así no se confunden

// entre los objetos. Lo único que tiene que estar en mayúscula en java son las clases, las
// variables en minúscula (preferiblemente cameCase pero con guión bajo también).
// Los nombres de las propiedades en camelCase se pasan a _ en la base de datos
// Como referencia: Vue y DOM usan camelCase, html los pasa a "-".
// En js, java y Python como alternativa a camelCase se usa "_" xq no se puede usar "-".

// En resume, camelCase "arriba" o guión "abajo". En el "medio" nunca.

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass // le dice a Spring q las propiedades deben mapearse en la table de las clases
                  // hijas "Jugador" y "Técnico"
public abstract class Persona {
    // la clase "publica" es accesible globalmente. La hacemos "abstracta" si
    // queremos definir los
    // mismos atributos y comportamientos que van a ser heredados por varias otras
    // clases hijas, por
    // ejemplo "Jugador.java" (que utilizarán el método "super" para elevar estos
    // atributos a la clase
    // madre "Persona".

    // Atributos o propiedades de los objetos pertenecientes a la clase persona
    @Id // le dice a Spring que genere un Id para cada instancia de las tablas hijas de
        // Persona (Jugador y Técnico)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre; // los atributos son privados para que solo se puedan acceder a través de
    private String apellido; // getter y setters de forma segura y controlada.
    private int documento;
    private LocalDate fecha_nacimiento;

    // Constructor: indica con que propiedades se debe instanciar (inicializar) un
    // objeto perteneciente
    // a esta clase
    public Persona() {
    }

    public Persona(String nombre, String apellido, int documento, LocalDate fecha_nacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    // Puedo tener varios constructores que den flexibilidad en la cantidad de datos
    // y tipo de
    // iniciación (en estos ejemplos con o sin documento).
    public Persona(String nombre, String apellido, LocalDate fecha_nacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    // Es buena práctica que los parámetros o atributos se llamen igual que las
    // propiedades.
    // Se diferencian porque podemos usar la palabra clave this para hacer
    // referencia a la clase
    // donde nos encontremos.
    // "this.firstName" hace referencia al atributo (variable) firstName de la
    // clase; "firstName" hace
    // referencia al parámetro.

    // Se sigue con los métodos Getters & Setters que permiten obtener un atributo
    // del objeto
    // (getter) o modificarlo (setter).

    // En Java siempre hay que especificar qué es lo que "get" trae (o retorna), que
    // en este caso será
    // un dato String.

    public String getNombre() {
        return nombre;
    }

    // "set" ejecuta una modificación pero no retorna nada por lo que se debe
    // especificar "void".

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    // Otros métodos o comportamientos de los objetos pertenecientes a la clase
    // Persona.
    public String saludar() {
        return "Hola!";
    }

    public String nombreCompleto() {
        return this.getNombre() + " " + this.getApellido();
    }

    // los métodos abstractos son obligatorios (sin ellos no compila) permiten que
    // una clase
    // hija pueda modificarlos, entonces jugadores y técnicos se pueden presentar de
    // formas
    // diferentes.
    public abstract String presentarse();

}
