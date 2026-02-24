import java.util.Scanner;

/**
 * Day 2 Project: Student Grade Analyzer
 * Features: Add/Display students, Class Summary, Search, Sort, Grade Distribution,
 *           Top K Students, Below Average, Rename, Grade Histogram
 *
 * DSA Concepts: Arrays, Parallel Arrays, Selection Sort O(n²), Linear Search O(n)
 */
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("     Welcome to Student Grade Analyzer     ");
        System.out.println("===========================================");

        System.out.print("\nEnter number of students: ");
        int n = readIntPositive();

        String[] names  = new String[n];
        int[]    scores = new int[n];

        System.out.println("\n--- Enter Student Details ---");
        for (int i = 0; i < n; i++) {
            System.out.println("\nStudent " + (i + 1));
            System.out.print("  Name  : ");
            names[i] = readNonEmptyString();

            // Prevent duplicate names
            while (findStudentIndex(names, names[i], i) != -1) {
                System.out.println("  ⚠ That name already exists. Try again.");
                System.out.print("  Name  : ");
                names[i] = readNonEmptyString();
            }

            System.out.print("  Score (0-100): ");
            scores[i] = readScore();
        }

        // ── Main Menu Loop ──────────────────────────────────────────────────
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║     Student Grade Analyzer       ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Display all students         ║");
            System.out.println("║  2. Update a student score       ║");
            System.out.println("║  3. Class summary (avg/min/max)  ║");
            System.out.println("║  4. Search student by name       ║");
            System.out.println("║  5. Sort report (high → low)     ║");
            System.out.println("║  6. Grade distribution           ║");
            System.out.println("║  7. Grade histogram ★            ║");
            System.out.println("║  8. Top K students               ║");
            System.out.println("║  9. Students below average       ║");
            System.out.println("║ 10. Rename a student             ║");
            System.out.println("║  0. Exit                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Choose: ");

            int choice = readInt();

            switch (choice) {
                case 0:
                    System.out.println("\nGoodbye! 👋");
                    return;
                case 1:
                    displayAll(names, scores);
                    break;
                case 2:
                    updateScoreByName(names, scores);
                    break;
                case 3:
                    classSummary(names, scores);
                    break;
                case 4:
                    searchByName(names, scores);
                    break;
                case 5:
                    // Work on a copy so original order is preserved unless user wants to keep sorted
                    String[] sortedNames  = names.clone();
                    int[]    sortedScores = scores.clone();
                    sortByScoreDesc(sortedNames, sortedScores);
                    System.out.println("\n--- Sort Report (High → Low) ---");
                    for (int i = 0; i < sortedNames.length; i++) {
                        System.out.printf("  #%-2d %-20s %3d  %s%n",
                                (i + 1), sortedNames[i], sortedScores[i],
                                letterGrade(sortedScores[i]));
                    }
                    break;
                case 6:
                    gradeDistribution(scores);
                    break;
                case 7:
                    gradeHistogram(scores);
                    break;
                case 8:
                    topKStudents(names, scores);
                    break;
                case 9:
                    belowAverage(names, scores);
                    break;
                case 10:
                    renameStudent(names, scores);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ── Feature Methods ──────────────────────────────────────────────────────

    /** O(n) — single traversal */
    static void displayAll(String[] names, int[] scores) {
        System.out.println("\n--- All Students ---");
        System.out.printf("  %-4s %-20s %-7s %s%n", "#", "Name", "Score", "Grade");
        System.out.println("  " + "-".repeat(38));
        for (int i = 0; i < names.length; i++) {
            System.out.printf("  %-4d %-20s %-7d %s%n",
                    (i + 1), names[i], scores[i], letterGrade(scores[i]));
        }
    }

    /** O(n) search + O(1) update */
    static void updateScoreByName(String[] names, int[] scores) {
        System.out.print("Enter name to update: ");
        String target = readNonEmptyString();

        int idx = findStudentIndex(names, target);
        if (idx == -1) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current score: " + scores[idx]);
        System.out.print("Enter new score (0-100): ");
        scores[idx] = readScore();
        System.out.println("Updated: " + names[idx] + " → " + scores[idx] + " (" + letterGrade(scores[idx]) + ")");
    }

    /** O(n) — single pass */
    static void classSummary(String[] names, int[] scores) {
        int sum    = 0;
        int minIdx = 0;
        int maxIdx = 0;

        for (int i = 0; i < scores.length; i++) {
            sum += scores[i];
            if (scores[i] < scores[minIdx]) minIdx = i;
            if (scores[i] > scores[maxIdx]) maxIdx = i;
        }

        double avg = (double) sum / scores.length;

        System.out.println("\n--- Class Summary ---");
        System.out.printf("  Average  : %.2f%n", avg);
        System.out.printf("  Highest  : %d (%s)%n", scores[maxIdx], names[maxIdx]);
        System.out.printf("  Lowest   : %d (%s)%n", scores[minIdx], names[minIdx]);
        System.out.printf("  Students : %d%n", scores.length);
    }

    /** O(n) linear search */
    static void searchByName(String[] names, int[] scores) {
        System.out.print("Enter name to search: ");
        String target = readNonEmptyString();

        int idx = findStudentIndex(names, target);
        if (idx == -1) {
            System.out.println("Student not found.");
        } else {
            System.out.printf("Found: %-20s Score: %d  Grade: %s%n",
                    names[idx], scores[idx], letterGrade(scores[idx]));
        }
    }

    /** O(n) frequency count */
    static void gradeDistribution(int[] scores) {
        int a = 0, b = 0, c = 0, d = 0, f = 0;

        for (int s : scores) {
            if      (s >= 90) a++;
            else if (s >= 80) b++;
            else if (s >= 70) c++;
            else if (s >= 60) d++;
            else              f++;
        }

        System.out.println("\n--- Grade Distribution ---");
        System.out.println("  A (90-100): " + a);
        System.out.println("  B (80-89) : " + b);
        System.out.println("  C (70-79) : " + c);
        System.out.println("  D (60-69) : " + d);
        System.out.println("  F (<60)   : " + f);
    }

    /** O(n) frequency count + visual histogram */
    static void gradeHistogram(int[] scores) {
        int a = 0, b = 0, c = 0, d = 0, f = 0;

        for (int s : scores) {
            if      (s >= 90) a++;
            else if (s >= 80) b++;
            else if (s >= 70) c++;
            else if (s >= 60) d++;
            else              f++;
        }

        System.out.println("\n--- Grade Histogram ---");
        printBar("A", a);
        printBar("B", b);
        printBar("C", c);
        printBar("D", d);
        printBar("F", f);
    }

    static void printBar(String label, int count) {
        System.out.printf("  %s | ", label);
        for (int i = 0; i < count; i++) System.out.print("★");
        System.out.println(" (" + count + ")");
    }

    /** O(n log n) sort (selection sort O(n²)) + display top K */
    static void topKStudents(String[] names, int[] scores) {
        System.out.print("Enter K (how many top students to show): ");
        int k = readIntPositive();
        k = Math.min(k, names.length);   // can't show more than we have

        String[] sortedNames  = names.clone();
        int[]    sortedScores = scores.clone();
        sortByScoreDesc(sortedNames, sortedScores);

        System.out.println("\n--- Top " + k + " Students ---");
        for (int i = 0; i < k; i++) {
            System.out.printf("  #%-2d %-20s %3d  %s%n",
                    (i + 1), sortedNames[i], sortedScores[i], letterGrade(sortedScores[i]));
        }
    }

    /** O(n) */
    static void belowAverage(String[] names, int[] scores) {
        int sum = 0;
        for (int s : scores) sum += s;
        double avg = (double) sum / scores.length;

        System.out.printf("%n--- Students Below Average (%.2f) ---%n", avg);
        boolean found = false;
        for (int i = 0; i < names.length; i++) {
            if (scores[i] < avg) {
                System.out.printf("  %-20s %3d  %s%n", names[i], scores[i], letterGrade(scores[i]));
                found = true;
            }
        }
        if (!found) System.out.println("  None! Great class 🎉");
    }

    /** O(n) search + O(1) rename */
    static void renameStudent(String[] names, int[] scores) {
        System.out.print("Enter current name: ");
        String target = readNonEmptyString();

        int idx = findStudentIndex(names, target);
        if (idx == -1) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter new name: ");
        String newName = readNonEmptyString();

        // Check for duplicate
        if (findStudentIndex(names, newName) != -1) {
            System.out.println("That name already exists. Rename cancelled.");
            return;
        }

        names[idx] = newName;
        System.out.println("Renamed to: " + newName);
    }

    // ── Helper Methods ───────────────────────────────────────────────────────

    /**
     * Selection Sort descending — O(n²)
     * Keeps names[] and scores[] in sync (parallel arrays).
     */
    static void sortByScoreDesc(String[] names, int[] scores) {
        int n = scores.length;

        for (int i = 0; i < n - 1; i++) {
            int bestIdx = i;

            for (int j = i + 1; j < n; j++) {
                if (scores[j] > scores[bestIdx]) {
                    bestIdx = j;
                }
            }

            // Swap scores
            int tmpScore  = scores[i];
            scores[i]     = scores[bestIdx];
            scores[bestIdx] = tmpScore;

            // Swap names (must mirror score swap)
            String tmpName  = names[i];
            names[i]        = names[bestIdx];
            names[bestIdx]  = tmpName;
        }
    }

    /**
     * Linear search — O(n), case-insensitive.
     * @param limit  only search indices 0..limit-1 (pass names.length for full search)
     */
    static int findStudentIndex(String[] names, String target, int limit) {
        for (int i = 0; i < limit; i++) {
            if (names[i] != null && names[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }

    /** Convenience overload — searches entire array */
    static int findStudentIndex(String[] names, String target) {
        return findStudentIndex(names, target, names.length);
    }

    /** Map numeric score to letter grade */
    static String letterGrade(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    // ── Input Helpers ────────────────────────────────────────────────────────

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    static int readIntPositive() {
        while (true) {
            int x = readInt();
            if (x > 0) return x;
            System.out.print("Must be positive. Try again: ");
        }
    }

    static int readScore() {
        while (true) {
            int s = readInt();
            if (s >= 0 && s <= 100) return s;
            System.out.print("Score must be 0-100. Try again: ");
        }
    }

    static String readNonEmptyString() {
        while (true) {
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.print("Cannot be empty. Enter again: ");
        }
    }
}
