package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    // HashMap для быстрого доступа по дню недели O(1)
    // TreeMap внутри для хранения времени начала -> список тренировок (отсортировано)
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new EnumMap<>(DayOfWeek.class);
        // Инициализируем для каждого дня недели
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    // Добавление тренировки
    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        // Получаем TreeMap для этого дня
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

        // Если для этого времени еще нет тренировок, создаем новый список
        if (!daySchedule.containsKey(time)) {
            daySchedule.put(time, new ArrayList<>());
        }

        // Добавляем тренировку в список для этого времени
        daySchedule.get(time).add(trainingSession);
    }

    // Получение всех тренировок за день, отсортированных по времени
    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result = new ArrayList<>();
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        // Проходим по всем временам в отсортированном порядке
        for (Map.Entry<TimeOfDay, List<TrainingSession>> entry : daySchedule.entrySet()) {
            result.addAll(entry.getValue());
        }

        return result;
    }

    // Получение тренировок для конкретного дня и времени
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        // Возвращаем список или пустой список, если нет тренировок
        return sessions != null ? new ArrayList<>(sessions) : new ArrayList<>();
    }

    // Подсчет тренировок по тренерам
    public List<CoachTrainingsCount> getCountByCoaches() {
        // Мапа для подсчета: тренер -> количество тренировок
        Map<Coach, Integer> coachCountMap = new HashMap<>();

        // Перебираем все дни недели
        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            // Перебираем все тренировки за день
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCountMap.put(coach, coachCountMap.getOrDefault(coach, 0) + 1);
                }
            }
        }

        // Создаем список для сортировки
        List<CoachTrainingsCount> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCountMap.entrySet()) {
            result.add(new CoachTrainingsCount(entry.getKey(), entry.getValue()));
        }

        // Сортируем по убыванию количества тренировок
        result.sort((c1, c2) -> Integer.compare(c2.getCount(), c1.getCount()));

        return result;
    }

    // Вспомогательный класс для хранения тренера и количества его тренировок
    public static class CoachTrainingsCount {
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
    }
}