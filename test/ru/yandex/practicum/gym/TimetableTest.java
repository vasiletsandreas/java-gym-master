package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size(), "Должно быть одно занятие в понедельник");

        // Проверить, что занятие соответствует добавленному
        TrainingSession returnedSession = mondaySessions.get(0);
        Assertions.assertEquals(singleTrainingSession.getGroup(), returnedSession.getGroup());
        Assertions.assertEquals(singleTrainingSession.getCoach(), returnedSession.getCoach());
        Assertions.assertEquals(singleTrainingSession.getDayOfWeek(), returnedSession.getDayOfWeek());
        Assertions.assertEquals(singleTrainingSession.getTimeOfDay().getHours(), returnedSession.getTimeOfDay().getHours());
        Assertions.assertEquals(singleTrainingSession.getTimeOfDay().getMinutes(), returnedSession.getTimeOfDay().getMinutes());

        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty(), "Во вторник не должно быть занятий");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size(), "Должно быть одно занятие в понедельник");

        // Проверить, что занятие соответствует добавленному
        Assertions.assertEquals(mondayChildTrainingSession.getGroup(), mondaySessions.get(0).getGroup());
        Assertions.assertEquals(13, mondaySessions.get(0).getTimeOfDay().getHours());

        // Проверить, что за четверг вернулось два занятия
        List<TrainingSession> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size(), "Должно быть два занятия в четверг");

        // Проверить правильный порядок: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(13, thursdaySessions.get(0).getTimeOfDay().getHours(),
                "Первое занятие должно быть в 13:00");
        Assertions.assertEquals(0, thursdaySessions.get(0).getTimeOfDay().getMinutes());

        Assertions.assertEquals(20, thursdaySessions.get(1).getTimeOfDay().getHours(),
                "Второе занятие должно быть в 20:00");
        Assertions.assertEquals(0, thursdaySessions.get(1).getTimeOfDay().getMinutes());

        // Проверить группы занятий
        Assertions.assertEquals(groupChild, thursdaySessions.get(0).getGroup(),
                "Первое занятие должно быть детской группой");
        Assertions.assertEquals(groupAdult, thursdaySessions.get(1).getGroup(),
                "Второе занятие должно быть взрослой группой");

        // Проверить, что за вторник не вернулось занятий
        List<TrainingSession> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty(), "Во вторник не должно быть занятий");

        // Проверить субботу
        List<TrainingSession> saturdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY);
        Assertions.assertEquals(1, saturdaySessions.size(), "Должно быть одно занятие в субботу");
        Assertions.assertEquals(10, saturdaySessions.get(0).getTimeOfDay().getHours());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> sessionsAt1300 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, sessionsAt1300.size(),
                "Должно быть одно занятие в понедельник в 13:00");

        // Проверить соответствие занятия
        TrainingSession returnedSession = sessionsAt1300.get(0);
        Assertions.assertEquals(singleTrainingSession.getGroup(), returnedSession.getGroup());
        Assertions.assertEquals(singleTrainingSession.getCoach(), returnedSession.getCoach());
        Assertions.assertEquals(singleTrainingSession.getTimeOfDay().getHours(),
                returnedSession.getTimeOfDay().getHours());
        Assertions.assertEquals(singleTrainingSession.getTimeOfDay().getMinutes(),
                returnedSession.getTimeOfDay().getMinutes());

        // Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> sessionsAt1400 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(sessionsAt1400.isEmpty(),
                "Не должно быть занятий в понедельник в 14:00");
    }

    @Test
    void testMultipleTrainingsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");

        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 60);

        // Две тренировки в одно и то же время
        TrainingSession training1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession training2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(training1);
        timetable.addNewTrainingSession(training2);

        // Проверить, что в одно время может быть несколько тренировок
        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        Assertions.assertEquals(2, sessions.size(),
                "Должно быть две тренировки в понедельник в 10:00");

        // Проверить, что обе тренировки присутствуют
        Set<Coach> coaches = new HashSet<>();
        Set<Group> groups = new HashSet<>();
        for (TrainingSession session : sessions) {
            coaches.add(session.getCoach());
            groups.add(session.getGroup());
        }

        Assertions.assertTrue(coaches.contains(coach1), "Должен быть тренер Иванов");
        Assertions.assertTrue(coaches.contains(coach2), "Должен быть тренер Петров");
        Assertions.assertTrue(groups.contains(group1), "Должна быть группа Йога");
        Assertions.assertTrue(groups.contains(group2), "Должна быть группа Пилатес");
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Coach coach3 = new Coach("Сидоров", "Алексей", "Владимирович");

        Group group = new Group("Йога", Age.ADULT, 60);

        // coach1: 4 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(10, 0)));

        // coach2: 2 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0)));

        // coach3: 3 тренировки
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(14, 0)));

        // Изменено: используем CoachTrainingsCount вместо Timetable.CoachTrainingsCount
        List<CoachTrainingsCount> result = timetable.getCountByCoaches();

        // Проверить количество тренеров
        Assertions.assertEquals(3, result.size(), "Должно быть 3 тренера");

        // Проверить сортировку по убыванию количества тренировок
        Assertions.assertEquals(4, result.get(0).getCount(),
                "Первый тренер должен иметь 4 тренировки");
        Assertions.assertEquals(coach1, result.get(0).getCoach(),
                "Первый тренер должен быть Иванов");

        Assertions.assertEquals(3, result.get(1).getCount(),
                "Второй тренер должен иметь 3 тренировки");
        Assertions.assertEquals(coach3, result.get(1).getCoach(),
                "Второй тренер должен быть Сидоров");

        Assertions.assertEquals(2, result.get(2).getCount(),
                "Третий тренер должен иметь 2 тренировки");
        Assertions.assertEquals(coach2, result.get(2).getCoach(),
                "Третий тренер должен быть Петров");
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        // Проверить пустое расписание
        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertTrue(mondaySessions.isEmpty(),
                "В пустом расписании не должно быть занятий");

        List<TrainingSession> specificSession = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        Assertions.assertTrue(specificSession.isEmpty(),
                "В пустом расписании не должно быть занятий в конкретное время");

        // Изменено: используем CoachTrainingsCount вместо Timetable.CoachTrainingsCount
        List<CoachTrainingsCount> coachCounts = timetable.getCountByCoaches();
        Assertions.assertTrue(coachCounts.isEmpty(),
                "В пустом расписании не должно быть тренеров");
    }

    @Test
    void testTrainingOrderingDifferentDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Йога", Age.ADULT, 60);

        // Добавляем тренировки в разном порядке времени
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 30)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0)));

        List<TrainingSession> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        // Проверить количество
        Assertions.assertEquals(4, mondaySessions.size(),
                "Должно быть 4 занятия в понедельник");

        // Проверить сортировку по возрастанию времени
        Assertions.assertEquals(9, mondaySessions.get(0).getTimeOfDay().getHours(),
                "Первое занятие должно быть в 9:00");
        Assertions.assertEquals(0, mondaySessions.get(0).getTimeOfDay().getMinutes());

        Assertions.assertEquals(11, mondaySessions.get(1).getTimeOfDay().getHours(),
                "Второе занятие должно быть в 11:00");
        Assertions.assertEquals(0, mondaySessions.get(1).getTimeOfDay().getMinutes());

        Assertions.assertEquals(12, mondaySessions.get(2).getTimeOfDay().getHours(),
                "Третье занятие должно быть в 12:30");
        Assertions.assertEquals(30, mondaySessions.get(2).getTimeOfDay().getMinutes());

        Assertions.assertEquals(15, mondaySessions.get(3).getTimeOfDay().getHours(),
                "Четвертое занятие должно быть в 15:00");
        Assertions.assertEquals(0, mondaySessions.get(3).getTimeOfDay().getMinutes());
    }
}