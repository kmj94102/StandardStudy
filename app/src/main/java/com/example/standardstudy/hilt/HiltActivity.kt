package com.example.standardstudy.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.standardstudy.databinding.ActivityHiltBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {

    @Inject lateinit var user: User

    private val binding : ActivityHiltBinding by lazy { ActivityHiltBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user.apply {
            getNewPokemon(Pikachu())
            pokemonSkillAChange(GhostSkill())
            pokemonSkillBChange(FireSkill())
            pokemonState {
                binding.textView.text = it
            }
        }
    }
}