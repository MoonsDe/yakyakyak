package com.example.yakyakyak.ui.search

import com.example.yakyakyak.api.DrugItem

object CommonDrugs {
    val list: List<DrugItem> = listOf(
        DrugItem("타이레놀정500mg", "한국얀센", "두통, 치통, 발열, 근육통, 생리통, 관절통 완화", "성인 1회 1~2정, 1일 3~4회, 4~6시간 간격 복용. 공복에도 복용 가능.", "음주 시 복용 금지. 간 질환자 주의. 하루 최대 4g 초과 금지.", null, null, null, null),
        DrugItem("이부프로펜정400mg (부루펜)", "삼일제약", "해열, 소염, 두통, 치통, 생리통, 근육통, 관절통 완화", "성인 1회 1정, 1일 3회 식후 복용.", "위장 장애 주의. 식후 복용 권장. 임산부 복용 금지. 신장·심장 질환자 주의.", null, null, null, null),
        DrugItem("게보린정", "삼진제약", "두통, 치통, 생리통, 신경통, 근육통 완화", "성인 1회 1정, 1일 3회 복용.", "카페인 함유(무수카페인). 수면 전 복용 주의. 음주 시 복용 금지.", null, null, null, null),
        DrugItem("판콜에이내복액", "동화약품", "감기로 인한 발열, 콧물, 코막힘, 재채기, 인후통, 기침, 가래 완화", "성인 1회 30mL, 1일 3회 식후 복용.", "졸음 유발 가능. 운전·기계 조작 주의. 음주 금지.", null, null, null, null),
        DrugItem("판피린티정", "동아제약", "감기로 인한 발열, 콧물, 기침, 두통 완화", "성인 1회 1정, 1일 3회 식후 복용.", "졸음 유발. 운전 주의. 음주 금지.", null, null, null, null),
        DrugItem("화이투벤정", "유한양행", "감기로 인한 발열, 두통, 콧물, 코막힘, 기침, 가래 완화", "성인 1회 2정, 1일 3회 식후 복용.", "항히스타민제 함유로 졸음 유발 가능.", null, null, null, null),
        DrugItem("베아제정", "대웅제약", "소화불량, 식체, 위부 팽만감, 위부 불쾌감 개선", "성인 1회 1정, 1일 3회 식사 중 또는 식후 즉시 복용.", "소화효소제. 급성 췌장염 환자 복용 금지.", null, null, null, null),
        DrugItem("훼스탈플러스정", "한독약품", "소화불량, 식체, 위부 팽만감 개선", "성인 1회 1~2정, 1일 3회 식사 직후 복용.", "담즙 분비 부전 환자 주의.", null, null, null, null),
        DrugItem("까스활명수큐액", "동화약품", "소화불량, 식체, 구역, 구토, 위부 팽만감 완화", "성인 1회 1병(75mL), 1일 3회 복용.", "알코올 함유. 임산부·수유부 주의.", null, null, null, null),
        DrugItem("겔포스엠현탁액", "보령제약", "위산 과다, 속쓰림, 위부 팽만감, 위통 완화", "성인 1회 1포(11g), 1일 3~4회 식사 1~2시간 후 복용.", "신장 질환자 주의. 다른 약 복용 2시간 전후 사용.", null, null, null, null),
        DrugItem("잔탁정150mg (라니티딘)", "글락소스미스클라인", "위·십이지장 궤양, 위산 역류, 속쓰림 완화", "성인 1회 1정, 1일 2회 복용.", "신장 기능 저하 시 용량 조절 필요.", null, null, null, null),
        DrugItem("오트리빈점비액", "한국노바티스", "코막힘, 알레르기성 비염, 급성 비염 완화", "성인 1회 각 비공 2~3회 분무, 1일 3회 사용.", "3일 이상 연속 사용 금지. 심혈관 질환자 주의.", null, null, null, null),
        DrugItem("지르텍정(세티리진)", "유씨비코리아", "알레르기성 비염, 두드러기, 피부 가려움증 완화", "성인 1회 1정(10mg), 1일 1회 복용.", "졸음 유발. 운전 주의. 알코올 병용 금지.", null, null, null, null),
        DrugItem("클라리틴정(로라타딘)", "바이엘코리아", "알레르기성 비염, 두드러기, 가려움증 완화", "성인 1회 1정(10mg), 1일 1회 공복 또는 식사와 함께 복용.", "비졸음성 항히스타민제. 운전 영향 적음.", null, null, null, null),
        DrugItem("아로나민골드정", "일동제약", "육체 피로, 눈 피로, 신경통, 근육통, 관절통 완화 및 비타민B 보충", "성인 1회 1정, 1일 1~2회 식후 복용.", "뇨가 황색으로 변할 수 있음 (정상 반응).", null, null, null, null),
        DrugItem("임팩타민프리미엄정", "대웅제약", "육체 피로, 신경통, 근육통, 비타민B 보충", "성인 1회 1정, 1일 1회 식후 복용.", "고용량 비타민B1 함유.", null, null, null, null),
        DrugItem("우루사정100mg", "대웅제약", "간 기능 개선, 피로 회복, 담즙 분비 촉진", "성인 1회 1정, 1일 3회 식후 복용.", "담석 환자는 전문의와 상의 후 복용.", null, null, null, null),
        DrugItem("안티푸라민", "유한양행", "근육통, 관절통, 타박상, 요통, 어깨 결림 완화", "환부에 적당량 바르고 문지름. 1일 수회 사용.", "눈·점막·상처 부위 사용 금지. 밀봉 금지.", null, null, null, null),
        DrugItem("신신파스아렉스", "신신제약", "근육통, 요통, 관절통, 타박상, 어깨 결림 완화", "환부에 1일 1~2회 부착.", "습진·발진 부위 사용 금지. 알레르기 반응 시 즉시 제거.", null, null, null, null),
        DrugItem("포타디연질캡슐(비타민E)", "종근당", "비타민E 결핍 예방, 노화 방지, 항산화 작용", "성인 1회 1캡슐, 1일 1회 식후 복용.", "지용성 비타민이므로 과다 복용 주의.", null, null, null, null),
        DrugItem("비타민C 500mg", "유한양행", "비타민C 보충, 항산화, 피부 건강 유지", "성인 1회 1정, 1일 1~3회 복용.", "과량 복용 시 설사, 위장 장애 가능.", null, null, null, null),
        DrugItem("센트룸", "한국화이자", "종합 비타민·미네랄 보충", "성인 1회 1정, 1일 1회 식사와 함께 복용.", "권장량 초과 복용 금지.", null, null, null, null),
        DrugItem("둘코락스정(비사코딜)", "한국사노피", "변비 완화", "성인 1회 1~2정, 취침 전 복용.", "장기 복용 금지. 복통 시 복용 중지. 우유와 함께 복용 금지.", null, null, null, null),
        DrugItem("정로환", "동아제약", "설사, 식체로 인한 복통, 소화불량 완화", "성인 1회 9~12환, 1일 3회 식전 또는 식간 복용.", "크레오소트 함유. 임산부 주의.", null, null, null, null),
        DrugItem("스트렙실", "레킷벤키저", "인후 통증, 구강 내 세균 억제", "성인 1회 1정, 2~3시간 간격 천천히 녹임. 1일 최대 8정.", "3세 미만 사용 금지. 5일 이상 사용 시 전문의 상담.", null, null, null, null),
        DrugItem("박카스디", "동아제약", "피로 회복, 간 보호 보조", "1회 1병(100mL), 1일 1~3회 음용.", "카페인 함유(30mg/병). 수면 전 다량 복용 주의.", null, null, null, null),
        DrugItem("에어본정", "한미약품", "비타민C·아연 보충, 면역력 지원", "물 한 컵에 1정 녹여 1일 1회 복용.", "신장 결석 병력자 주의.", null, null, null, null),
        DrugItem("마데카솔연고", "동국제약", "찰과상, 화상, 욕창, 피부 궤양 치료·재생 촉진", "환부를 깨끗이 하고 1일 1~2회 얇게 도포.", "눈에 접촉 금지. 감염 부위는 의사 지시에 따라 사용.", null, null, null, null),
        DrugItem("후시딘연고", "동화약품", "피부 세균 감염, 화농성 피부 질환 치료", "환부에 1일 1~3회 얇게 도포 후 거즈로 덮음.", "장기 사용 시 내성 유발 가능.", null, null, null, null),
        DrugItem("아즈렌양치액", "부광약품", "인후염, 편도염, 치은염에 의한 인후·구강 내 소염 완화", "1일 수회 적당량을 입에 머금고 30초 이상 가글 후 뱉음.", "삼키지 말 것.", null, null, null, null),
    )

    private val CHOSEONGS = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ"

    fun getChoseong(c: Char): Char? {
        if (c < '가' || c > '힣') return null
        return CHOSEONGS[(c - '가') / (21 * 28)]
    }

    fun filterByChoseong(cho: Char): List<DrugItem> {
        return list.filter { item ->
            val first = item.name?.firstOrNull() ?: return@filter false
            getChoseong(first) == cho
        }
    }

    fun filter(query: String): List<DrugItem> {
        if (query.isBlank()) return list
        val q = query.trim().lowercase()
        return list.filter {
            it.name?.lowercase()?.contains(q) == true ||
            it.efficacy?.lowercase()?.contains(q) == true ||
            it.company?.lowercase()?.contains(q) == true
        }
    }
}
