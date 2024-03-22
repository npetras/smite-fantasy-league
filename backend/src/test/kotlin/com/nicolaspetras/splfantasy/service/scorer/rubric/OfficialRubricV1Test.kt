package com.nicolaspetras.splfantasy.service.scorer.rubric

import com.nicolaspetras.splfantasy.model.SmiteRole
import com.nicolaspetras.splfantasy.model.SplTeamName
import com.nicolaspetras.splfantasy.model.stat.collection.SplPlayerStats
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Tests for the scoring calculation of the [OfficialRubricV1] scoring rubric.
 *
 * The stats for the test below were taken from "SMITE Pro League Road to Worlds Week 5 : Oni Warriors vs Styx Ferrymen"
 * Game 1 of that set, which was played on 21st October 2023.
 * YouTube link for view of Game 1 stats: https://youtu.be/tdmIpzRiq40?si=qMzj-AUS4JNatp_0&t=4469
 */
class OfficialRubricV1Test {

    private val officialRubricV1 = OfficialRubricV1()

    @Test
    fun `test solo laner stats - Baskin ferrymen vs warriors game 1`() {

        val baskinGame1Stats = SplPlayerStats(
            name = "Baskin",
            splTeam = SplTeamName.STYX,
            role = SmiteRole.SOLO,
            kills = 8,
            deaths = 3,
            assists = 0,
            goldPerMin = 544,
            playerDamage = 44292,
            mitigatedDamage = 28404,
            structureDamage = 1340,
            healing = 0,
            wards = 30
        )
        println(Json.encodeToString(baskinGame1Stats))
        val baskinGame1Score = officialRubricV1.calculatePlayerScore(baskinGame1Stats)
        val expectedScore: BigDecimal =
            (BigDecimal(baskinGame1Stats.kills) * BigDecimal("2")) +
                    (BigDecimal(baskinGame1Stats.deaths) * BigDecimal("-1")) +
                    (BigDecimal(baskinGame1Stats.assists) * BigDecimal("0.5")) +
                    (BigDecimal(baskinGame1Stats.mitigatedDamage) * BigDecimal("0.00005")) +
                    (BigDecimal(baskinGame1Stats.playerDamage) * BigDecimal("0.000075")) +
                    (BigDecimal(baskinGame1Stats.goldPerMin) * BigDecimal("0.001")) +
                    (BigDecimal(baskinGame1Stats.wards) * BigDecimal("0.1")) +
                    (BigDecimal(baskinGame1Stats.structureDamage) * BigDecimal("0.00025")) +
                    (BigDecimal(0)) + // deathless
                    (BigDecimal(baskinGame1Stats.healing) * BigDecimal("0.0001"))
        assertEquals(expectedScore, baskinGame1Score)
    }
    @Test
    fun `test mid laner stats - Paul ferrymen vs warriors game 1`() {

        val paulGame1Stats = SplPlayerStats(
            name = "Paul",
            splTeam = SplTeamName.STYX,
            role = SmiteRole.MID,
            kills = 0,
            deaths = 2,
            assists = 2,
            goldPerMin = 484,
            playerDamage = 11530,
            mitigatedDamage = 6037,
            structureDamage = 564,
            healing = 0,
            wards = 10
        )
        val paulGame1Score = officialRubricV1.calculatePlayerScore(paulGame1Stats)
        println(Json.encodeToString(paulGame1Stats))

        val expectedScore: BigDecimal =
            (BigDecimal(paulGame1Stats.kills) * BigDecimal("2.5")) +
                    (BigDecimal(paulGame1Stats.deaths) * BigDecimal("-1")) +
                    (BigDecimal(paulGame1Stats.assists) * BigDecimal("0.1")) +
                    (BigDecimal(paulGame1Stats.mitigatedDamage) * BigDecimal("0.00005")) +
                    (BigDecimal(paulGame1Stats.playerDamage) * BigDecimal("0.0001")) +
                    (BigDecimal(paulGame1Stats.goldPerMin) * BigDecimal("0.001")) +
                    (BigDecimal(paulGame1Stats.wards) * BigDecimal("0.1")) +
                    (BigDecimal(paulGame1Stats.structureDamage) * BigDecimal("0.00025")) +
                    (BigDecimal(0)) + // deathless
                    (BigDecimal(paulGame1Stats.healing) * BigDecimal("0.0001"))
        assertEquals(expectedScore.setScale(5, RoundingMode.UP), paulGame1Score.setScale(5, RoundingMode.UP))
    }

