package myproject.demo.manager.viewManager.all.domain;

import myproject.demo.novelViewModel.domain.NovelViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllNovelViewModelRepository extends JpaRepository<NovelViewModel, Long> {


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "        select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "        from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.view desc ) as `pre`) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "                                                select semi.viewRaking\n" +
            "                                                from\n" +
            "                                                (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "                                                from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.view desc ) as pre) as semi\n" +
            "                                                where semi.novel_id = :cursor)\n" +
            "                                                else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInViewOrder(Long cursor, int size);




    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count from novel_view_model nvm left outer join  total_preference_count tpc on tpc.deleted=false and nvm.deleted=false where tpc.novel_id=nvm.novel_id order by tpc.total_count desc ) as `pre`) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count from novel_view_model nvm left outer join  total_preference_count tpc on tpc.deleted=false and nvm.deleted=false where tpc.novel_id=nvm.novel_id order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInPreferenceOrder(Long cursor, int size);




    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.update_time desc ) as `pre`) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findAllInDateOrder(Long cursor, int size);



    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeContRanking> case when :cursor>0 then (\n" +
            "    select semi.episodeContRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeContRanking , pre.*\n" +
            "         from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findAllInEpisodeCountOrder(Long cursor, int size);


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.book_mark_count desc ) as `pre`) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select * from novel_view_model nvm where nvm.deleted=false order by nvm.book_mark_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size", nativeQuery = true)
    List<NovelViewModel> findAllInBookMarkCountOrder(Long cursor, int size);

    
    
    
    
    
    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join  category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "                where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.view desc ) as `pre`) as semi\n" +
            "where semi.viewRaking> case when :cursor>0 then (\n" +
            "    select semi.viewRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.view desc ) as viewRaking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.view desc) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInViewOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count from novel_view_model nvm\n" +
            "             left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                 on tpc.deleted=false on cnr.category_id =:categoryId and nvm.deleted=false\n" +
            "                  where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id order by tpc.total_count desc ) as `pre`) as semi\n" +
            "where semi.preferenceRaking> case when :cursor>0 then (\n" +
            "    select semi.preferenceRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.total_count desc ) as preferenceRaking , pre.*\n" +
            "         from (select nvm.*, tpc.total_count from novel_view_model nvm\n" +
            "             left join category_novel_relation cnr left join  total_preference_count tpc\n" +
            "                 on tpc.deleted=false on cnr.category_id =:categoryId and nvm.deleted=false\n" +
            "                 where tpc.novel_id=nvm.novel_id and cnr.novel_id=nvm.novel_id order by tpc.total_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInPreferenceOrderWithCategory(Long categoryId, Long cursor, int size);

    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.update_time desc  ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join  category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.update_time desc  ) as `pre`) as semi\n" +
            "where semi.updateTimeRaking> case when :cursor>0 then (\n" +
            "    select semi.updateTimeRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.update_time desc ) as updateTimeRaking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.update_time desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                            else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInDateOrderWithCategory(Long categoryId, Long cursor, int size);


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.episode_number desc ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join  category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.episode_number desc ) as `pre`) as semi\n" +
            "where semi.episodeCountRaking> case when :cursor>0 then (\n" +
            "    select semi.episodeCountRaking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.episode_number desc  ) as episodeCountRaking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.episode_number desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                  else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInEpisodeCountOrderWithCategory(Long categoryId, Long cursor, int size);


    @Query(value = "select semi.*\n" +
            "from (\n" +
            "         select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join  category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.book_mark_count desc ) as `pre`) as semi\n" +
            "where semi.bookmarkCountRanking> case when :cursor>0 then (\n" +
            "    select semi.bookmarkCountRanking\n" +
            "    from\n" +
            "        (select ROW_NUMBER() over (ORDER BY pre.book_mark_count desc  ) as bookmarkCountRanking , pre.*\n" +
            "         from (select nvm.* from novel_view_model nvm left outer join category_novel_relation cnr on cnr.deleted=false and cnr.category_id=:categoryId\n" +
            "               where cnr.novel_id = nvm.novel_id and nvm.deleted=false order by nvm.book_mark_count desc ) as pre) as semi\n" +
            "    where semi.novel_id = :cursor)\n" +
            "                                    else 0 end\n" +
            "limit :size;", nativeQuery = true)
    List<NovelViewModel> findAllInBookMarkCountOrderWithCategory(Long categoryId, Long cursor, int size);
}
