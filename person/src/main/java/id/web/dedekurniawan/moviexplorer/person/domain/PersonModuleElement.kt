package id.web.dedekurniawan.moviexplorer.person.domain

import android.content.Context
import android.content.Intent
import id.web.dedekurniawan.moviexplorer.core.domain.ModuleElement
import id.web.dedekurniawan.moviexplorer.person.R
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.presentation.view.PersonDetailActivity
import id.web.dedekurniawan.moviexplorer.person.presentation.view.PersonListFragment

class PersonModuleElement(moduleUseCase: ModuleUseCase<Person>) : ModuleElement(moduleUseCase) {
    override val name = "person"
    override val itemMenuId = R.drawable.person_icon

    override fun createFragment() = PersonListFragment()
    override fun startActivity(context: Context, itemId: Int) {
        val intent = Intent(context, PersonDetailActivity::class.java)
        intent.putExtra(PersonDetailActivity.EXTRA_PERSON_ID, itemId)
        context.startActivity(intent)
    }
}