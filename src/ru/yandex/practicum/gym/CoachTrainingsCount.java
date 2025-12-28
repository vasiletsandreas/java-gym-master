package ru.yandex.practicum.gym;

public class CoachTrainingsCount implements Comparable<CoachTrainingsCount> {
    private final Coach coach;
    private final int count;

    public CoachTrainingsCount(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CoachTrainingsCount other) {
        // Сортировка по убыванию количества тренировок
        return Integer.compare(other.count, this.count);
    }

    @Override
    public String toString() {
        return coach + ": " + count + " тренировок";
    }
}