    @Test
    fun `test jungle stats - Cyno ferrymen vs warriors game 1`() {
        val cynoGame1Stats = SplPlayerStats(
            name = "Cyno",
            splTeam = SplTeamName.STYX,
            role = SmiteRole.JUNGLE,
            kills = 0,
            deaths = 2,
            assists = 7,
            goldPerMin = 506,
            playerDamage = 12_935,
            mitigatedDamage = 24_198,
            structureDamage = 735,
            healing = 0,
            wards = 12
        )
        val cynoGame1Score = officialRubricV1.calculatePlayerScore(cynoGame1Stats)
        println(Json.encodeToString(cynoGame1Stats))
        val expectedScore: BigDecimal =
            (BigDecimal(cynoGame1Stats.kills) * BigDecimal("2.5")) +
                    (BigDecimal(cynoGame1Stats.deaths) * BigDecimal("-1")) +
                    (BigDecimal(cynoGame1Stats.assists) * BigDecimal("0.2")) +
                    (BigDecimal(cynoGame1Stats.mitigatedDamage) * BigDecimal("0.00005")) +
                    (BigDecimal(cynoGame1Stats.playerDamage) * BigDecimal("0.0001")) +
                    (BigDecimal(cynoGame1Stats.goldPerMin) * BigDecimal("0.001")) +
                    (BigDecimal(cynoGame1Stats.wards) * BigDecimal("0.1")) +
                    (BigDecimal(cynoGame1Stats.structureDamage) * BigDecimal("0.00025")) +
                    (BigDecimal(0)) + // deathless
                    (BigDecimal(cynoGame1Stats.healing) * BigDecimal("0.0001"))
        assertEquals(expectedScore.setScale(5, RoundingMode.UP), cynoGame1Score.setScale(5, RoundingMode.UP))

    }

    @Test
    fun `test carry stats - CycloneSpin ferrymen vs warriors game 1`() {

        val cycloneGame1Stats = SplPlayerStats(
            name = "CycloneSpin",
            splTeam = SplTeamName.STYX,
            role = SmiteRole.CARRY,
            kills = 0,
            deaths = 2,
            assists = 5,
            goldPerMin = 475,
            playerDamage = 20819,
            mitigatedDamage = 14333,
            structureDamage = 5007,
            healing = 0,
            wards = 7
        )
        val cycloneGame1Score = officialRubricV1.calculatePlayerScore(cycloneGame1Stats)
        println(Json.encodeToString(cycloneGame1Stats))
        val expectedScore: BigDecimal =
            (BigDecimal(cycloneGame1Stats.kills) * BigDecimal("2.5")) +
                    (BigDecimal(cycloneGame1Stats.deaths) * BigDecimal("-1")) +
                    (BigDecimal(cycloneGame1Stats.assists) * BigDecimal("0.1")) +
                    (BigDecimal(cycloneGame1Stats.mitigatedDamage) * BigDecimal("0.00005")) +
                    (BigDecimal(cycloneGame1Stats.playerDamage) * BigDecimal("0.0001")) +
                    (BigDecimal(cycloneGame1Stats.goldPerMin) * BigDecimal("0.001")) +
                    (BigDecimal(cycloneGame1Stats.wards) * BigDecimal("0.1")) +
                    (BigDecimal(cycloneGame1Stats.structureDamage) * BigDecimal("0.00025")) +
                    (BigDecimal(0)) + // deathless
                    (BigDecimal(cycloneGame1Stats.healing) * BigDecimal("0.0001"))
        assertEquals(expectedScore.setScale(5, RoundingMode.UP), cycloneGame1Score.setScale(5, RoundingMode.UP))
    }

