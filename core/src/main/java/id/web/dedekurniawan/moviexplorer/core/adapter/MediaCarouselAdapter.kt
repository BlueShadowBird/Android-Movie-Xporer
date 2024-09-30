package id.web.dedekurniawan.moviexplorer.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import id.web.dedekurniawan.moviexplorer.core.databinding.ImageMediaItemBinding
import id.web.dedekurniawan.moviexplorer.core.databinding.VideoMediaItemBinding
import id.web.dedekurniawan.moviexplorer.core.domain.model.MediaSourceInfo
import id.web.dedekurniawan.moviexplorer.core.utils.loadImage
class MediaCarouselAdapter(mediaSourceInfo: List<MediaSourceInfo> = listOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mediaSources: MutableList<MediaSourceInfo> = ArrayList()

    init {
        mediaSources.addAll(mediaSourceInfo.filter {
            it.source==MediaSourceInfo.MEDIA_SOURCE_IMAGE||it.source==MediaSourceInfo.MEDIA_SOURCE_VIDEO
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType){
        1 -> VideoViewHolder.from(parent)
        else -> ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ImageViewHolder -> holder.bind(mediaSources[position].data)
            is VideoViewHolder -> holder.bind(mediaSources[position].data)
        }
    }

    override fun getItemViewType(position: Int): Int = if(mediaSources[position].source ==MediaSourceInfo.MEDIA_SOURCE_VIDEO)1 else 0

    override fun getItemCount() = mediaSources.size

    //////////////////////////////////////////////////////////////////////////////////////////

    class ImageViewHolder private constructor(val binding: ImageMediaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageURL: String){
            loadImage(binding.root.context, binding.image, imageURL)
        }
        companion object{
            fun from(parent: ViewGroup) = ImageViewHolder(ImageMediaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }
    class VideoViewHolder(binding: VideoMediaItemBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private val player = ExoPlayer.Builder(binding.root.context).build()

        init {
            binding.playerView.player = player
        }

        fun bind(videoURL: String){
            player.setMediaItem(MediaItem.fromUri(videoURL))

            player.prepare()
            player.play()
        }
        companion object{
            fun from(parent: ViewGroup) = VideoViewHolder(VideoMediaItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }
}