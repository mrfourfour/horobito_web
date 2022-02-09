package myproject.demo.manager.viewManager.all.controller;

import lombok.RequiredArgsConstructor;
import myproject.demo.manager.viewManager.all.service.AllNovelViewManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/novels/free")
@RequiredArgsConstructor
public class AllNovelViewManagerController {

    private final AllNovelViewManagerService allNovelViewManagerService;



    @GetMapping("/all")
    public void getAll(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
            allNovelViewManagerService.getAllNovelList(sort, cursor, size);
        } else {
            allNovelViewManagerService.getAllNovelListByCategory(categoryName, sort, cursor, size);
        }
    }

    @GetMapping("/adult")
    public void getAdult(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {

        } else {

        }
    }

    @GetMapping("/non-adult")
    public void getNonAdult(
            @PathVariable String categoryName,
            @RequestParam(value = "sort") String sort,
            @RequestParam(value = "cursor") Long cursor,
            @RequestParam(value = "size") int size
    ) {
        if (categoryName.equals("all")) {
        } else {
        }
    }
}
