package myproject.demo.novelViewModel.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NovelViewModelRepository extends JpaRepository<NovelViewModel, Long> {
    int ADULT_LIMIT= 18;


    @Query(value = "select  nvm.* " +
            " from  novel_view_model nvm " +
            " order by nvm.preference_count DESC  ", nativeQuery = true)
    List<NovelViewModel> findTop20(Pageable pageable);

    @Query("select  nvm" +
            " from  NovelViewModel nvm  where nvm.age>= " + ADULT_LIMIT +
            " order by nvm.preferenceCount DESC  ")
    List<NovelViewModel> findTop20Adult( Pageable pageable);

    @Query("select  nvm" +
            " from  NovelViewModel nvm left join TotalPreferenceCount  t" +
            " on nvm.age<" + ADULT_LIMIT +
            " where nvm.novelId=t.novelId" +
            " order by t.totalCount.totalCount DESC  ")
    List<NovelViewModel> findTop20NonAdult( Pageable pageable);

    @Query(value = "select nvm.* " +
            " from novel_view_model nvm left join total_preference_count t left join category_novel_relation c" +
            " on c.category_id=:categoryId on t.deleted=false" +
            " where nvm.novel_id=c.novel_id and nvm.novel_id=t.novel_id" +
            " order by t.total_count DESC", nativeQuery = true)
    List<NovelViewModel> findTop20ByCategory(@Param("categoryId") long categoryId, Pageable pageable);

    @Query(value = "select  nvm.* " +
            "from novel_view_model nvm left join total_preference_count  t left join category_novel_relation  c" +
            " on c.category_id=:categoryId " + " on nvm.age>="+ ADULT_LIMIT +
            " where nvm.novel_id=c.novel_id and  nvm.novel_id=t.novel_id" +
            " order by t.total_count DESC", nativeQuery = true)
    List<NovelViewModel> findTop20AdultByCategory( @Param("categoryId") long categoryId, Pageable pageable);


    @Query(value = "select  nvm.* " +
            "from novel_view_model nvm left join total_preference_count  t left join category_novel_relation  c" +
            " on c.category_id=:categoryId" + " on nvm.age< "+ ADULT_LIMIT +
            " where nvm.novel_id=c.novel_id and  nvm.novel_id=t.novel_id" +
            " order by t.total_count DESC", nativeQuery = true)
    List<NovelViewModel> findTop20NonAdultByCategory(
            @Param("categoryId") long categoryId, Pageable pageable);




    @Query(value =
            "select " +
                    "pre.novel_id," +
                    "pre.title," +
                    "pre.author_name," +
                    "pre.cover_image_url," +
                    "pre.description," +
                    "pre.deleted," +
                    "pre.age," +
                    "pre.premium," +
                    "pre.update_time," +
                    "pre.cnt," +
                    "pre.book_mark_count," +
                    "pre.view," +
                    "pre.episode_number  " +
                    "from (" +
                    "   select result.*, (@ROWNUM:=@ROWNUM+1) as row" +
                    "       from (" +
                    "           select  nvm.*, count(*) as cnt " +
                    "           from novel_view_model nvm left join preference_info p" +
                    "           on p.preference_time>=:from and p.deleted=false " +
                    "           group by nvm.novel_id order by cnt desc " +
                    "       ) as `result`, (select @ROWNUM :=0) as R" +
                    ") as `pre` where pre.row>(select row from pre where pre.novel_id=:cursor limit :size )"
            , nativeQuery = true)

    List<NovelViewModel> findRankAll(
            @Param("from") LocalDateTime from,
            @Param("cursor") long cursor,
            @Param("size") int size);

    @Query(value =
            "select " +
                    "pre.novel_id," +
                    "pre.title," +
                    "pre.author_name," +
                    "pre.cover_image_url," +
                    "pre.description," +
                    "pre.deleted," +
                    "pre.age," +
                    "pre.premium," +
                    "pre.update_time," +
                    "pre.cnt," +
                    "pre.book_mark_count," +
                    "pre.view," +
                    "pre.episode_number  " +
                "from (select result.*,(@ROW:=@ROW+1) as row" +
                    " from (" +
                    "   select  nvm.*, count(*) as cnt" +
                    "   from novel_view_model nvm left join preference_info p" +
                    "   on nvm.age>=" + ADULT_LIMIT +" and p.preference_time>=:from and p.deleted=false" +
                    "   where nvm.novel_id=p.novel_id" +
                    "   group by nvm.novel_id order by cnt desc" +
                    ") as `result`, (select @ROW:= 0) as R " +
                ") as `pre`" +
                " where pre.row>(select row from pre where pre.novel_id=:cursor limit :size)"
            , nativeQuery = true)
    List<NovelViewModel> findRankAdult(
            @Param("from") LocalDateTime from,
            @Param("cursor") long cursor,
            @Param("size") int size);

    @Query(value =
            "select " +
                    "pre.novel_id," +
                    "pre.title," +
                    "pre.author_name," +
                    "pre.cover_image_url," +
                    "pre.description," +
                    "pre.deleted," +
                    "pre.age," +
                    "pre.premium," +
                    "pre.update_time," +
                    "pre.cnt," +
                    "pre.book_mark_count," +
                    "pre.view," +
                    "pre.episode_number  " +
                    "from ("+
                    "select result.*, (@ROWNUM:=@ROWNUM+1) as row" +
                    " from ("+
                    "           select nvm.*,count(*) as cnt " +
                    "           from novel_view_model nvm left join preference_info p" +
                    "           on nvm.age<" +ADULT_LIMIT+" and p.preference_time>=:from and p.deleted=false  " +
                    "           where nvm.novel_id=p.novel_id " +
                    "           group by nvm.novel_id order by cnt desc " +
                    "       ) as `result`, (select @ROWNUM:=0) as R" +
                    ") as `pre` where pre.row>(select row from pre where pre.novel_id=:cursor limit:size)"
            , nativeQuery = true)
    List<NovelViewModel> findRankNonAdult(
            @Param("from") LocalDateTime from,
            @Param("cursor") long cursor,
            @Param("size") int size);

    @Query(value =
            "select " +
                    "pre.novel_id," +
                    "pre.title," +
                    "pre.author_name," +
                    "pre.cover_image_url," +
                    "pre.description," +
                    "pre.deleted," +
                    "pre.age," +
                    "pre.premium," +
                    "pre.update_time," +
                    "pre.cnt," +
                    "pre.book_mark_count," +
                    "pre.view," +
                    "pre.episode_number  " +
                    "from (" +
                    "   select result.*, (@ROWNUM :=@ROWNUM+1) as row" +
                    "       from (" +
                    "           select  nvm.*, count(*) as cnt " +
                    "           from novel_view_model nvm left join category_novel_relation cnr left join preference_info p" +
                    "           on (cnr.deleted=false and cnr.category_id=:categoryId) on (p.preference_time>=:from and p.deleted=false) " +
                    "           where  nvm.novel_id=p.novel_id group by nvm.novel_id order by cnt desc " +
                    "       )   as `result`, (select @ROWNUM :=0) as R" +
                    ") as `pre` where pre.row>(select row from pre where pre.novel_id=:cursor limit:size)"
            , nativeQuery = true)
    List<NovelViewModel> findRankAllByCategory(@Param("from") LocalDateTime from,
                                               @Param("categoryId") long categoryId,
                                               @Param("cursor") long cursor,
                                               @Param("size") int size);

    @Query(value =
            "select " +
                    "pre.novel_id," +
                    "pre.title," +
                    "pre.author_name," +
                    "pre.cover_image_url," +
                    "pre.description," +
                    "pre.deleted," +
                    "pre.age," +
                    "pre.premium," +
                    "pre.update_time," +
                    "pre.cnt," +
                    "pre.book_mark_count," +
                    "pre.view," +
                    "pre.episode_number  " +
                    "from (" +
                    "   select result.*, (@ROWNUM :=@ROWNUM+1) as row" +
                    "       from (" +
                    "           select  nvm.*, count(*) as cnt " +
                    "           from novel_view_model nvm left join category_novel_relation cnr left join preference_info p " +
                    "           on nvm.age>= " + ADULT_LIMIT+ " on (cnr.deleted=false and cnr.category_id=:categoryId) and (p.preference_time>=:from and p.deleted=false) " +
                    "           where nvm.novel_id=p.novel_id and nvm.novel_id=cnr.novel_id" +
                    "           group by nvm.novel_id order by cnt desc )" +
                    "        as `result`, (select @ROWNUM :=0) as R" +
                    ") as `pre` where pre.row>(select row from pre where pre.novel_id=:cursor limit:size)"
            , nativeQuery = true)
    List<NovelViewModel> findRankAdultByCategory(
            @Param("from") LocalDateTime from,
            @Param("categoryId") long categoryId,
            @Param("cursor") long cursor,
            @Param("size") int size);



    @Query(value =
            "select " +
                    "pre.novel_id," +
                    "pre.title," +
                    "pre.author_name," +
                    "pre.cover_image_url," +
                    "pre.description," +
                    "pre.deleted," +
                    "pre.age," +
                    "pre.premium," +
                    "pre.update_time," +
                    "pre.cnt," +
                    "pre.book_mark_count," +
                    "pre.view," +
                    "pre.episode_number  " +
                    "from (" +
                    "   select result.*, (@ROWNUM :=@ROWNUM+1) as row" +
                    "       from (" +
                    "           select  nvm.*, count(*) as cnt" +
                    "           from novel_view_model nvm left join category_novel_relation cnr left join preference_info p" +
                    "           on nvm.age< " +ADULT_LIMIT +" on (cnr.category_id=:categoryId and cnr.deleted=false and p.preference_time>=:from and p.deleted=false) where nvm.novel_id=p.novel_id" +
                    "           group by nvm.novel_id order by cnt desc )" +
                    "        as `result`, (select @ROWNUM :=0) as R" +
                    ") as `pre` where pre.row>(select row from pre where pre.novel_id=:cursor limit:size)"
            , nativeQuery = true)
    List<NovelViewModel> findRankNonAdultByCategory(
            @Param("from") LocalDateTime from,
            @Param("categoryId") long categoryId,
            @Param("cursor") long cursor,
            @Param("size") int size);

}
