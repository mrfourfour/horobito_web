package myproject.demo.manager.viewManager.premium.domain;

import myproject.demo.novelViewModel.domain.NovelViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PremiumNovelViewModelRepository extends JpaRepository<NovelViewModel, Long> {


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.deleted=false and nvm.premium=true\n" +
            "              order by nvm.view desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.deleted=false and nvm.premium=true\n" +
            "              order by nvm.view desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInViewOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "              from novel_view_model nvm\n" +
            "                  left join  total_preference_count tpc\n" +
            "                      on tpc.deleted=false and nvm.deleted=false and nvm.premium=true\n" +
            "               where  tpc.novel_id=nvm.novel_id\n" +
            "              order by tpc.total_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                   left join  total_preference_count tpc\n" +
            "                       on tpc.deleted=false and nvm.deleted=false and nvm.premium=true\n" +
            "               where tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInPreferenceOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.premium=true and nvm.deleted=false\n" +
            "              order by nvm.update_time desc ) as `pre`\n" +
            "         ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where  nvm.premium=true and nvm.deleted=false\n" +
            "               order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInDateOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false\n" +
            "               order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.premium=true and nvm.deleted=false\n" +
            "              order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;\n", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInEpisodeCountOrder(Long cursor, int size);


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false\n" +
            "               order by nvm.book_mark_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInBookMarkCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left join  category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left join category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id order by nvm.view desc) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "              from novel_view_model nvm\n" +
            "                  left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                      on tpc.deleted=false on cnr.category_id =:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                   left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                       on tpc.deleted=false on cnr.category_id =:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre\n" +
            "         ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left outer join  category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc  ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left outer join category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInDateOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left join  category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as `pre`\n" +
            "         ) as semi\n" +
            "where semi.episodeCountRaking> case when :cursor>0 then (\n" +
            "    select semi.episodeCountRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc  ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left outer join category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "              from novel_view_model nvm\n" +
            "                  left join  category_novel_relation cnr\n" +
            "                      on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc  ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left outer join category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=true\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc\n" +
            "         ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllPremiumNovelInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=true and nvm.age>=18\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=true and nvm.age>=18\n" +
            "               order by nvm.view desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;\n", nativeQuery = true)
    List<NovelViewModel> findAdultPremiumNovelInViewOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  total_preference_count tpc\n" +
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=true and nvm.age>=18\n" +
            "               where  tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  total_preference_count tpc\n" +
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=true and nvm.age>=18\n" +
            "               where tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultPremiumNovelInPreferenceOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.update_time desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where  nvm.premium=true and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultPremiumNovelInDateOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultPremiumNovelInEpisodeCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
                "               where nvm.premium=true and nvm.deleted=false and nvm.age>=18\n" +
                "               order by nvm.book_mark_count desc ) as pre\n" +
                "        ) as semi\n" +
                "    where semi.novel_id = :cursor)\n" +
                "                                      else 0 end\n" +
                "limit :size", nativeQuery = true)
    List<NovelViewModel> findAdultPremiumNovelInBookMarkCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=true and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr\n" +
            "                                  on cnr.deleted=false\n" +
            "                                         and cnr.category_id=:categoryId\n" +
            "                                         and nvm.deleted=false\n" +
            "                                         and nvm.premium=true\n" +
            "                                         and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id order by nvm.view desc) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultNovelInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                            on tpc.deleted=false\n" +
            "                            on cnr.category_id =:categoryId\n" +
            "                                   and nvm.deleted=false\n" +
            "                                   and nvm.premium=true\n" +
            "                                   and nvm.age>=18\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                            on tpc.deleted=false\n" +
            "                            on cnr.category_id =:categoryId\n" +
            "                                   and nvm.deleted=false\n" +
            "                                   and nvm.premium=true\n" +
            "                                   and nvm.age>=18\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultNovelInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join  category_novel_relation cnr\n" +
            "                                         on cnr.deleted=false\n" +
            "                                                and cnr.category_id=:categoryId\n" +
            "                                                and nvm.deleted=false\n" +
            "                                                and nvm.premium=true\n" +
            "                                                and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc  ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join category_novel_relation cnr\n" +
            "                                        on cnr.deleted=false\n" +
            "                                               and cnr.category_id=:categoryId\n" +
            "                                               and nvm.deleted=false\n" +
            "                                               and nvm.premium=true\n" +
            "                                               and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultNovelInDateOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=true\n" +
            "                                          and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.episodeCountRaking> case when :cursor>0 then (\n" +
            "    select semi.episodeCountRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc  ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join category_novel_relation cnr\n" +
            "                                        on cnr.deleted=false\n" +
            "                                               and cnr.category_id=:categoryId\n" +
            "                                               and nvm.deleted=false\n" +
            "                                               and nvm.premium=true\n" +
            "                                               and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultNovelInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=true\n" +
            "                                          and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc  ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join category_novel_relation cnr\n" +
            "                                        on cnr.deleted=false\n" +
            "                                               and cnr.category_id=:categoryId\n" +
            "                                               and nvm.deleted=false\n" +
            "                                               and nvm.premium=true\n" +
            "                                               and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc\n" +
            "              ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultNovelInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);








    ///////////// non adult

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=true and nvm.age<18\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=true and nvm.age<18\n" +
            "               order by nvm.view desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;\n", nativeQuery = true)
    List<NovelViewModel> findNonAdultPremiumNovelInViewOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  total_preference_count tpc\n" +
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=true and nvm.age<18\n" +
            "               where  tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  total_preference_count tpc\n" +
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=true and nvm.age<18\n" +
            "               where tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultPremiumNovelInPreferenceOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.update_time desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where  nvm.premium=true and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultPremiumNovelInDateOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultPremiumNovelInEpisodeCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=true and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.book_mark_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findNonAdultPremiumNovelInBookMarkCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=true and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr\n" +
            "                                  on cnr.deleted=false\n" +
            "                                         and cnr.category_id=:categoryId\n" +
            "                                         and nvm.deleted=false\n" +
            "                                         and nvm.premium=true\n" +
            "                                         and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id order by nvm.view desc) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultNovelInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                            on tpc.deleted=false\n" +
            "                            on cnr.category_id =:categoryId\n" +
            "                                   and nvm.deleted=false\n" +
            "                                   and nvm.premium=true\n" +
            "                                   and nvm.age<18\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                            on tpc.deleted=false\n" +
            "                            on cnr.category_id =:categoryId\n" +
            "                                   and nvm.deleted=false\n" +
            "                                   and nvm.premium=true\n" +
            "                                   and nvm.age<18\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultNovelInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join  category_novel_relation cnr\n" +
            "                                         on cnr.deleted=false\n" +
            "                                                and cnr.category_id=:categoryId\n" +
            "                                                and nvm.deleted=false\n" +
            "                                                and nvm.premium=true\n" +
            "                                                and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc  ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join category_novel_relation cnr\n" +
            "                                        on cnr.deleted=false\n" +
            "                                               and cnr.category_id=:categoryId\n" +
            "                                               and nvm.deleted=false\n" +
            "                                               and nvm.premium=true\n" +
            "                                               and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultNovelInDateOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=true\n" +
            "                                          and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.episodeCountRaking> case when :cursor>0 then (\n" +
            "    select semi.episodeCountRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc  ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join category_novel_relation cnr\n" +
            "                                        on cnr.deleted=false\n" +
            "                                               and cnr.category_id=:categoryId\n" +
            "                                               and nvm.deleted=false\n" +
            "                                               and nvm.premium=true\n" +
            "                                               and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultNovelInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=true\n" +
            "                                          and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc  ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join category_novel_relation cnr\n" +
            "                                        on cnr.deleted=false\n" +
            "                                               and cnr.category_id=:categoryId\n" +
            "                                               and nvm.deleted=false\n" +
            "                                               and nvm.premium=true\n" +
            "                                               and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc\n" +
            "              ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultNovelInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);


}
