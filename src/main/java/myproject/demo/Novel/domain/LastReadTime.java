//package myproject.demo.Novel.domain;
//
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Embeddable;
//import java.time.LocalDateTime;
//
//@Embeddable
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class LastReadTime {
//
//    LocalDateTime lastReadTime;
//
//    private LastReadTime(LocalDateTime lastReadTime) {
//        this.lastReadTime = lastReadTime;
//    }
//
//    public static LastReadTime create(LocalDateTime lastReadTime){
//        return new LastReadTime(lastReadTime);
//    }
//}
