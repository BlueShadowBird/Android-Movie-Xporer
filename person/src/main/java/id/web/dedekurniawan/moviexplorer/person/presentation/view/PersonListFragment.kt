package id.web.dedekurniawan.moviexplorer.person.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.web.dedekurniawan.moviexplorer.core.presentation.view.ModuleListFragment
import id.web.dedekurniawan.moviexplorer.person.adapter.PersonAdapter
import id.web.dedekurniawan.moviexplorer.person.domain.model.Person
import id.web.dedekurniawan.moviexplorer.person.presentation.viewmodel.PersonViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PersonListFragment : ModuleListFragment<Person>() {
    private val personAdapter: PersonAdapter by inject{
        parametersOf(requireActivity())
    }
    override val viewModel: PersonViewModel by viewModel()
    override val moduleName = "Person"

    override fun processData(dataList: List<Person>?) {
        personAdapter.submitList(dataList)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val rvList = binding.rvSearch
        rvList.adapter = personAdapter
        rvList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        return view
    }

    companion object{
        private const val TAG = "PersonListFragment"
    }
}