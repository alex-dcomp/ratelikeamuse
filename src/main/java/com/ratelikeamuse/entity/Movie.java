package ratelikeamuse.entity;

import java.util.Objects;

public class Movie {

    private Long id;
    private String nome;
    private String diretor;

    public Movie() {
    }

// Construtor dos objetos para os filmes.
    public Movie(Long id, String nome, String diretor) {
        this.id = id;
        this.nome = nome;
        this.diretor = diretor;
    }

// Gets e Sets
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", diretor='" + diretor + '\'' +
                '}';
    }
}
