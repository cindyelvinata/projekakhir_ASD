import java.util.List;

public class TaskSorter {

    // Bubble Sort berdasarkan Deadline
    public static void bubblesortDeadline(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = 0; j < tasks.size() - i - 1; j++) {
                if (tasks.get(j).getDeadline().isAfter(tasks.get(j + 1).getDeadline())) {
                    // transit = variabel untuk tempat nilai sementara
                    Task transit = tasks.get(j);
                    tasks.set(j, tasks.get(j + 1));
                    tasks.set(j + 1, transit);
                }
            }
        }
    }

    // Selection Sort berdasarkan Prioritas
    public static void selectionsortPrioritas(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            int indexTerkecil = i;
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(j).getPrioritas() < tasks.get(indexTerkecil).getPrioritas()) {
                    indexTerkecil = j;
                }
            }
            Task transit = tasks.get(indexTerkecil);
            tasks.set(indexTerkecil, tasks.get(i));
            tasks.set(i, transit);
        }
    }
}