package myproject.demo.manager.viewManager.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import myproject.demo.Novel.domain.Age;
import myproject.demo.Novel.domain.Novel;
import myproject.demo.Novel.domain.NovelRepository;
import myproject.demo.Novel.service.NovelService;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfo;
import myproject.demo.Preference.domain.PreferencInfo.PreferenceInfoRepository;
import myproject.demo.User.service.UserService;
import myproject.demo.category_novel.service.CategoryNovelRelationService;
import myproject.demo.manager.novelManager.service.NovelInfoDto;
import myproject.demo.manager.novelManager.service.NovelManagerService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RankingViewManagerService {

    private final CategoryNovelRelationService relationService;
    private final PreferenceInfoRepository preferenceInfoRepository;
    private final NovelManagerService novelManagerService;
    private final NovelService novelService;
    private final UserService userService;
    private final NovelRepository novelRepository;

    public List<NovelInfoDto> getTopAllByCategory(
            String categoryName, String date, Long cursor, int size) {
        LocalDateTime startDay = LocalDate.parse(date).atStartOfDay().plusHours(9);

        if (categoryName.equals("all")) {
            HashMap<Long, PreferenceInfoBetweenPeriod> idAndPreference = new HashMap<>();
            preferenceInfoRepository.findAllByDeletedAndPreferenceTimeGreaterThanEqual(false, startDay)
                    .forEach(
                            selected -> {
                                if (novelService.isPresentAndNonDeleted(selected.getNovelId())
                                        && userService.isPresentAndNonDeleted(selected.getUserId())) {
                                    getMapOfIdAndPreference( idAndPreference, selected);
                                }
                            });
            List<PreferenceInfoBetweenPeriod> info = new ArrayList<>(idAndPreference.values());
            return getNovelInfoDtosByCursorAndSize(cursor, size, info);
        } else {
            // 해당 카테고리를 가지고 있는 novel들의 ids
            List<Long> novelIds = relationService.getNovelIdsCorrespondingThisCategory(categoryName);
            return checkPreferenceCountPerNovel(cursor, size, startDay, novelIds);
        }
    }


    public List<NovelInfoDto> getTopAdultByCategory(String categoryName, String date, Long cursor, int size) {
        LocalDateTime startDay = LocalDate.parse(date).atStartOfDay().plusHours(9);
        List<Long> adultNovelIds = novelRepository.findAllByDeletedAndAgeGreaterThanEqual(false, Age.create(18)).stream().map(Novel::getId).collect(Collectors.toList());
        return getNovelsPreferenceInSuchCategory(categoryName, cursor, size, startDay, adultNovelIds);
    }


    public List<NovelInfoDto> getTopNonAdultByCategory(String categoryName, String date, Long cursor, int size) {
        LocalDateTime startDay = LocalDate.parse(date).atStartOfDay().plusHours(9);
        List<Long> nonAdultNovelIds = novelRepository.findAllByDeletedAndAgeLessThan(false, Age.create(18)).stream().map(Novel::getId).collect(Collectors.toList());
        return getNovelsPreferenceInSuchCategory(categoryName, cursor, size, startDay, nonAdultNovelIds);
    }






    private List<NovelInfoDto> getNovelsPreferenceInSuchCategory(String categoryName, Long cursor, int size, LocalDateTime startDay, List<Long> nonAdultNovelIds) {
        if (categoryName.equals("all")) {
            HashMap<Long, PreferenceInfoBetweenPeriod> idAndPreference = new HashMap<>();
            preferenceInfoRepository.findAllByNovelIdInAndDeletedAndPreferenceTimeGreaterThanEqual(nonAdultNovelIds, false, startDay)
                    .forEach(
                            selected -> {
                                if (novelService.isPresentAndNonDeleted(selected.getNovelId())
                                        && userService.isPresentAndNonDeleted(selected.getUserId())) {
                                    getMapOfIdAndPreference(idAndPreference, selected);
                                }
                            });
            List<PreferenceInfoBetweenPeriod> info = new ArrayList<>(idAndPreference.values());
            return getNovelInfoDtosByCursorAndSize(cursor, size, info);

        } else {
            List<Long> filtered = relationService.getNovelIdsCorrespondingThisCategoryOnlyAdult(categoryName, nonAdultNovelIds);
            return checkPreferenceCountPerNovel(cursor, size, startDay, filtered);
        }
    }

    private void getMapOfIdAndPreference(HashMap<Long, PreferenceInfoBetweenPeriod> idAndPreference, PreferenceInfo selected) {
        if (idAndPreference.containsKey(selected.getNovelId())) {
            idAndPreference.get(selected.getNovelId()).increase();
        } else {
            idAndPreference.put(
                    selected.getNovelId(), new PreferenceInfoBetweenPeriod(selected.getNovelId(), 1L));
        }
    }


    private List<NovelInfoDto> checkPreferenceCountPerNovel(Long cursor, int size, LocalDateTime startDay, List<Long> novelIds) {
        List<PreferenceInfoBetweenPeriod> pib = new ArrayList<>();
        HashSet<Long> checkList = new HashSet<>();
        novelIds.forEach(
                selectedId -> {
                    if (novelService.isPresentAndNonDeleted(selectedId)
                            && userService.isPresentAndNonDeleted(novelService.getNovelDto(selectedId).getAuthorId())) {
                        if (!checkList.contains(selectedId)) {
                            checkList.add(selectedId);
                            int preferenceSize = preferenceInfoRepository.findByNovelIdAndDeletedAndPreferenceTimeGreaterThanEqual(
                                    selectedId, false, startDay).size();
                            if (preferenceSize != 0) {
                                pib.add(new PreferenceInfoBetweenPeriod(
                                        selectedId,
                                        (long) preferenceSize));
                            }
                        }
                    }
                });
        return getNovelInfoDtosByCursorAndSize(cursor, size, pib);
    }

    private List<NovelInfoDto> getNovelInfoDtosByCursorAndSize(Long cursor, int size, List<PreferenceInfoBetweenPeriod> pib) {
        pib.sort(Collections.reverseOrder());
        if (cursor == null || cursor == 0) {
            if (pib.size() > size) {
                return pib.subList(0, size).stream().map(
                        select -> novelManagerService.viewNovelWithPreference(select.getNovelId(), select.preferenceCount)).collect(Collectors.toList());
            } else {
                return pib.subList(0, pib.size()).stream().map(
                        select -> novelManagerService.viewNovelWithPreference(select.getNovelId(), select.preferenceCount)).collect(Collectors.toList());
            }
        } else {
            int startIndex = pib.indexOf(new PreferenceInfoBetweenPeriod(cursor, 0L));

            if (startIndex + size < pib.size()) {
                return pib.subList(startIndex, startIndex + size).stream().map(
                        select -> novelManagerService.viewNovelWithPreference(select.getNovelId(), select.preferenceCount)).collect(Collectors.toList());
            } else {
                return pib.subList(startIndex, pib.size()).stream().map(
                        select -> novelManagerService.viewNovelWithPreference(select.getNovelId(), select.preferenceCount)).collect(Collectors.toList());
            }
        }
    }

}
@Getter
class PreferenceInfoBetweenPeriod implements Comparable<PreferenceInfoBetweenPeriod> {
    Long novelId;
    Long preferenceCount;

    public PreferenceInfoBetweenPeriod(Long novelId, Long preferenceCount) {
        this.novelId = novelId;
        this.preferenceCount = preferenceCount;
    }

    public void increase() {
        this.preferenceCount++;
    }

    @Override
    public int compareTo(PreferenceInfoBetweenPeriod o) {
        return (int) (this.preferenceCount - o.preferenceCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }
        if (!(obj instanceof PreferenceInfoBetweenPeriod)) {
            return false;
        }
        PreferenceInfoBetweenPeriod that = (PreferenceInfoBetweenPeriod) obj;
        if (this.novelId.equals(that.novelId)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(novelId);
    }
}




