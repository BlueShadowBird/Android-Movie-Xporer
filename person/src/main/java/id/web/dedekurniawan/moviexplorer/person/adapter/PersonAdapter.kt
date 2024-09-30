package id.web.dedekurniawan.moviexplorer.person.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.web.dedekurniawan.moviexplorer.core.utils.loadImage
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import id.web.dedekurniawan.moviexplorer.person.databinding.RvPersonBinding
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.presentation.view.PersonDetailActivity
import kotlin.math.roundToInt

class PersonAdapter(private val activity: Activity): ListAdapter<Person, PersonAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
    }
){

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(activity, parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = getItem(position)
        holder.bind(person)
    }

    class ViewHolder private constructor(private val activity: Activity, private val binding: RvPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person){
            binding.run {
                personName.text = person.name

                if(person.popularity!=null) {
                    val score = person.popularity.roundToInt()
                    popularity.setProgress(score)
                    popularity.setProgressColor(reviewScoreToColor(score))

                    popularity.visibility = View.VISIBLE
                    popularityText.visibility = View.VISIBLE
                }else{
                    popularity.visibility = View.GONE
                    popularityText.visibility = View.GONE
                }

                personDepartment.text = person.knownForDepartment
                personGender.text = person.gender
                loadImage(binding.root.context, personImage, person.profilePath)

                itemView.setOnClickListener {
                    startDetailActivity(person, binding)
                }
            }
        }

        private fun startDetailActivity(person: Person, binding: RvPersonBinding){
            val intent = Intent(activity, PersonDetailActivity::class.java)
            intent.putExtra(PersonDetailActivity.EXTRA_PERSON_ID, person.id)
            intent.putExtra(PersonDetailActivity.EXTRA_PERSON_IMAGE, person.profilePath)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                binding.personImage,
                ViewCompat.getTransitionName(binding.personImage)!!
            )

            activity.startActivity(intent, options.toBundle())
        }

        companion object{
            fun from(activity: Activity, parent: ViewGroup) = ViewHolder(activity, RvPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}