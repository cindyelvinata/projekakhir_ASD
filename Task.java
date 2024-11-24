import java.time.LocalDate;

public class Task {
    private String namaTugas;
    private int prioritas;
    private LocalDate deadline;

    public Task(String nama, int level, LocalDate batas) {
        this.namaTugas = nama;
        this.prioritas = level;
        this.deadline = batas;
    }

    public String getNamaTugas() {
        return namaTugas;
    }

    public int getPrioritas() {
        return prioritas;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}