package br.com.raulreis.instaapp.search.view

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raulreis.instaapp.R
import br.com.raulreis.instaapp.common.base.BaseFragment
import br.com.raulreis.instaapp.common.base.DependencyInjector
import br.com.raulreis.instaapp.common.model.UserAuth
import br.com.raulreis.instaapp.databinding.FragmentSearchBinding
import br.com.raulreis.instaapp.search.Search
import br.com.raulreis.instaapp.search.presentation.SearchPresenter

class SearchFragment : BaseFragment<FragmentSearchBinding, Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
), Search.View {

    override lateinit var presenter: Search.Presenter

    private val adapter by lazy { SearchAdapter(onItemClicked) }

    private var searchListener : SearchListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchListener) {
            searchListener = context
        }
    }

    override fun setupViews() {
        binding?.rvSearch?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvSearch?.adapter = adapter
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.searchRepository()
        presenter = SearchPresenter(this, repository)
    }

    private val onItemClicked: (String) -> Unit = { uuid ->
        searchListener?.goToProfile(uuid)
    }
    override fun getMenu(): Int? {
        return R.menu.menu_search
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.menu_search).actionView as SearchView)
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true) {
                        presenter.fetchUsers(newText)
                        return true
                    }
                    return false
                }
            })
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.progressSearch?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullUsers(users: List<UserAuth>) {
        binding?.txvSearchEmpty?.visibility = View.GONE
        binding?.rvSearch?.visibility = View.VISIBLE
        adapter.items = users
        adapter.notifyDataSetChanged()
    }

    override fun displayEmptyUsers() {
        binding?.txvSearchEmpty?.visibility = View.VISIBLE
        binding?.rvSearch?.visibility =  View.GONE
    }

    interface SearchListener {
        fun goToProfile(uuid : String)
    }
}