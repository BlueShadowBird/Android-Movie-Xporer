package id.web.dedekurniawan.moviexplorer.person.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.web.dedekurniawan.moviexplorer.core.utils.loadGLide
import id.web.dedekurniawan.moviexplorer.core.utils.reviewScoreToColor
import id.web.dedekurniawan.moviexplorer.person.databinding.RvPersonBinding
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import kotlin.math.roundToInt

class PersonAdapter: ListAdapter<Person, PersonAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
    }
){
    var onClickListener: ((Person) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = getItem(position)
        holder.bind(person, onClickListener)
    }

    class ViewHolder private constructor(private val binding: RvPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person, onClickListener: ((Person) -> Unit)?){
            binding.run {
                personName.text = person.name

                val score = (person.popularity?:0F).roundToInt()
                personPopularity.setProgress(score)
                personPopularity.setProgressColor(reviewScoreToColor(score))

                personDepartment.text = person.knownForDepartment
                personGender.text = person.gender
                loadGLide(personImage, person.profilePath)

                itemView.setOnClickListener {
                    onClickListener?.invoke(person)
                }
            }
        }

        companion object{
            fun from(parent: ViewGroup) = ViewHolder(RvPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
}