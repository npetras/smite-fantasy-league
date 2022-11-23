package com.nicolaspetras.splfantasy.service.scorer

import com.nicolaspetras.splfantasy.model.SplTeamName
import com.nicolaspetras.splfantasy.model.collection.SplMatchStats
import com.nicolaspetras.splfantasy.model.collection.SplPlayerStats
import com.nicolaspetras.splfantasy.model.score.SplMatchScore
import com.nicolaspetras.splfantasy.model.score.SplPlayerMatchScore
import com.nicolaspetras.splfantasy.service.scorer.rubric.OfficialRubricV1
import com.nicolaspetras.splfantasy.service.scorer.rubric.Rubric
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Scorer(private val rubric: Rubric = OfficialRubricV1()) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun scoreMatch(matchStats: SplMatchStats): SplMatchScore {
        val homeTeamScores = createHomeTeamScoresBase(matchStats)
        val awayTeamScores = createAwayTeamScoresBase(matchStats)

        for (game in matchStats.games) {
            scorePlayersOnTeam(game.orderTeamStats, matchStats, homeTeamScores, awayTeamScores)
            scorePlayersOnTeam(game.chaosTeamStats, matchStats, homeTeamScores, awayTeamScores)
            scoreBonusPoints(homeTeamScores, awayTeamScores)
        }

        return SplMatchScore(
            homeTeamName = matchStats.homeTeamName,
            awayTeamName = matchStats.awayTeamName,
            homeTeamScores = homeTeamScores,
            awayTeamScores = awayTeamScores
        )
    }

    /**
     * Adds bonus points earned by each player in the game
     */
    private fun scoreBonusPoints(homeTeamScores: List<SplPlayerMatchScore>, awayTeamScores: List<SplPlayerMatchScore>) {
//        scoreTeamBonusPts()
    }

    /**
     * Scores all the players on the team provided: Order or Chaos
     */
    private fun scorePlayersOnTeam(
        teamStats: ArrayList<SplPlayerStats>,
        matchStats: SplMatchStats,
        homeTeamScores: ArrayList<SplPlayerMatchScore>,
        awayTeamScores: ArrayList<SplPlayerMatchScore>
    ) {
        for (playerStats in teamStats) {
            val playerScore = rubric.calculatePlayerScore(playerStats)
            val teamScoresForCurrentPlayer =
                when (playerStats.splTeam) {
                    matchStats.homeTeamName -> {
                        homeTeamScores
                    }
                    matchStats.awayTeamName -> {
                        awayTeamScores
                    }
                    else -> {
                        log.error("Invalid team name: ${playerStats.splTeam}")
                        arrayListOf<SplPlayerMatchScore>()
                    }
                }
            val player = teamScoresForCurrentPlayer.find { it.name == playerStats.name }
            player?.gameScores?.add(playerScore)
        }
    }

    /**
     * Returns a list of [SplPlayerMatchScore]s that encompasses the players on the home team,
     * without any scores, just the SplPlayer details
     */
    private fun createHomeTeamScoresBase(matchStats: SplMatchStats): ArrayList<SplPlayerMatchScore> {
        return createTeamScoresBase(
            orderTeamName = matchStats.games[0].orderTeamName,
            teamName = matchStats.homeTeamName,
            orderTeamStats = matchStats.games[0].orderTeamStats,
            chaosTeamStats = matchStats.games[0].chaosTeamStats
        )
    }

    /**
     * Returns a list of [SplPlayerMatchScore]s that encompasses the players on the away team,
     * without any scores, just the SplPlayer details
     */
    private fun createAwayTeamScoresBase(matchStats: SplMatchStats): ArrayList<SplPlayerMatchScore> {
        return createTeamScoresBase(
            orderTeamName = matchStats.games[0].orderTeamName,
            teamName = matchStats.awayTeamName,
            orderTeamStats = matchStats.games[0].orderTeamStats,
            chaosTeamStats = matchStats.games[0].chaosTeamStats
        )
    }

    /**
     * Returns a List of [SplPlayerMatchScore]s that is already populated with the name, role and team name of
     * each player for the [teamName] provided.
     *
     */
    private fun createTeamScoresBase(
        orderTeamName: SplTeamName,
        teamName: SplTeamName,
        chaosTeamStats: ArrayList<SplPlayerStats>,
        orderTeamStats: ArrayList<SplPlayerStats>,
    ): ArrayList<SplPlayerMatchScore> {
        val teamScores = arrayListOf<SplPlayerMatchScore>()

        if (orderTeamName == teamName) {
            for (playerStats in orderTeamStats) {
                teamScores.add(createSplPlayerMatchScore(playerStats))
            }
        } else {
            for (playerStats in chaosTeamStats) {
                teamScores.add(createSplPlayerMatchScore(playerStats))
            }
        }
        return teamScores
    }

    /**
     * Returns an [SplPlayerMatchScore] that is based on the [playerStats] provided.
     * The [SplPlayerMatchScore] will have the same name, role and team as [playerStats].
     */
    private fun createSplPlayerMatchScore(playerStats: SplPlayerStats): SplPlayerMatchScore {
        return SplPlayerMatchScore(
            name = playerStats.name,
            role = playerStats.role,
            team = playerStats.splTeam
        )
    }

}