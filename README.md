# Day 2 – Student Grade Analyzer

> **DSA Bootcamp Day 2** | Arrays · Strings · Sorting · Time Complexity

A console-based Java application that stores student records, runs analytics, and prints clean reports — mirroring what real backend systems do with data pipelines and dashboards.

---

## Features

| # | Option | DSA / Concept |
|---|--------|---------------|
| 1 | Display all students | Array traversal O(n) |
| 2 | Update a student's score | Linear search + O(1) update |
| 3 | Class summary (avg / min / max) | Single-pass O(n) |
| 4 | Search student by name | Case-insensitive linear search O(n) |
| 5 | Sort report (high → low) | Selection sort O(n²), parallel arrays |
| 6 | Grade distribution (A/B/C/D/F) | Frequency counting O(n) |
| 7 | Grade histogram ★ | Frequency counting + visual output |
| 8 | Top K students | Sort + slice |
| 9 | Students below average | Two-pass O(n) |
| 10 | Rename a student | Search + update |

**Bonus safeguards**
- Score validation: rejects anything outside 0–100
- Duplicate name prevention at input and rename time
- Never crashes on bad input (robust try/catch + loops)

---

## DSA Concepts Practiced

| Concept | Where |
|---------|-------|
| Parallel arrays | `names[i]` always maps to `scores[i]` |
| Selection Sort | `sortByScoreDesc()` — O(n²) |
| Linear Search | `findStudentIndex()` — O(n) |
| Frequency Counting | `gradeDistribution()` / `gradeHistogram()` |
| Single-pass analytics | `classSummary()` — O(n) |
| String comparison | `equalsIgnoreCase()` |

---

## Time & Space Complexity

| Operation | Time | Space |
|-----------|------|-------|
| Display all | O(n) | O(1) |
| Search by name | O(n) | O(1) |
| Class summary | O(n) | O(1) |
| Grade distribution | O(n) | O(1) |
| Sort report | O(n²) | O(n) clone |
| Top K | O(n²) sort + O(k) print | O(n) clone |

---

## How to Run

### Compile & run (terminal)
```bash
cd src
javac Main.java
java Main
```

### With an IDE (IntelliJ / Eclipse / VS Code)
1. Open the `src/` folder as a Java project
2. Run `Main.java`

---

## Sample Session

```
Enter number of students: 3

Student 1
  Name  : Alice
  Score (0-100): 92

Student 2
  Name  : Bob
  Score (0-100): 74

Student 3
  Name  : Carol
  Score (0-100): 85

╔══════════════════════════════════╗
║     Student Grade Analyzer       ║
╠══════════════════════════════════╣
║  3. Class summary (avg/min/max)  ║
...

--- Class Summary ---
  Average  : 83.67
  Highest  : 92 (Alice)
  Lowest   : 74 (Bob)
  Students : 3
```

---

## Project Structure

```
Day2StudentGradeAnalyzer/
└── src/
    └── Main.java      ← single-file console app
```

---

## Concepts Used

- `int[]` and `String[]` arrays
- Parallel array pattern
- `for` / `while` / enhanced `for` loops
- Static helper methods
- Exception handling for input validation
- Selection Sort
- String methods: `equalsIgnoreCase`, `trim`, `isEmpty`

---

*Part of a self-paced DSA + Java bootcamp. Day 3 → To-Do List App (ArrayList + OOP + Recursion intro)*
