package myproject.demo.manager.viewManager.free.domain;

import myproject.demo.novelViewModel.domain.NovelViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeNovelViewManagerRepository extends JpaRepository<NovelViewModel, Long> {

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.deleted=false and nvm.premium=false\n" +
            "              order by nvm.view desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.deleted=false and nvm.premium=false\n" +
            "              order by nvm.view desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInViewOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "              from novel_view_model nvm\n" +
            "                  left join  total_preference_count tpc\n" +
            "                      on tpc.deleted=false and nvm.deleted=false and nvm.premium=false\n" +
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
            "                       on tpc.deleted=false and nvm.deleted=false and nvm.premium=false\n" +
            "               where tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInPreferenceOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.premium=false and nvm.deleted=false\n" +
            "              order by nvm.update_time desc ) as `pre`\n" +
            "         ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where  nvm.premium=false and nvm.deleted=false\n" +
            "               order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInDateOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false\n" +
            "               order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "              from novel_view_model nvm\n" +
            "              where nvm.premium=false and nvm.deleted=false\n" +
            "              order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;\n", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInEpisodeCountOrder(Long cursor, int size);


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "        ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false\n" +
            "               order by nvm.book_mark_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInBookMarkCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left join  category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
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
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
            "               where cnr.novel_id = nvm.novel_id order by nvm.view desc) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "              from novel_view_model nvm\n" +
            "                  left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                      on tpc.deleted=false on cnr.category_id =:categoryId and nvm.deleted=false and nvm.premium=false\n" +
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
            "                       on tpc.deleted=false on cnr.category_id =:categoryId and nvm.deleted=false and nvm.premium=false\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre\n" +
            "         ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left outer join  category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
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
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInDateOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                   left join  category_novel_relation cnr\n" +
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
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
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "              from novel_view_model nvm\n" +
            "                  left join  category_novel_relation cnr\n" +
            "                      on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
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
            "                       on cnr.deleted=false and cnr.category_id=:categoryId and nvm.deleted=false and nvm.premium=false\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc\n" +
            "         ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllFreeNovelInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=false and nvm.age>=18\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=false and nvm.age>=18\n" +
            "               order by nvm.view desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;\n", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInViewOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  total_preference_count tpc\n" +
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=false and nvm.age>=18\n" +
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
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=false and nvm.age>=18\n" +
            "               where tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInPreferenceOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.update_time desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where  nvm.premium=false and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInDateOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInEpisodeCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age>=18\n" +
            "               order by nvm.book_mark_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInBookMarkCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=false and nvm.age>=18\n" +
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
            "                                         and nvm.premium=false\n" +
            "                                         and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id order by nvm.view desc) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                            on tpc.deleted=false\n" +
            "                            on cnr.category_id =:categoryId\n" +
            "                                   and nvm.deleted=false\n" +
            "                                   and nvm.premium=false\n" +
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
            "                                   and nvm.premium=false\n" +
            "                                   and nvm.age>=18\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join  category_novel_relation cnr\n" +
            "                                         on cnr.deleted=false\n" +
            "                                                and cnr.category_id=:categoryId\n" +
            "                                                and nvm.deleted=false\n" +
            "                                                and nvm.premium=false\n" +
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
            "                                               and nvm.premium=false\n" +
            "                                               and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInDateOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=false\n" +
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
            "                                               and nvm.premium=false\n" +
            "                                               and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=false\n" +
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
            "                                               and nvm.premium=false\n" +
            "                                               and nvm.age>=18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc\n" +
            "              ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAdultFreeNovelInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);








    ///////////// non adult

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=false and nvm.age<18\n" +
            "               order by nvm.view desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.deleted=false and nvm.premium=false and nvm.age<18\n" +
            "               order by nvm.view desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;\n", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInViewOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  total_preference_count tpc\n" +
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=false and nvm.age<18\n" +
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
            "                                   on tpc.deleted=false and nvm.deleted=false and nvm.premium=false and nvm.age<18\n" +
            "               where tpc.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInPreferenceOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.update_time desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where  nvm.premium=false and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInDateOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInEpisodeCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.book_mark_count desc ) as `pre`\n" +
            "     ) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select *\n" +
            "               from novel_view_model nvm\n" +
            "               where nvm.premium=false and nvm.deleted=false and nvm.age<18\n" +
            "               order by nvm.book_mark_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInBookMarkCountOrder(Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=false and nvm.age<18\n" +
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
            "                                         and nvm.premium=false\n" +
            "                                         and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id order by nvm.view desc) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count\n" +
            "               from novel_view_model nvm\n" +
            "                        left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                            on tpc.deleted=false\n" +
            "                            on cnr.category_id =:categoryId\n" +
            "                                   and nvm.deleted=false\n" +
            "                                   and nvm.premium=false\n" +
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
            "                                   and nvm.premium=false\n" +
            "                                   and nvm.age<18\n" +
            "               where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id\n" +
            "               order by tpc.total_count desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left outer join  category_novel_relation cnr\n" +
            "                                         on cnr.deleted=false\n" +
            "                                                and cnr.category_id=:categoryId\n" +
            "                                                and nvm.deleted=false\n" +
            "                                                and nvm.premium=false\n" +
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
            "                                               and nvm.premium=false\n" +
            "                                               and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.update_time desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInDateOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=false\n" +
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
            "                                               and nvm.premium=false\n" +
            "                                               and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.episode_number desc ) as pre\n" +
            "        ) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.*\n" +
            "               from novel_view_model nvm\n" +
            "                        left join  category_novel_relation cnr\n" +
            "                                   on cnr.deleted=false\n" +
            "                                          and cnr.category_id=:categoryId\n" +
            "                                          and nvm.deleted=false\n" +
            "                                          and nvm.premium=false\n" +
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
            "                                               and nvm.premium=false\n" +
            "                                               and nvm.age<18\n" +
            "               where cnr.novel_id = nvm.novel_id\n" +
            "               order by nvm.book_mark_count desc\n" +
            "              ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                      else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findNonAdultFreeNovelInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);


}
