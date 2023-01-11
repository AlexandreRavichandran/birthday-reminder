package com.birthdayreminder.birthdayreminder.service;

import com.birthdayreminder.birthdayreminder.domain.Birthday;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class BirthdayServiceImpl implements BirthdayService {

    private Long id = 1L;
    private static final String FILE_PATH = "src/main/resources/example.json";

    public List<Birthday> browse() {
        return this.getFileData();

    }

    public void addNew(Birthday birthday) {
        try {
            List<Birthday> birthdayList = this.getFileData();
            if (nonNull(birthdayList)) {
                birthdayList.add(birthday);
                FileWriter fileWriter = new FileWriter(FILE_PATH);
                JSONArray jsonArray = new JSONArray();
                Long id = 1L;

                for (Birthday birthday1 : birthdayList) {
                    JSONObject object = new JSONObject();
                    object.put("id", id++);
                    object.put("name", birthday1.getName());
                    object.put("date", birthday1.getDate());
                    jsonArray.add(object);
                }

                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(Birthday birthday) {
        this.delete(birthday);
        this.addNew(birthday);
    }

    public void delete(Birthday birthday) {

        try {
            List<Birthday> birthdayList = this.getFileData();
            if (nonNull(birthdayList)) {
                List<Birthday> filteredBirthday = birthdayList.stream().filter(birthday1 -> !birthday1.getId().equals(birthday.getId())).collect(Collectors.toList());
                FileWriter fileWriter = new FileWriter(FILE_PATH);
                JSONArray jsonArray = new JSONArray();
                for (Birthday birthday1 : filteredBirthday) {
                    JSONObject object = new JSONObject();
                    object.put("name", birthday1.getName());
                    object.put("date", birthday1.getDate());
                    jsonArray.add(object);
                }

                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Birthday> getFileData() {
        JSONParser parser = new JSONParser();
        List<Birthday> birthdays = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Object object = parser.parse(reader);

            JSONArray birthdayList = (JSONArray) object;

            for (Object o : birthdayList) {
                birthdays.add(this.parseBirthDayObjects((JSONObject) o));
            }

            this.id = 1L;
            return birthdays;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Birthday parseBirthDayObjects(JSONObject jsonObject) {
        Birthday birthday = new Birthday();
        birthday.setId(this.id);
        birthday.setName(jsonObject.get("name").toString());
        birthday.setDate(jsonObject.get("date").toString());
        this.id++;
        return birthday;
    }

}
