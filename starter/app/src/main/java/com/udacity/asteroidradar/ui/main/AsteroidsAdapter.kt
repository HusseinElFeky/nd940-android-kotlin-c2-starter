package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.models.Asteroid

internal class AsteroidsAdapter(
    private val onItemClicked: (Asteroid) -> Unit
) : RecyclerView.Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    private var asteroids = emptyList<Asteroid>()

    override fun getItemCount(): Int = asteroids.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding = ItemAsteroidBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bind(asteroids[position])
    }

    internal fun setAsteroids(asteroids: List<Asteroid>) {
        this.asteroids = asteroids
        notifyDataSetChanged()
    }

    inner class AsteroidViewHolder(
        private val binding: ItemAsteroidBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            itemView.setOnClickListener {
                onItemClicked(asteroid)
            }
        }
    }
}
