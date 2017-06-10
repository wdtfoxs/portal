package com.code405.service;

import com.code405.entity.embeddable.Time;
import com.code405.entity.model.Event;
import com.code405.repository.models.EventRepository;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Peter Kozlovsky on 14.05.17.
 */
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public List<Event> findLast3Event() {
        return eventRepository.findFirst3ByOrderByEventDateDesc().stream().map(event -> {
            event.setText(Jsoup.parse(event.getText()).text());
            return event;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Event> findNewsByPage(Integer pageNumber, Integer pageSize) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize,
                new Sort(Sort.Direction.DESC, "eventDate"));
        return eventRepository.findAll(request).getContent().stream().map(event -> {
            event.setText(Jsoup.parse(event.getText()).text());
            return event;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Event findOne(Long id) {
        return eventRepository.findOne(id);
    }

    @Transactional
    @SneakyThrows
    public Event createEvent(MultipartFile file, String text, String title, String from,
                             String to, String date) {
        if (!file.isEmpty()){
            String imageName = UUID.randomUUID().toString();
            File image = resourceLoader.getResource("/resources/image/" + imageName + ".png").getFile();
            file.transferTo(image);
            if(checkIsAnImage(file, image) && checkForValid(text, title)) {
                if (Integer.parseInt(date.substring(0, 4)) > Year.now(ZoneId.of("UTC+3")).getValue() + 1) {
                    date = Year.now(ZoneId.of("UTC+3")).getValue() + date.substring(4, date.length());
                }
                Timestamp fromTime = new Timestamp(format.parse(date + " " + from).getTime());
                Timestamp eventDate = new Timestamp(format.parse(date + " " + from).getTime());
                Timestamp toTime = new Timestamp(format.parse(date + " " + to).getTime());
                if (checkTimestamps(eventDate, fromTime, toTime)) {

                    Event event = Event.builder().eventDate(eventDate).title(Jsoup.parse(title).text().trim()).text(text.trim())
                            .image("/resources/image/" + imageName + ".png")
                            .time(new Time(fromTime, toTime)).build();
                    return eventRepository.save(event);
                }
            }
        }
        return null;
    }

    private boolean checkIsAnImage(MultipartFile file, File image) {
        try {
            Image image1 = ImageIO.read(image);
            if (image1 == null) {
                return false;
            }
        } catch (IOException ex) {
            return false;
        }
        String mimetype = file.getContentType();
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    private boolean checkTimestamps(Timestamp eventDate, Timestamp from, Timestamp to) {
        return eventDate.compareTo(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC+3")))) > 0 && to.compareTo(from) > 0;
    }

    private boolean checkForValid(String text, String title) {
        String cleanText = Jsoup.parse(text.trim()).text().trim();
        return Jsoup.parse(title.trim()).text().trim().length() >= 8 && cleanText.length() >= 20 && cleanText.length() <= 1000;
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    @SneakyThrows
    public Event updateEvent(Long id, MultipartFile file, String text, String title,
                             String from, String to, String date) {
        Event one = eventRepository.findOne(id);
        String filename = null;
        File temp = new File("q");
        file.transferTo(temp);
        if (!file.isEmpty() && checkIsAnImage(file, temp)) {
            String imageName = UUID.randomUUID().toString();
            filename = "/resources/image/" + imageName + ".png";
            File image = resourceLoader.getResource(filename).getFile();
            file.transferTo(image);
        }
        Timestamp fromTime = new Timestamp(format.parse(date + " " + from).getTime());
        Timestamp eventDate = new Timestamp(format.parse(date + " " + from).getTime());
        Timestamp toTime = new Timestamp(format.parse(date + " " + to).getTime());
        if (checkTimestamps(eventDate, fromTime, toTime)) {
            if (filename != null) {
                one.setImage(filename);
            }
            one.setEventDate(eventDate);
            if (checkForValid(text, title)) {
                one.setTitle(Jsoup.parse(title).text().trim());
                one.setText(text.trim());
            }
            one.setTime(new Time(fromTime, toTime));
            one = eventRepository.save(one);
        }
        return one;
    }
}










