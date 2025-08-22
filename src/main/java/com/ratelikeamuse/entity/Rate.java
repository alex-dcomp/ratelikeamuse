package RateLikeAMuse.entity; // mesma coisa dos outros, tem que ver essa parte do package

import java.util.Objects;

public class Rate {

    private Long id;        // ID da avaliação
    private Long userId;    // ID do usuário que fez a avaliação
    private Long movieId;   // ID do filme que foi avaliado
    private int score;      // A nota


    public Rate() {
    }

    // Construtor completo
    public Rate(Long id, Long userId, Long movieId, int score) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.score = score;
    }

// Gets e Sets 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(id, rate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", userId=" + userId +
                ", movieId=" + movieId +
                ", score=" + score 
                + '}';
    }
}
