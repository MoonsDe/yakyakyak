package com.example.yakyakyak.util

import com.example.yakyakyak.data.Medication

object DrugInteractionChecker {

    // 아세트아미노펜 계열 (중복 복용 시 간 독성)
    private val ACETAMINOPHEN = listOf("타이레놀", "판콜", "판피린", "화이투벤", "써스펜", "세토펜")

    // 항히스타민 계열 (중복 복용 시 졸음·어지럼 심화)
    private val ANTIHISTAMINE = listOf("지르텍", "클라리틴", "씨잘", "알레그라", "판콜", "판피린", "화이투벤")

    // NSAIDs 계열 (중복 복용 시 위장 출혈 위험)
    private val NSAIDS = listOf("이부프로펜", "부루펜", "아스피린", "나프록센", "덱시부프로펜")

    // 카페인 고함량 (중복 시 심계항진·불면)
    private val HIGH_CAFFEINE = listOf("게보린", "박카스", "펜잘", "사리돈")

    fun check(medications: List<Medication>): List<String> {
        val warnings = mutableListOf<String>()
        val activeNames = medications.filter { it.isActive }.map { it.name }

        fun findMatches(group: List<String>): List<String> =
            activeNames.filter { name ->
                group.any { keyword -> name.contains(keyword, ignoreCase = true) }
            }

        val acetaminophenMatches = findMatches(ACETAMINOPHEN)
        if (acetaminophenMatches.size >= 2) {
            warnings.add(
                "${acetaminophenMatches.joinToString(", ")}은(는) 아세트아미노펜 성분이 중복될 수 있어 " +
                "하루 최대 용량을 초과하면 간 독성 위험이 있습니다."
            )
        }

        val antihistamineMatches = findMatches(ANTIHISTAMINE)
        if (antihistamineMatches.size >= 2) {
            warnings.add(
                "${antihistamineMatches.joinToString(", ")}은(는) 항히스타민 성분이 중복되어 " +
                "졸음·어지러움이 심해질 수 있습니다."
            )
        }

        val nsaidMatches = findMatches(NSAIDS)
        if (nsaidMatches.size >= 2) {
            warnings.add(
                "${nsaidMatches.joinToString(", ")}은(는) 소염진통 성분이 중복되어 " +
                "위장 장애 및 출혈 위험이 높아질 수 있습니다."
            )
        }

        val caffeineMatches = findMatches(HIGH_CAFFEINE)
        if (caffeineMatches.size >= 2) {
            warnings.add(
                "${caffeineMatches.joinToString(", ")}은(는) 카페인 함량이 중복되어 " +
                "심계항진·불면이 생길 수 있습니다."
            )
        }

        return warnings
    }
}
