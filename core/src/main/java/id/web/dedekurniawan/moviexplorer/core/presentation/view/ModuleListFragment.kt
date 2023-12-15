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

        val searchQuery = arguments?.getString(SEARCH_QUERY_ARGUMENT)
        if(searchQuery.isNullOrBlank()){
            viewModel.retrieveTrending()
        }else{
            activity?.title  = "Search Movie: $searchQuery"
            viewModel.search(searchQuery)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object{
        private const val TAG = "BaseListFragment"
        const val SEARCH_QUERY_ARGUMENT = "searchQuery"
    }
}