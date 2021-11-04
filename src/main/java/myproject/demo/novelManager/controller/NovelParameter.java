package myproject.demo.novelManager.controller;


import lombok.Value;

import java.util.List;

@Value
public class NovelParameter {
    String title;
    String description;
    List<String> categories;
    int age;
    boolean premium;
    String coverImageUrl;

}
