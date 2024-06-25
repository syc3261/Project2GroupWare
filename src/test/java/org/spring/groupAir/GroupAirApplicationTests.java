package org.spring.groupAir;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.spring.groupAir.airplane.entity.AirPlaneEntity;
import org.spring.groupAir.airplane.entity.QAirPlaneEntity;
import org.spring.groupAir.commute.entity.QCommuteEntity;
import org.spring.groupAir.member.entity.MemberEntity;
import org.spring.groupAir.member.entity.QMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Configuration
class GroupAirApplicationTests {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void test() {

        QMemberEntity member = QMemberEntity.memberEntity;

        List<MemberEntity> memberEntityList = queryFactory.selectFrom(member).fetch();

        System.out.println("member: >>>" + member);
        System.out.println("memberEntityList: >>>" + memberEntityList);
    }

    @Test
    public void airPlaneList() {

        QAirPlaneEntity airPlane = QAirPlaneEntity.airPlaneEntity;

        List<AirPlaneEntity> airPlaneEntityList = queryFactory.selectFrom(airPlane).fetch();

        for (AirPlaneEntity airPlaneEntity : airPlaneEntityList) {
            System.out.print("id : " + airPlaneEntity.getId());
            System.out.print(" 출발지 : " + airPlaneEntity.getToArea());
            System.out.print(" 도착지 : " + airPlaneEntity.getFromArea());
            System.out.print(" 출발시간 : " + airPlaneEntity.getToTime());
            System.out.print(" 도착시간 : " + airPlaneEntity.getFromTime());
            System.out.println(" 비행기 : " + airPlaneEntity.getAirplane());
        }
    }

    @Test
    public void commuteList() {
        QCommuteEntity commute = QCommuteEntity.commuteEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        LocalDate now = LocalDate.now();

        int workOut = (int) queryFactory.select(commute.count())
            .from(commute)
            .leftJoin(commute.memberEntity, member)
            .where(commute.inTime.gt(now.atStartOfDay())
                .and(commute.status.eq("출근"))
                .and(commute.id.in(JPAExpressions.select(commute.id.max())
                    .from(commute)
                    .groupBy(commute.memberEntity.id))))
            .fetchCount();

        System.out.println(">>>>" + workOut);
    }
}
