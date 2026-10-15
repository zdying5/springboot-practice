package me.scpark.springbootdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository를 통해 member 테이블의 모든 레코드(3개) 가져오기")
    @Test
    public void getAllMembers() {
        // 준비 given

        // 실행 when
        List<Member> members = memberRepository.findAll();

        // 검증 then
        assertThat(members).hasSize(3);
    }

    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository를 통해 member 이름이 'C'인 레코드 가져오기")
    @Test
    public void getMemberByName() {
        // 준비 given

        // 실행 when
        Optional<Member> optMember = memberRepository.findByName("C");

        // 검증 then
        assertThat(optMember).isPresent();
        assertThat(optMember.get().getId()).isEqualTo(3);
    }

    // select * from member where id = ?
    @Sql("/insert-members.sql")
    @DisplayName("MemberRepository를 통해 Id가 2인 레코드 가져오기")
    @Test
    public void getMemberById() {
        // 준비 given

        // 실행 when
        Member member = memberRepository.findById(2L).orElseThrow();

        // 검증 then
        assertThat(member.getName()).isEqualTo("B");
    }

    @DisplayName("MemberRepository를 통해 이름이 '박성철'인 레코드 추가하기")
    @Test
    public void saveMember() {
        // 준비 given : Member 객체를 생성 (id는 DB가 생성)
        Member member = new Member("박성철");

        // 실행 when
        memberRepository.save(member);

        // 검증 then
        // member 테이블에서 모든 레코드를 select 했을 때 레코드의 수가 1이고
        // 그 레코드의 name 칼럼 값이 '박성철'인지 확인
        List<Member> result = memberRepository.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("박성철");
    }

    @Sql("/insert-members.sql")
    @DisplayName("테이블 member에서 id가 3인 레코드 삭제하기")
    @Test
    public void deleteMemberById() {
        // 준비 given

        // 실행 when
        memberRepository.deleteById(3L);

        // 검증 then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
        assertThat(members).extracting(Member::getId).doesNotContain(3L);
    }

    @Sql("/insert-members.sql")
    @DisplayName("테이블 member의 모든 레코드 삭제하기")
    @Test
    public void deleteAll() {
        // 준비 given
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(3);

        // 실행 when
        memberRepository.deleteAll();

        // 검증 then -> member 테이블에서 모든 레코드를 가져왔더니 하나도 없더라.
        assertThat(memberRepository.findAll()).isEmpty();
    }

    @DisplayName("테이블 member name이 'A', 'B'인 레코드를 한번에 삽입하기")
    @Test
    public void saveMembers(){
        // 준비 given
        // 두 개의 Member 객체를 생성해서 List에 넣는다.
        List<Member> members = List.of(new Member("A"), new Member("B"));

        // 실행 when
        memberRepository.saveAll(members);

        // 검증 then
        // 테이블에서 모든 레코드를 selection 했더니 갯수가 2개임을 검증
        assertThat(memberRepository.findAll()).hasSize(2);
    }

    @Sql("/insert-members.sql")
    @DisplayName("변경감지 기능을 통행 Id가 2인 레코드의 이름을 'B', 'BB'로 변경하기")
    // @Transactional
    @Test
    public void update() {
        // 준비 given
        Member member = memberRepository.findById(2L).get();

        // 실행 when
        // member.name = "BB";
        member.setName("BB");
        // memberRepository.save(member);

        // 검증 then
        member =  memberRepository.findById(2L).get();
        assertThat(member.getName()).isEqualTo("BB");


    }
}