    @Test
    fun `test support stats - Aror ferrymen vs warriors game 1`() {

        val arorGame1Stats = SplPlayerStats(
            name = "Aror",
            splTeam = SplTeamName.STYX,
            role = SmiteRole.SUPPORT,
            kills = 2,
            deaths = 6,
            assists = 5,
            goldPerMin = 454,
            playerDamage = 9906,
            mitigatedDamage = 60833,
            structureDamage = 69,
            healing = 0,
            wards = 16
        )
        val arorGame1Score = officialRubricV1.calculatePlayerScore(arorGame1Stats)
        println(Json.encodeToString(arorGame1Stats))
        val expectedScore: BigDecimal =
            (BigDecimal(arorGame1Stats.kills) * BigDecimal("1.5")) +
                    (BigDecimal(arorGame1Stats.deaths) * BigDecimal("-1")) +
                    (BigDecimal(arorGame1Stats.assists) * BigDecimal("0.75")) +
                    (BigDecimal(arorGame1Stats.mitigatedDamage) * BigDecimal("0.000075")) +
                    (BigDecimal(arorGame1Stats.playerDamage) * BigDecimal("0.000075")) +
                    (BigDecimal(arorGame1Stats.goldPerMin) * BigDecimal("0.001")) +
                    (BigDecimal(arorGame1Stats.wards) * BigDecimal("0.1")) +
                    (BigDecimal(arorGame1Stats.structureDamage) * BigDecimal("0.00025")) +
                    (BigDecimal(0)) + // deathless
                    (BigDecimal(arorGame1Stats.healing) * BigDecimal("0.0001"))
        assertEquals(expectedScore.setScale(5, RoundingMode.UP), arorGame1Score.setScale(5, RoundingMode.UP))
    }

    @Test
    fun `test mid laner stats - Pegon ferrymen vs warriors game 1`() {

        val pegonGame1Stats = SplPlayerStats(
            name = "Pegon",
            splTeam = SplTeamName.ONI,
            role = SmiteRole.MID,
            kills = 7,
            deaths = 0,
            assists = 7,
            goldPerMin = 554,
            playerDamage = 44_144,
            mitigatedDamage = 17_990,
            structureDamage = 1_877,
            healing = 302,
            wards = 12
        )
        val pegonGame1Score = officialRubricV1.calculatePlayerScore(pegonGame1Stats)
        val expectedScore: BigDecimal =
            (BigDecimal(pegonGame1Stats.kills) * BigDecimal("2.5")) +
                    (BigDecimal(pegonGame1Stats.deaths) * BigDecimal("-1")) +
                    (BigDecimal(pegonGame1Stats.assists) * BigDecimal("0.1")) +
                    (BigDecimal(pegonGame1Stats.mitigatedDamage) * BigDecimal("0.00005")) +
                    (BigDecimal(pegonGame1Stats.playerDamage) * BigDecimal("0.0001")) +
                    (BigDecimal(pegonGame1Stats.goldPerMin) * BigDecimal("0.001")) +
                    (BigDecimal(pegonGame1Stats.wards) * BigDecimal("0.1")) +
                    (BigDecimal(pegonGame1Stats.structureDamage) * BigDecimal("0.00025")) +
                    (BigDecimal(1) * BigDecimal("3.0")) + // deathless
                    (BigDecimal(pegonGame1Stats.healing) * BigDecimal("0.0001"))
        assertEquals(expectedScore.setScale(5, RoundingMode.UP), pegonGame1Score.setScale(5, RoundingMode.UP))
    }
}