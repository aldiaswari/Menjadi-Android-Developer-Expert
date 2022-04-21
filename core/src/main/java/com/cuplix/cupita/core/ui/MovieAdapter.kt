package com.cuplix.cupita.core.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cuplix.cupita.core.BuildConfig
import com.cuplix.cupita.core.databinding.ListItemMovieBinding
import com.cuplix.cupita.core.domain.model.Movie
import com.cuplix.cupita.core.utils.Helper.loadFromUrl

class MovieAdapter :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MovieAdapter.ViewHolder {
        return ViewHolder(
            ListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val movie = listData[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {

                movie.posterPath.let {
                    imgPhoto.loadFromUrl(BuildConfig.IMAGE_URL + it)
                }
                tvTitle.text = movie.title
                tvDesc.text = movie.overview
                tvRealeseDate.text = movie.releaseDate

            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

    }


}