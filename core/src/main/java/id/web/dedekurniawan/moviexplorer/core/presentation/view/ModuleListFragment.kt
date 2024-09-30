package id.web.dedekurniawan.moviexplorer.core.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.web.dedekurniawan.moviexplorer.core.data.remote.Result
import id.web.dedekurniawan.moviexplorer.core.databinding.FragmentListBinding
import id.web.dedekurniawan.moviexplorer.core.presentation.viewmodel.ModuleViewModel
import id.web.dedekurniawan.moviexplorer.core.utils.alert

abstract class ModuleListFragment<T> : Fragment() {
    protected lateinit var binding: FragmentListBinding
    protected abstract val viewModel: ModuleViewModel<T>
    protected abstract val moduleName: String

    abstract fun processData(dataList: List<T>?)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)

        viewModel.listResult.observe(requireActivity()){
            when(it){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    processData(it.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    alert(binding.root, TAG, it.message.toString())
                }
            }
        }

        binding.run {
            swipeRefreshLayout.setOnRefreshListener {
                // Refresh your data here
                loadData()
                swipeRefreshLayout.isRefreshing = false //no need to show refresh indicator, the page have loading indicator
            }
        }

        loadData()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadData(){
        val isFavorite = arguments?.getBoolean(ARGUMENT_ISFAVORITE)
        if(isFavorite == true){
            viewModel.getFavorites()
        }else{
            val searchQuery = arguments?.getString(ARGUMENT_SEARCH_QUERY)
            if(searchQuery.isNullOrBlank()){
                viewModel.retrieveTrending()
            }else{
                activity?.title  = "Search $moduleName: $searchQuery"
                viewModel.search(searchQuery)
            }
        }

    }

    companion object{
        private const val TAG = "BaseListFragment"
        const val ARGUMENT_ISFAVORITE = "isFavorite"
        const val ARGUMENT_SEARCH_QUERY = "searchQuery"
    }
}