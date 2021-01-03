package com.example.skillbox.kotlin.a7extensionsobjectsenums

class Battle(
        firstTeamSize: Int,
        secondTeamSize: Int
) {
    private val firstTeam: Team = Team(firstTeamSize)
    val firstTeamList: List<Warrior> = firstTeam.createTeam
    private val secondTeam: Team = Team(secondTeamSize)
    val secondTeamList: List<Warrior> = secondTeam.createTeam
    val battleIsOver: Boolean
        get() {
            return ((firstTeamList.all { it.isKilled } || (secondTeamList.all { it.isKilled })))
        }

    fun getBattleState(): BattleState {
        return if (firstTeamList.any { !it.isKilled } && (secondTeamList.any { !it.isKilled })) {
            val teamHealth: Int = firstTeamList.sumBy { it.currentHealth } + secondTeamList.sumBy { it.currentHealth }
            BattleState.Progress(teamHealth)
        } else if (firstTeamList.any { !it.isKilled } && (secondTeamList.all { it.isKilled })) {
            BattleState.FirstTeamWin
        } else if (firstTeamList.all { it.isKilled } && (secondTeamList.any { !it.isKilled })) {
            BattleState.SecondTeamWin
        } else BattleState.Draw
    }

    fun nextBattleIteration() {
        while (!battleIsOver) {
            firstTeamList.shuffled()
            secondTeamList.shuffled()

            if (firstTeamList.size == secondTeamList.size) {
                for (current in 0 until firstTeamList.size) {
                    if (!firstTeamList[current].isKilled) {
                        firstTeamList[current].attack(secondTeamList[current])
                    } else break
                    continue
                }
                for (current in 0 until firstTeamList.size) {
                    if (!secondTeamList[current].isKilled) {
                        secondTeamList[current].attack(firstTeamList[current])
                    } else break
                    continue
                }
                getBattleState()

            } else if (firstTeamList.size < secondTeamList.size) {
                for (current in 0 until firstTeamList.size) {
                    firstTeamList[current].attack(secondTeamList[current])
                    secondTeamList[current].attack(firstTeamList[current])
                    continue
                }
                getBattleState()

            } else {
                for (current in 0 until secondTeamList.size) {
                    firstTeamList[current].attack(secondTeamList[current])
                    secondTeamList[current].attack(firstTeamList[current])
                    continue
                }
                getBattleState()
            }
        }
    }
}

