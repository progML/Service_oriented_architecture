package dao;

import model.HumanBeing;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.DateBuilder;
import util.HibernateDatasource;

import javax.management.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class HumanBeingDao {

    public HumanBeingDao() {
    }

    //получение всех human
    public List<HumanBeing> getAllHumanBeing() {
        Transaction transaction = null;
        List<HumanBeing> humanBeings = null;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            humanBeings = session.createQuery("from HumanBeing").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
        return humanBeings;
    }

    //добавление human
    public void addHumanBeing(HumanBeing humanBeing) {
        Transaction transaction = null;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(humanBeing);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    //удаление human по id
    public boolean deleteHumanBeing(Long id) {
        Transaction transaction = null;
        boolean successful = false;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            HumanBeing humanBeing = session.find(HumanBeing.class, id);
            if (humanBeing != null) {
                session.delete(humanBeing);
                session.flush();
                successful = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return successful;
    }

    //удаление по mood
    public boolean deleteByMood(String mood) {
        Transaction transaction = null;
        boolean successful = false;
        List<HumanBeing> humanBeings = null;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            humanBeings = session.createQuery("Select id from HumanBeing where mood = '" + mood + "' ").getResultList();
            if (!humanBeings.isEmpty()) {
                session.createQuery("DELETE from HumanBeing where id = " + humanBeings.get(0)).executeUpdate();
                successful = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return successful;
    }

    //обновление human
    public void updateHumanBeing(HumanBeing humanBeing) {
        Transaction transaction = null;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(humanBeing);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    //поиск human по id
    public Optional<HumanBeing> getHumanBeingById(Long id) {
        Transaction transaction = null;
        HumanBeing humanBeing = null;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            humanBeing = session.get(HumanBeing.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return Optional.ofNullable(humanBeing);
    }

    public List<HumanBeing> getFilteredHumanBeings(Map<String, String[]> params) {
        Transaction transaction = null;
        List<HumanBeing> humanBeings = null;
        boolean isJoin = false;
        StringBuilder queryStr = new StringBuilder("from HumanBeing hb");
        if (params.containsKey("x1") || params.containsKey("y1") || params.containsKey("cool")) {
            isJoin = true;
            if (params.containsKey("x1") || params.containsKey("y1")) {
                queryStr.append(" join hb.coordinates cor");
            }
            if (params.containsKey("cool")) {
                queryStr.append(" join hb.car ca");
            }
            queryStr.append(" where");
            if (params.containsKey("x1")) {
                queryStr.append(" cor.x BETWEEN '").append(params.get("x1")[0]).append("' AND '").append(params.get("x2")[0]).append("' AND");
            }
            if (params.containsKey("y1")) {
                queryStr.append(" cor.y BETWEEN '").append(params.get("y1")[0]).append("' AND '").append(params.get("y2")[0]).append("' AND");
            }
            if (params.containsKey("cool")) {
                queryStr.append(" ca.cool = '").append(params.get("cool")[0]).append("' AND");
            }


        }
        if (!queryStr.toString().contains("where") && params.size() > 2) {
            queryStr.append(" where");
        }
        if (params.containsKey("name")) {
            queryStr.append(" hb.name = '").append(params.get("name")[0]).append("' AND");
        }
        if (params.containsKey("start-name")) {
            queryStr.append(" hb.name LIKE '").append(params.get("start-name")[0]).append("%' AND");
        }

        if (params.containsKey("start-creation-date")) {
            LocalDateTime startCreationDate = DateBuilder.getLocalDateFromDateAndTime(params.get("start-creation-date")[0], params.get("start-creation-time")[0]);
            LocalDateTime endCreationDate = DateBuilder.getLocalDateFromDateAndTime(params.get("end-creation-date")[0], params.get("end-creation-time")[0]);
            queryStr.append(" hb.creationDate BETWEEN '").append(Timestamp.valueOf(startCreationDate)).append("' AND '").append(Timestamp.valueOf(endCreationDate)).append("' AND");
        }

        if (params.containsKey("realHero")) {
            queryStr.append(" hb.realHero = '").append(params.get("realHero")[0]).append("' AND");
        }
        if (params.containsKey("hasToothpick")) {
            queryStr.append(" hb.hasToothpick = '").append(params.get("hasToothpick")[0]).append("' AND");
        }


        ArrayList<String> paramsNames = new ArrayList<>(Arrays.asList("impactSpeed", "minutesOfWaiting"));
        for (String paramName : paramsNames) {
            if (params.containsKey(paramName + "1")) {
                queryStr.append(" hb." + paramName + " BETWEEN '").append(params.get(paramName + "1")[0]).append("' AND '").append(params.get(paramName + "2")[0]).append("' AND");
            }
        }

        ArrayList<String> checkboxParamsNames = new ArrayList<>(Arrays.asList("weaponType", "mood"));
        for (String paramName : checkboxParamsNames) {
            if (params.containsKey(paramName)) {
                queryStr.append(" hb." + paramName + " IN (");
                String[] checkboxValues = params.get(paramName);
                for (int i = 0; i < checkboxValues.length; i++) {
                    queryStr.append("'" + checkboxValues[i] + "', ");
                }
                queryStr = new StringBuilder(queryStr.substring(0, queryStr.length() - 2));
                queryStr.append(") AND");
            }
        }

        //отризаем не нужный AND
        if (queryStr.toString().endsWith("AND")) {
            queryStr = new StringBuilder(queryStr.substring(0, queryStr.length() - 4));
        }

        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (isJoin) {
                List<Object[]> humanBeingsListWithExtraColumns = session.createQuery(queryStr.toString()).getResultList();
                humanBeings = new ArrayList<>();
                for (Object[] humanBeingWithExtraColumns : humanBeingsListWithExtraColumns) {
                    humanBeings.add((HumanBeing) humanBeingWithExtraColumns[0]);
                }
            } else {
                humanBeings = session.createQuery(queryStr.toString()).getResultList();
            }
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return humanBeings;
    }

    public List<HumanBeing> sort(String sortBy, String order) {
        Transaction transaction = null;
        List<HumanBeing> humanBeings = null;
        List<Object[]> humanBeingsListWithExtraColumns = null;
        try (Session session = HibernateDatasource.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            if (sortBy.equals("x") || sortBy.equals("y")) {
                humanBeingsListWithExtraColumns = session.createQuery("from HumanBeing hb join hb.coordinates cor ORDER BY cor." + sortBy + " " + order).getResultList();
            }
            if (sortBy.equals("cool")) {
                humanBeingsListWithExtraColumns = session.createQuery("from HumanBeing hb join hb.car ca ORDER BY ca." + sortBy + " " + order).getResultList();
            }
            if (humanBeingsListWithExtraColumns != null && !humanBeingsListWithExtraColumns.isEmpty()) {
                humanBeings = new ArrayList<>();
                for (Object[] humanBeingWithExtraColumns : humanBeingsListWithExtraColumns) {
                    humanBeings.add((HumanBeing) humanBeingWithExtraColumns[0]);
                }
            } else {
                humanBeings = session.createQuery("from HumanBeing ORDER BY " + sortBy + " " + order).getResultList();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return humanBeings;
    }
}
