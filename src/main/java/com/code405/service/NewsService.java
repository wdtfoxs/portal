package com.code405.service;

import com.code405.entity.model.News;
import com.code405.repository.models.NewsRepository;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Peter Kozlovsky on 14.05.17.
 */
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private ResourceLoader resourceLoader;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<News> findLast3News() {
        return newsRepository.findFirst3ByOrderByCreatedDesc().stream().map(event -> {
            event.setText(Jsoup.parse(event.getText()).text());
            return event;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<News> findNewsByPage(Integer pageNumber, Integer pageSize) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize,
                new Sort(Sort.Direction.DESC, "created"));
        return newsRepository.findAll(request).getContent().stream().map(event -> {
            event.setText(Jsoup.parse(event.getText()).text());
            return event;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public News findOne(Long id) {
        return newsRepository.findOne(id);
    }

    @Transactional
    @SneakyThrows
    public News createNews(MultipartFile file, String text, String title) {
        if (!file.isEmpty()) {
            String imageName = UUID.randomUUID().toString();
            File image = resourceLoader.getResource("resources/image/" + imageName + ".png").getFile();
            file.transferTo(image);
            if (checkIsAnImage(file, image) && checkForValid(text, title)) {
                News news = News.builder()
                        .created(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC+3"))))
                        .title(Jsoup.parse(title.trim()).text())
                        .text(text.trim())
                        .image("/resources/image/" + imageName + ".png")
                        .build();
                return newsRepository.save(news);
            }
        }
        return null;
    }

    private boolean checkForValid(String text, String title) {
        String cleanText = Jsoup.parse(text.trim()).text().trim();
        return Jsoup.parse(title.trim()).text().length() >= 8 && cleanText.length() >= 20 && cleanText.length() <= 1000;
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

    public void delete(Long id) {
        newsRepository.delete(id);
    }
    @SneakyThrows
    public News updateNews(Long id, MultipartFile file, String text, String title) {
        News news = newsRepository.findOne(id);
        String filename = null;
        File temp = new File("q");
        file.transferTo(temp);
        if (!file.isEmpty() && checkIsAnImage(file, temp)) {
            String imageName = UUID.randomUUID().toString();
            filename = "/resources/image/" + imageName + ".png";
            File image = resourceLoader.getResource(filename).getFile();
            file.transferTo(image);
        }
        if (filename != null) {
            news.setImage(filename);
        }
        if (!checkForValid(text, title)) {
            return news;
        }
        news.setText(text.trim());
        news.setTitle(title.trim());
        return newsRepository.save(news);
    }
}
