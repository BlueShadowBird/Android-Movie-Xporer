package id.web.dedekurniawan.moviexplorer.person.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.utils.alert
import id.web.dedekurniawan.moviexplorer.core.utils.loadGLide
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import id.web.dedekurniawan.moviexplorer.movie.R
import id.web.dedekurniawan.moviexplorer.person.databinding.ActivityPersonBinding
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.presentation.viewmodel.PersonViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class PersonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonBinding
    private val viewModel: PersonViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonBinding.inflate(layoutInflater)

        installSplashScreen()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)

        viewModel.result.observe(this){ result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { bindPersonData(it) }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    alert(binding.root, result.message.toString())
                }

                else -> {}
            }
        }
        viewModel.retrievePerson(intent.getIntExtra(EXTRA_PERSON_ID, 0))
    }

    @SuppressLint("SetTextI18n")
    private fun bindPersonData(person: Person) {
        supportActionBar?.title = person.name
        binding.apply {
            personGender.text = person.gender
            personBirthDay.text = person.birthday.toString()
            personPlaceOfBirth.text = person.placeOfBirth
            personBiography.text = person.biography

            personAlsoKnownAs.setList(person.alsoKnownAs)

            if ((person.popularity ?: 0F) > 0F) {
                val score = (person.popularity?:0F).roundToInt()
                personPopularity.setProgress(score)
                personPopularity.setProgressColor(reviewScoreToColor(score))

                userPopularity.text = getString(R.string.user_score)
            }else{
                personPopularity.setProgress(-1)
                userPopularity.text = getString(R.string.not_scored)
            }


            if (person.deathday == null){
                deathDay.visibility = View.GONE
                personDeathDay.visibility = View.GONE
            }else{
                personDeathDay.text = person.deathday.toString()
            }

            loadGLide(personImage, person.profilePath)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object{
        const val EXTRA_PERSON_ID = "extraPersonId"
    }
}