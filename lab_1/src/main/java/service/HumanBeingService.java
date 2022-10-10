package service;

import dao.HumanBeingDao;
import model.HumanBeing;
import model.dataForXml.Errors;
import model.dataForXml.HumanBeings;
import model.dataForXml.JaxbHumanBeing;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import util.Jaxb;
import validators.HumanBeingValidator;
import validators.ValidateFieldsException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.IntStream;

import static util.ServletUtil.getBody;


public class HumanBeingService {

    private final HumanBeingDao humanBeingDao;
    private final HumanBeings humanBeingsList;
    private final HumanBeingValidator humanBeingValidator;
    private final Errors errorsList;


    public HumanBeingService() {
        this.humanBeingDao = new HumanBeingDao();
        this.humanBeingsList = new HumanBeings();
        this.humanBeingValidator = new HumanBeingValidator();
        this.errorsList = new Errors();
    }

    // Получение списка всех human
    public void getAllHumanBeing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numberOfRecordsPerPage = 5;
        int selectedPage = 1;
        List<HumanBeing> humanBeings = humanBeingDao.getAllHumanBeing();
        int humanBeingsQuality = (int) Math.ceil((double) (humanBeings.size() + 1) / numberOfRecordsPerPage);
        request.setAttribute("pagesQuality", IntStream.range(1, (int) Math.ceil(humanBeingsQuality + 1)).toArray());
        request.setAttribute("humanBeingsLength", humanBeings.size());
        int from = (selectedPage - 1) * numberOfRecordsPerPage;
        request.setAttribute("humanBeings", Arrays.copyOfRange(humanBeings.toArray(), from, from + numberOfRecordsPerPage));
        request.getRequestDispatcher("jsp/main-page.jsp").forward(request, response);
    }

    public void filterHumanBeing(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String[]> queryMap = request.getParameterMap();
        List<HumanBeing> filterHumanBeings = humanBeingDao.getFilteredHumanBeings(queryMap);
        sendHumanBeings(request, response, filterHumanBeings);
    }

    //сортировка
    public void sort(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        List<HumanBeing> sortedCities = humanBeingDao.sort(sortBy, order);
        sendHumanBeings(request, response, sortedCities);
    }


    private void sendHumanBeings(HttpServletRequest request, HttpServletResponse response, List<HumanBeing> filterHumanBeings) throws Exception {
        int numberOfRecordsPerPage = Integer.parseInt(request.getParameter("numberOfRecordsPerPage"));
        int selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
        int from = (selectedPage - 1) * numberOfRecordsPerPage;
        int to = (from + numberOfRecordsPerPage > filterHumanBeings.size()) ? filterHumanBeings.size() : from + numberOfRecordsPerPage;
        List<HumanBeing> humanBeings = new ArrayList<>(filterHumanBeings.subList(from, to));
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        humanBeingsList.setHumanBeings(humanBeings);
        response.setStatus(200);
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        serializer.write(humanBeingsList, writer);
        String xml = writer.toString();
        System.out.println(xml);
        response.getWriter().print(xml);
    }

    //создание human
    public void createHumanBeing(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String humanBeingBody = getBody(request);
            JaxbHumanBeing humanBeing = Jaxb.fromStr(humanBeingBody, JaxbHumanBeing.class);
            if (humanBeing.getImpactSpeed() == null || humanBeing.getWeaponType() == null) {
                if (humanBeing.getImpactSpeed() == null && humanBeing.getWeaponType() == null) {
                    humanBeing.setImpactNull();
                    humanBeing.setWeaponTypeNull();
                } else if (humanBeing.getImpactSpeed() == null) {
                    humanBeing.setImpactNull();
                } else {
                    humanBeing.setWeaponTypeNull();
                }
                humanBeingValidator.validate(humanBeing);
                humanBeingDao.addHumanBeing(humanBeing.toHumanBeing());
            } else {
                humanBeingValidator.validate(humanBeing);
                humanBeingDao.addHumanBeing(humanBeing.toHumanBeing());
            }
            response.setStatus(200);
        } catch (ValidateFieldsException ex) {
            sendErrorList(request, response, ex);
        } catch (IllegalAccessException | JAXBException e) {
            e.printStackTrace();
        }
    }


    //удаление human
    public void deleteHumanBeing(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        if (humanBeingDao.deleteHumanBeing(id)) {
            response.setStatus(200);
        } else {
            throw new EntityNotFoundException("Cannot find HumanBeing with id " + id);
        }
    }

    //удаление mood
    public void deleteByMood(HttpServletRequest request, HttpServletResponse response) {
        String mood = request.getParameter("mood");
        if (humanBeingDao.deleteByMood(mood)) {
            response.setStatus(200);
        } else {
            throw new EntityNotFoundException("Cannot find mood" + mood);
        }
    }


    //обновление human
    public void updateHumanBeing(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String humanBeingBody = getBody(request);
            JaxbHumanBeing humanBeingData = Jaxb.fromStr(humanBeingBody, JaxbHumanBeing.class);
            humanBeingValidator.validate(humanBeingData);
            Optional<HumanBeing> humanBeingFromDB = humanBeingDao.getHumanBeingById(humanBeingData.getId());
            if (humanBeingFromDB.isPresent()) {
                HumanBeing updatingHumanBeing = humanBeingFromDB.get();
                updatingHumanBeing.update(humanBeingData);
                humanBeingDao.updateHumanBeing(updatingHumanBeing);
                response.setStatus(200);
            } else {
                throw new EntityNotFoundException("Cannot update humanBeing");
            }
        } catch (ValidateFieldsException ex) {
            sendErrorList(request, response, ex);
        } catch (IllegalAccessException | JAXBException e) {
            e.printStackTrace();
        }
    }


    //Отправка ошибок
    private void sendErrorList(HttpServletRequest request, HttpServletResponse response, ValidateFieldsException ex) throws Exception {
        response.setStatus(400);
        errorsList.setErrors(ex.getErrorMsg());
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        serializer.write(errorsList, writer);
        String xml = writer.toString();
        response.getWriter().print(xml);
    }


}
