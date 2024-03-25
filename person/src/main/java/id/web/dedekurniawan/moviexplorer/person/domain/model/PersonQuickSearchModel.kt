package id.web.dedekurniawan.moviexplorer.person.domain.model

import id.web.dedekurniawan.moviexplorer.core.domain.model.QuickSearchBaseModel
import id.web.dedekurniawan.moviexplorer.person.R

class PersonQuickSearchModel(id: String, name: String, additionalInfo: String)
    : QuickSearchBaseModel(id, name, additionalInfo) {

    override val iconId: Int
        get() = R.drawable.person_icon
}