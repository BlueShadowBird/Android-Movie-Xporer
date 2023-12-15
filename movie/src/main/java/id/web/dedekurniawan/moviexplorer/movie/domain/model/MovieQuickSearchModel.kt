package id.web.dedekurniawan.moviexplorer.movie.domain.model

import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.movie.R

class MovieQuickSearchModel(id: String, name: String, additionalInfo: String)
    : QuickSearchBaseModel(id, name, additionalInfo) {

    override val iconId: Int
        get() = R.drawable.movie_icon
}