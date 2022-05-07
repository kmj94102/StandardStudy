package com.example.standardstudy.hilt

import javax.inject.Inject

interface Pokemon {
    val name: String
    var skillA : Skill?
    var skillB : Skill?
}

interface Skill {
    fun attack() : Pair<String, Int>
}

class ElectricitySkill : Skill {
    override fun attack() : Pair<String, Int> {
        val type : String = "전기"
        val damage : Int = 60
        return Pair(type, damage)
    }
}

class NormalSkill : Skill {
    override fun attack(): Pair<String, Int> {
        val type : String = "노말"
        val damage : Int = 30
        return Pair(type, damage)
    }
}

class GhostSkill : Skill {
    override fun attack(): Pair<String, Int> {
        val type : String = "고스트"
        val damage : Int = 70
        return Pair(type, damage)
    }
}

class FireSkill : Skill {
    override fun attack(): Pair<String, Int> {
        val type : String = "불꽃"
        val damage : Int = 40
        return Pair(type, damage)
    }
}

class Pikachu : Pokemon {
    override val name = "피카츄"
    override var skillA: Skill? = ElectricitySkill()

    override var skillB: Skill? = NormalSkill()
}

class Trainer @Inject constructor() {

    private lateinit var pokemon: Pokemon

    fun newPokemon(pokemon: Pokemon) {
        this.pokemon = pokemon
    }

    fun pokemonSkillAChange(skill : Skill){
        pokemon.skillA = skill
    }

    fun pokemonSkillBChange(skill : Skill) {
        pokemon.skillB = skill
    }

    fun state() : String {
        if (!this::pokemon.isInitialized) return "포켓몬을 생성해주세요"

        val skillA = pokemon.skillA?.attack()
        val skillB = pokemon.skillB?.attack()

        return "이름 : ${pokemon.name}\n" +
                "A 스킬 : ${skillA?.first} / ${skillA?.second}\n" +
                "B 스킬 : ${skillB?.first} / ${skillB?.second}\n"
    }

}

class User @Inject constructor(
    private val trainer: Trainer
) {

    fun getNewPokemon(pokemon: Pokemon) {
        trainer.newPokemon(pokemon)
    }

    fun pokemonSkillAChange(skill : Skill){
        trainer.pokemonSkillAChange(skill)
    }

    fun pokemonSkillBChange(skill : Skill) {
        trainer.pokemonSkillBChange(skill)
    }

    fun pokemonState(state : (String) -> Unit) {
        state(trainer.state())
    }

}

